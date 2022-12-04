import cv2 as cv

from matplotlib import pyplot as plt

cap = cv.VideoCapture(0)
cap.set(cv.CAP_PROP_FRAME_WIDTH, 1200)
cap.set(cv.CAP_PROP_FRAME_HEIGHT, 800)

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
        height, width, _ = frame.shape()
        
        cx = int(width/2)
        cy = int(width/2)
        
        pixel_center = frame(cx, cy)
        cv.circle(frame, (cx, cy), 5, (255, 0, 0), 3)
        
        
        cv.imshow('Frame', frame)
    
        if cv.waitKey(1) == ord('q'):
            break
    
    cap.release()
    cv.destroyAllWindows()
        
        
        
detect_color()