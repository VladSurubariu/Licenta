import numpy as np
import cv2
import base64
import io
import PIL.Image as Image

def main(data):

    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv2.imdecode(np_data, cv2.IMREAD_UNCHANGED)
    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    img_rgb = cv2.circle(img_rgb, (10, 10), 5, (255,0,0),3)
    #img_gray = cv2.cvtColor(img_gray, cv2.COLOR_BGR2RGB)
    pil_im = Image.fromarray(img_rgb)

    buff = io.BytesIO()
    pil_im.save(buff, format="PNG")
    img_str = base64.b64encode(buff.getvalue())
    #return img_str

    return "" + str(img_str, 'utf-8') + "matrix:"
