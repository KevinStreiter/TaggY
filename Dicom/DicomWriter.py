import xml.etree.ElementTree as ET
import cv2
import pydicom
import subprocess
from pydicom import DataElement
from pydicom.dataset import Dataset, FileDataset


tree = ET.parse('allImageMetadata.xml')
root = tree.getroot()

# ds = pydicom.dcmread('a34z1107.dcm')  # reference file

print(root.tag)

for image in root.findall('image'):
    # reading xml meta data
    imgName = image.get('src')
    filename = imgName.split('.')[0] + '.dcm'
    description = image.find('description').text
    originalURL = image.find('originalURL').text
    magnification = image.find('magnification').text
    uidSuffix = image.find('uid').text

    # print(imgName, originalURL, magnification, uidSuffix, filename, description)
    subprocess.run(['E:\\dcm4che-5.12.0-bin\\dcm4che-5.12.0\\bin\\jpg2dcm.bat', 'E:\\tElvis\\allJPEGImages\\' + imgName,
                    '\\tElvis\\newdcm\\' + filename])
    ds = pydicom.dcmread('\\tElvis\\newdcm\\' + filename)

    # writing new meta information to dcm
    uid = '1.2.826.0.1.3680043.8.654.50.2010' + '.' + uidSuffix
    ds[0x0008, 0x0018] = DataElement(0x00080018, 'UI', uid)
    ds[0x0020, 0x4000] = DataElement(0x00204000, 'LT', description)
    ds.save_as('\\tElvis\\newdcm\\' + filename)
# print(ds)
# subprocess.run(['E:\\dcm4che-5.12.0-bin\\dcm4che-5.12.0\\bin\\jpg2dcm.bat', 'E:\\tElvis\\Dicom\\'+imgName, '\\tElvis\\Dicom\\'+filename])

# img = (cv2.imread(imgName))
# cv2.imshow('image', img)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# print(ds.PixelData)
# print(ds.pixel_array, img)

# for n, val in enumerate(ds.pixel_array.flat):
#     print(val, 'val')
#     print(ds.pixel_array.flat[n])
# ds.save_as(filename, write_like_original=True)
# print(enumerate(ds.pixel_array.flat))
# print(ds.pixel_array.shape)
# print(ds.Rows, ds.Columns)
