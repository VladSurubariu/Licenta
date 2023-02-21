import cv2 as cv
import numpy as np

from matplotlib import pyplot as plt

cap = cv.VideoCapture(0)
cap.set(cv.CAP_PROP_FRAME_WIDTH, 1200)
cap.set(cv.CAP_PROP_FRAME_HEIGHT, 800)

lower_blue = np.array([90,50,50], np.uint8)
upper_blue = np.array([130,255,255], np.uint8)

lower_green = np.array([43, 132, 122], np.uint8)
upper_green = np.array([63, 250, 250], np.uint8)

lower_red = np.array([136,87,111], np.uint8)
upper_red = np.array([180,255,255], np.uint8)

def take_photo():
    ret, frame = cap.read()
    
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
    
    
def detectMultipleColors():
    while(True):
        _, img = cap.read()
            
        #converting frame(img i.e BGR) to HSV (hue-saturation-value)

        hsv=cv.cvtColor(img,cv.COLOR_BGR2HSV)


        #finding the range of red,blue and yellow color in the image
        blue=cv.inRange(hsv,lower_blue,upper_blue)
        yellow=cv.inRange(hsv,lower_red, upper_red)

        #Morphological transformation, Dilation  	
        kernal = np.ones((5 ,5), "uint8")

        blue=cv.dilate(blue,kernal)
        res1=cv.bitwise_and(img, img, mask = blue)

        yellow=cv.dilate(yellow,kernal)
        res2=cv.bitwise_and(img, img, mask = yellow)    


        #Tracking the Blue Color
        (contours,hierarchy)=cv.findContours(blue,cv.RETR_TREE,cv.CHAIN_APPROX_SIMPLE)
        for pic, contour in enumerate(contours):
            area = cv.contourArea(contour)
            if(area>300):
                x,y,w,h = cv.boundingRect(contour)	
                img = cv.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)

        #Tracking the yellow Color
        (contours,hierarchy)=cv.findContours(yellow,cv.RETR_TREE,cv.CHAIN_APPROX_SIMPLE)
        for pic, contour in enumerate(contours):
            area = cv.contourArea(contour)
            if(area>300):
                x,y,w,h = cv.boundingRect(contour)	
                img = cv.rectangle(img,(x,y),(x+w,y+h),(0,255,0),2)
                
        #cv.imshow("Redcolour",red)
        cv.imshow("Color Tracking",img)
        #cv.imshow("red",res) 	
        if cv.waitKey(10) & 0xFF == ord('q'):
            cap.release()
            cv.destroyAllWindows()
            break  
        
       
       
def find_color_code():
    BGR_color = np.array([[[5, 250, 29]]], dtype=np.uint8)
    x = cv.cvtColor(BGR_color, cv.COLOR_RGB2HSV)

    print(x[0][0])

if __name__ == "__main__":
    detectMultipleColors()
    