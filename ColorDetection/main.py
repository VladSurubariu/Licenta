import cv2 as cv
import numpy as np
import time

cap = cv.VideoCapture(0)
cap.set(cv.CAP_PROP_FRAME_WIDTH, 1200)
cap.set(cv.CAP_PROP_FRAME_HEIGHT, 800)

drawing_tile_dimension = 70

cube_matrix = [["","",""],
               ["","",""],
               ["","",""]]

a00 = a01 = a02 = a10 = a11 = a12 = a20 = a21 = a22 = np.array([[0,0], [0,0]])

# draw the cube in the top-left corner
def draw_model_cube(image):

    # first column
    cv.rectangle(image, (10, 10), (50, 50), (0, 0, 0), 2)
    cv.rectangle(image, (10, 50), (50, 90), (0, 0, 0), 2)
    cv.rectangle(image, (10, 90), (50, 130), (0, 0, 0), 2)

    # second column
    cv.rectangle(image, (50, 10), (90, 50), (0, 0, 0), 2)
    cv.rectangle(image, (50, 50), (90, 90), (0, 0, 0), 2)
    cv.rectangle(image, (50, 90), (90, 130), (0, 0, 0), 2)

    # third column
    cv.rectangle(image, (90, 10), (130, 50), (0, 0, 0), 2)
    cv.rectangle(image, (90, 50), (130, 90), (0, 0, 0), 2)
    cv.rectangle(image, (90, 90), (130, 130), (0, 0, 0), 2)


def calculate_placeholder_cube_coords(cx, cy):
    
    a00_left = (cx - int(drawing_tile_dimension * 1.5), cy - int(drawing_tile_dimension * 1.5))
    a00_right = (cx - int(drawing_tile_dimension * 0.5), cy - int(drawing_tile_dimension * 0.5))
    a00 = [a00_left, a00_right]
    
    a01_left = (cx - int(drawing_tile_dimension * 0.5), cy - int(drawing_tile_dimension * 1.5))
    a01_right = (cx + int(drawing_tile_dimension * 0.5), cy - int(drawing_tile_dimension * 0.5))
    a01 = [a01_left, a01_right]

    a02_left = (cx + int(drawing_tile_dimension * 0.5), cy - int(drawing_tile_dimension * 1.5))
    a02_right = (cx + int(drawing_tile_dimension * 1.5), cy - int(drawing_tile_dimension * 0.5))
    a02 = [a02_left, a02_right]
    
    a10_left = (cx - int(drawing_tile_dimension * 1.5), cy - int(drawing_tile_dimension * 0.5))
    a10_right = (cx - int(drawing_tile_dimension * 0.5), cy + int(drawing_tile_dimension * 0.5))
    a10 = [a10_left, a10_right]

    a11_left = (cx - int(drawing_tile_dimension * 0.5), cy - int(drawing_tile_dimension * 0.5))
    a11_right = (cx + int(drawing_tile_dimension * 0.5), cy + int(drawing_tile_dimension * 0.5))
    a11 = [a11_left, a11_right]
    
    a12_left = (cx + int(drawing_tile_dimension * 0.5), cy - int(drawing_tile_dimension * 0.5))
    a12_right = (cx + int(drawing_tile_dimension * 1.5), cy + int(drawing_tile_dimension * 0.5))
    a12 = [a12_left, a12_right]

    a20_left = (cx - int(drawing_tile_dimension * 1.5), cy + int(drawing_tile_dimension * 0.5))
    a20_right = (cx - int(drawing_tile_dimension * 0.5), cy + int(drawing_tile_dimension * 1.5))
    a20 = [a20_left, a20_right]
    
    a21_left = (cx - int(drawing_tile_dimension * 0.5), cy + int(drawing_tile_dimension * 0.5))
    a21_right = (cx + int(drawing_tile_dimension * 0.5), cy + int(drawing_tile_dimension * 1.5))
    a21 = [a21_left, a21_right]

    a22_left = (cx + int(drawing_tile_dimension * 0.5), cy + int(drawing_tile_dimension * 0.5))
    a22_right = (cx + int(drawing_tile_dimension * 1.5), cy + int(drawing_tile_dimension * 1.5))
    a22 = [a22_left, a22_right]
    
    return a00, a01, a02, a10, a11, a12, a20, a21, a22


def draw_cube_placeholder(image,cx, cy):

    a00, a01, a02, a10, a11, a12, a20, a21, a22 = calculate_placeholder_cube_coords(cx, cy)
    # first column
    cv.rectangle(image, a00[0], a00[1], (0, 0, 0), 2)
    cv.rectangle(image, a10[0], a10[1], (0, 0, 0), 2)
    cv.rectangle(image, a20[0], a20[1], (0, 0, 0), 2)

    # second column
    cv.rectangle(image, a01[0], a01[1], (0, 0, 0), 2)
    cv.rectangle(image, a11[0], a11[1], (0, 0, 0), 2)
    cv.rectangle(image, a21[0], a21[1], (0, 0, 0), 2)

    # third column
    cv.rectangle(image, a02[0], a02[1], (0, 0, 0), 2)
    cv.rectangle(image, a12[0], a12[1], (0, 0, 0), 2)
    cv.rectangle(image, a22[0], a22[1], (0, 0, 0), 2)


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

    
def detect_multiple_colors():
    while True:
        _, img = cap.read()
        height, width, _ = img.shape
        hsv = cv.cvtColor(img, cv.COLOR_BGR2HSV)
        
        cx = int(width/2)
        cy = int(height/2)
        
        # calculate the pixels coords
        a00_center, a01_center, a02_center, a10_center, a11_center, a12_center, a20_center, a21_center, a22_center  = get_tiles_centers(cx, cy)
        
        # draw the Rubik's cube in the top-left corner
        draw_model_cube(img)

        # draw the placeholder for the Rubik's cube
        draw_cube_placeholder(img,cx, cy)
        
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
                # use color dectection in a certain area of the video source
                h_value = centers[i][j][0]
                s_value = centers[i][j][1]
                v_value = centers[i][j][2]
                
                if  h_value <= 6 :
                    cube_matrix[i][j] = "R" 
                elif 7 <= h_value <=11:
                    cube_matrix[i][j] = "O"
                elif 32 <= h_value <= 40:
                    cube_matrix[i][j] = "Y"
                elif 59 <= h_value <= 72:
                    cube_matrix[i][j] = "G"
                elif s_value < 50:
                    cube_matrix[i][j] = "W"
                elif 100 <= h_value <=115:
                    cube_matrix[i][j] = "B"
                #else:
                   #print(a11_center_pixel)
        
        print(cube_matrix) 
            
        img = cv.circle(img, (a11_center[0], a11_center[1]), 5, (255,0,0),3)
        
        cv.imshow("Color Tracking", img)
        if cv.waitKey(10) & 0xFF == ord('q'):
            cap.release()
            cv.destroyAllWindows()
            break
        

if __name__ == "__main__":
    detect_multiple_colors()
