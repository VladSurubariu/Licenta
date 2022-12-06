import cv2 as cv
import numpy as np

from matplotlib import pyplot as plt

cap = cv.VideoCapture(0)
cap.set(cv.CAP_PROP_FRAME_WIDTH, 1200)
cap.set(cv.CAP_PROP_FRAME_HEIGHT, 800)

lower_blue = np.array([90,50,50])
upper_blue = np.array([130,255,255])

lower_green = np.array([43, 132, 122])
upper_green = np.array([63, 250, 250])

def take_photo():
    ret, frame = cap.read();
    
    cv.imwrite('webcamphoto.jpg', frame)
    cap.release()
    
    
def take_video():
    
    while True:
    
        ret, frame = cap.read()
        width = int(cap.get(3))
        height = int(cap.get(4))
        
        hsv = cv.cvtColor(frame,cv.COLOR_BGR2HSV)
        
        cv.imshow('Frame', hsv)
        
        if cv.waitKey(1) == ord('q'):
            break
    
    cap.release()
    cv.destroyAllWindows()
    

def detect_color():
    while True:
        ret, frame = cap.read()
        height, width, x = frame.shape
        
        cx = int(width/2)
        cy = int(height/2)
        
        hsv = cv.cvtColor(frame,cv.COLOR_BGR2HSV)
        
        pixel_center = frame[cx, cy]
        cv.circle(hsv, (cx, cy), 5, (255, 0, 0), 3)
        
        
        cv.imshow('Frame', hsv)
    
        if cv.waitKey(1) == ord('q'):
            break
    
    cap.release()
    cv.destroyAllWindows()
        
        
def video_hsv_center_circle():
    while True:
        ret, frame = cap.read()
        height, width, x = frame.shape
        
        cx = int(width/2)
        cy = int(height/2)
        
        pixel_center = frame[cx, cy]
        hsv = cv.cvtColor(frame,cv.COLOR_BGR2HSV)
        mask = cv.inRange(hsv, lower_green, upper_green)
        
        result = cv.bitwise_and(frame, frame, mask = mask)
        cv.circle(result, (cx, cy), 5, (255, 0, 0), 3)
        
        cv.imshow('Frame', result)
    
        if cv.waitKey(1) == ord('q'):
            break
    
    cap.release()
    cv.destroyAllWindows()
       
       
def find_color_code():
    BGR_color = np.array([[[5, 250, 29]]], dtype=np.uint8)
    x = cv.cvtColor(BGR_color, cv.COLOR_RGB2HSV)

    print(x[0][0])

if __name__ == "__main__":
    video_hsv_center_circle()