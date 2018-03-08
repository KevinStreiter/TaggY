'''
Created on 07.03.2018

@author: Joel Zimmerli
'''

import psycopg2
import xml.etree.ElementTree as ET

def connect():
    conn = psycopg2.connect(
        host="127.0.0.1", port=5432, database='taggy',
        user="taggy", password="taggytaggy"
        )
    return conn

INSERT_STRING = """
insert into "pictures" (id, comment, description)
values (%s,%s,%s)
"""
def insert(id,comment,description, cur):
    
    cur.execute(INSERT_STRING,(id, comment, description))
    cur.connection.commit()
    
def parseXML(file,cur):
    tree = ET.parse(file)
    root = tree.getroot()
    for child in root:
        insert(child.find("uid").text, "", child.find("description").text, cur)
        
        
        
if __name__ == '__main__':
    cur=connect().cursor()
    parseXML("Metadata.xml",cur)