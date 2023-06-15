import numpy as np
import cv2
import base64
import io
import PIL.Image as Image

drawing_tile_dimension = 375
cube_matrix = [["","",""],
               ["","",""],
               ["","",""]]

def calculate_placeholder_cube_coords(cx, cy):
    
    a00_left = (cx - round(drawing_tile_dimension * 1.5), cy - round(drawing_tile_dimension * 1.5))
    a00_right = (cx - round(drawing_tile_dimension * 0.5), cy - round(drawing_tile_dimension * 0.5))
    a00 = [a00_left, a00_right]
    
    a01_left = (cx - round(drawing_tile_dimension * 0.5), cy - round(drawing_tile_dimension * 1.5))
    a01_right = (cx + round(drawing_tile_dimension * 0.5), cy - round(drawing_tile_dimension * 0.5))
    a01 = [a01_left, a01_right]

    a02_left = (cx + round(drawing_tile_dimension * 0.5), cy - round(drawing_tile_dimension * 1.5))
    a02_right = (cx + round(drawing_tile_dimension * 1.5), cy - round(drawing_tile_dimension * 0.5))
    a02 = [a02_left, a02_right]
    
    a10_left = (cx - round(drawing_tile_dimension * 1.5), cy - round(drawing_tile_dimension * 0.5))
    a10_right = (cx - round(drawing_tile_dimension * 0.5), cy + round(drawing_tile_dimension * 0.5))
    a10 = [a10_left, a10_right]

    a11_left = (cx - round(drawing_tile_dimension * 0.5), cy - round(drawing_tile_dimension * 0.5))
    a11_right = (cx + round(drawing_tile_dimension * 0.5), cy + round(drawing_tile_dimension * 0.5))
    a11 = [a11_left, a11_right]
    
    a12_left = (cx + round(drawing_tile_dimension * 0.5), cy - round(drawing_tile_dimension * 0.5))
    a12_right = (cx + round(drawing_tile_dimension * 1.5), cy + round(drawing_tile_dimension * 0.5))
    a12 = [a12_left, a12_right]

    a20_left = (cx - round(drawing_tile_dimension * 1.5), cy + round(drawing_tile_dimension * 0.5))
    a20_right = (cx - round(drawing_tile_dimension * 0.5), cy + round(drawing_tile_dimension * 1.5))
    a20 = [a20_left, a20_right]
    
    a21_left = (cx - round(drawing_tile_dimension * 0.5), cy + round(drawing_tile_dimension * 0.5))
    a21_right = (cx + round(drawing_tile_dimension * 0.5), cy + round(drawing_tile_dimension * 1.5))
    a21 = [a21_left, a21_right]

    a22_left = (cx + round(drawing_tile_dimension * 0.5), cy + round(drawing_tile_dimension * 0.5))
    a22_right = (cx + round(drawing_tile_dimension * 1.5), cy + round(drawing_tile_dimension * 1.5))
    a22 = [a22_left, a22_right]
    
    return a00, a01, a02, a10, a11, a12, a20, a21, a22

def get_tiles_centers(cx, cy):
    
    a00, a01, a02, a10, a11, a12, a20, a21, a22 = calculate_placeholder_cube_coords(cx, cy)
    
    a00_center = np.array([int(( a00[0][0] + a00[1][0] ) / 2), int((a00[0][1] + a00[1][1] ) / 2)])
    a01_center = np.array([int(( a01[0][0] + a01[1][0] ) / 2), int((a01[0][1] + a01[1][1] ) / 2)])
    a02_center = np.array([int(( a02[0][0] + a02[1][0] ) / 2), int((a02[0][1] + a02[1][1] ) / 2)])
    
    a10_center = np.array([int(( a10[0][0] + a10[1][0] ) / 2), int((a10[0][1] + a10[1][1] ) / 2)])
    a11_center = np.array([int(( a11[0][0] + a11[1][0] ) / 2), int((a11[0][1] + a11[1][1] ) / 2)])
    a12_center = np.array([int(( a12[0][0] + a12[1][0] ) / 2), int((a12[0][1] + a12[1][1] ) / 2)])

    a20_center = np.array([int(( a20[0][0] + a20[1][0] ) / 2), int((a20[0][1] + a20[1][1] ) / 2)])
    a21_center = np.array([int(( a21[0][0] + a21[1][0] ) / 2), int((a21[0][1] + a21[1][1] ) / 2)])
    a22_center = np.array([int(( a22[0][0] + a22[1][0] ) / 2), int((a22[0][1] + a22[1][1] ) / 2)])
    
    return a00_center, a01_center, a02_center, a10_center, a11_center, a12_center, a20_center, a21_center, a22_center

def main(data, width, height):

    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv2.imdecode(np_data, cv2.IMREAD_UNCHANGED)

    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    #calculate the pixels coords
    cx = round(width/2)
    cy = round(height/2)
    a00_center, a01_center, a02_center, a10_center, a11_center, a12_center, a20_center, a21_center, a22_center  = get_tiles_centers(cx, cy)

    a00_center_pixel = hsv[a00_center[1], a00_center[0]]
    a01_center_pixel = hsv[a01_center[1], a01_center[0]]
    a02_center_pixel = hsv[a02_center[1], a02_center[0]]

    a10_center_pixel = hsv[a10_center[1], a10_center[0]]
    a11_center_pixel = hsv[a11_center[1], a11_center[0]]
    a12_center_pixel = hsv[a12_center[1], a12_center[0]]

    a20_center_pixel = hsv[a20_center[1], a20_center[0]]
    a21_center_pixel = hsv[a21_center[1], a21_center[0]]
    a22_center_pixel = hsv[a22_center[1], a22_center[0]]

    centers = np.array([[a00_center_pixel, a01_center_pixel, a02_center_pixel],
                        [a10_center_pixel, a11_center_pixel, a12_center_pixel],
                        [a20_center_pixel, a21_center_pixel, a22_center_pixel]])

    centers_i, centers_j, _ = centers.shape

    for i in range(centers_i):
            for j in range(centers_j):
                # use color detection in a certain area of the video source
                h_value = centers[i][j][0]
                s_value = centers[i][j][1]

                if  h_value <= 4 :
                    cube_matrix[i][j] = "R"
                elif s_value < 50:
                    cube_matrix[i][j] = "W"
                elif h_value <=25:
                    cube_matrix[i][j] = "O"
                elif h_value <= 40:
                    cube_matrix[i][j] = "Y"
                elif h_value <= 75:
                    cube_matrix[i][j] = "G"
                elif h_value <=115:
                    cube_matrix[i][j] = "B"
                else:
                    cube_matrix[i][j] = "R"

    img = cv2.circle(img, (a00_center[0], a00_center[1]), 5, (0, 0, 0), 2)
    img = cv2.circle(img, (a01_center[0], a01_center[1]), 5, (0, 0, 0), 2)
    img = cv2.circle(img, (a02_center[0], a02_center[1]), 5, (0, 0, 0), 2)

    ## second column
    img = cv2.circle(img, (a10_center[0], a10_center[1]), 5, (0, 0, 0), 2)
    img = cv2.circle(img, (a11_center[0], a11_center[1]), 5, (0, 0, 0), 2)
    img = cv2.circle(img, (a12_center[0], a12_center[1]), 5, (0, 0, 0), 2)

    # third column
    img = cv2.circle(img, (a20_center[0], a20_center[1]), 5, (0, 0, 0), 2)
    img = cv2.circle(img, (a21_center[0], a21_center[1]), 5, (0, 0, 0), 2)
    img = cv2.circle(img, (a22_center[0], a22_center[1]), 5, (0, 0, 0), 2)

    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    pil_im = Image.fromarray(img_rgb)

    buff = io.BytesIO()
    pil_im.save(buff, format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return "" + str(img_str, 'utf-8') + "matrix:" + str(cube_matrix)
