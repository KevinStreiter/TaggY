import xml.etree.ElementTree as ET
import pydicom
import subprocess
from pydicom import DataElement

# Path needs to be changed to run this script
jpg2dcmPath = 'E:\\dcm4che-5.12.0-bin\\dcm4che-5.12.0\\bin\\jpg2dcm.bat'
jpegPath = 'E:\\tElvis\\allJPEGImages\\'
dcmPath = '\\tElvis\\newdcm\\'

tree = ET.parse('allImageMetadata.xml')
root = tree.getroot()
photometricInterpretation = 'YBR_FULL_422'

for image in root.findall('image'):
    # reading xml meta data
    imgName = image.get('src')
    filename = imgName.split('.')[0] + '.dcm'
    description = image.find('description').text
    originalURL = image.find('originalURL').text
    magnification = image.find('magnification').text
    uidSuffix = image.find('uid').text

    # creating dcm files with jpg2dcm
    subprocess.run([jpg2dcmPath, jpegPath + imgName, dcmPath + filename])
    ds = pydicom.dcmread(dcmPath + filename)

    # writing new meta information to dcm
    uid = '1.2.826.0.1.3680043.8.654.50.2010' + '.' + uidSuffix
    ds[0x0008, 0x0018] = DataElement(0x00080018, 'UI', uid)
    ds[0x0020, 0x4000] = DataElement(0x00204000, 'LT', description)
    ds[0x0028, 0x0004] = DataElement(0x00280004, 'CS', photometricInterpretation)  # without this colors may not match
    ds.save_as(dcmPath + filename)
