import cv2 as cv
import numpy as np

cap = cv.VideoCapture(0)
cap.set(cv.CAP_PROP_FRAME_WIDTH, 1200)
cap.set(cv.CAP_PROP_FRAME_HEIGHT, 800)

lower_blue = np.array([95, 206, 0], np.uint8)
upper_blue = np.array([255, 255, 255], np.uint8)

lower_red = np.array([0, 160, 74], np.uint8)
upper_red = np.array([255, 201, 118], np.uint8)

lower_yellow = np.array([25, 116, 141], np.uint8)
upper_yellow = np.array([66, 237, 242], np.uint8)

lower_green = np.array([53, 194, 68], np.uint8)
upper_green = np.array([85, 255, 255], np.uint8)

lower_orange = np.array([0, 144, 145], np.uint8)
upper_orange = np.array([83, 225, 249], np.uint8)

lower_white = np.array([87, 44, 140], np.uint8)
upper_white = np.array([109, 134, 173], np.uint8)


def take_photo():
    _, frame = cap.read()

    cv.imwrite('webcamphoto.jpg', frame)
    cap.release()


def take_video():
    while True:

        ret, frame = cap.read()
        # width = int(cap.get(3))
        # height = int(cap.get(4))

        hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)

        cv.imshow('Frame', hsv)

        if cv.waitKey(1) == ord('q'):
            break

    cap.release()
    cv.destroyAllWindows()


def detect_color():
    while True:
        ret, frame = cap.read()
        height, width, x = frame.shape

        cx = int(width / 2)
        cy = int(height / 2)

        hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)

        # pixel_center = frame[cx, cy]
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

        cx = int(width / 2)
        cy = int(height / 2)

        # pixel_center = frame[cx, cy]
        hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)
        mask = cv.inRange(hsv, lower_green, upper_green)

        result = cv.bitwise_and(frame, frame, mask=mask)
        cv.circle(result, (cx, cy), 5, (255, 0, 0), 3)

        cv.imshow('Frame', result)

        if cv.waitKey(1) == ord('q'):
            break

    cap.release()
    cv.destroyAllWindows()


def detect_multiple_colors():
    # Morphological transformation, Dilation
    kernel = np.ones((5, 5), "uint8")

    while True:
        _, img = cap.read()

        # draw the Rubik's cube in the top-left corner
        draw_model_cube(img)

        # draw the placeholder for the Rubik's cube
        draw_cube_placeholder(img)

        # converting frame(img i.e BGR) to HSV (hue-saturation-value)
        hsv = cv.cvtColor(img, cv.COLOR_BGR2HSV)

        blue, red, yellow, green, orange, white = take_colors(hsv)

        blue = cv.dilate(blue, kernel)
        # res1 = cv.bitwise_and(img, img, mask=blue)

        red = cv.dilate(red, kernel)
        # res2 = cv.bitwise_and(img, img, mask=red)

        yellow = cv.dilate(yellow, kernel)
        # res3 = cv.bitwise_and(img, img, mask=yellow)

        green = cv.dilate(green, kernel)
        # res4 = cv.bitwise_and(img, img, mask=green)

        orange = cv.dilate(orange, kernel)
        # res5 = cv.bitwise_and(img, img, mask=orange)

        white = cv.dilate(white, kernel)
        # res6 = cv.bitwise_and(img, img, mask=white)

        # TODO: can be simplified by using the common stuff in a for loop

        # Tracking the Blue Color
        (contours, hierarchy) = cv.findContours(blue, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        for pic, contour in enumerate(contours):
            area = cv.contourArea(contour)
            if area > 300:
                x, y, w, h = cv.boundingRect(contour)
                img = cv.rectangle(img, (x, y), (x + w, y + h), (255, 0, 0), 2)
                cv.putText(img, "blue", (x, y), cv.FONT_HERSHEY_PLAIN, 2, (255, 0, 0), 2)

        # # Tracking the yellow Color
        # (contours, hierarchy) = cv.findContours(red, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        # for pic, contour in enumerate(contours):
        #     area = cv.contourArea(contour)
        #     if area > 300:
        #         x, y, w, h = cv.boundingRect(contour)
        #         img = cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
        #         cv.putText(img, "red", (x, y), cv.FONT_HERSHEY_PLAIN, 2, (255, 0, 0), 2)
        
        # (contours, hierarchy) = cv.findContours(yellow, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        # for pic, contour in enumerate(contours):
        #     area = cv.contourArea(contour)
        #     if area > 300:
        #         x, y, w, h = cv.boundingRect(contour)
        #         img = cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
        #
        # (contours, hierarchy) = cv.findContours(green, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        # for pic, contour in enumerate(contours):
        #     area = cv.contourArea(contour)
        #     if area > 300:
        #         x, y, w, h = cv.boundingRect(contour)
        #         img = cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
        #
        # (contours, hierarchy) = cv.findContours(orange, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        # for pic, contour in enumerate(contours):
        #     area = cv.contourArea(contour)
        #     if area > 300:
        #         x, y, w, h = cv.boundingRect(contour)
        #         img = cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
        #
        # (contours, hierarchy) = cv.findContours(white, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        # for pic, contour in enumerate(contours):
        #     area = cv.contourArea(contour)
        #     if area > 300:
        #         x, y, w, h = cv.boundingRect(contour)
        #         img = cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)

        cv.imshow("Color Tracking", img)
        # cv.imshow("red",res)
        if cv.waitKey(10) & 0xFF == ord('q'):
            cap.release()
            cv.destroyAllWindows()
            break


# save the value-intervals for the colors of the Rubik's cube
def take_colors(hsv):
    # finding the range of red,blue and yellow color in the image
    blue = cv.inRange(hsv, lower_blue, upper_blue)
    red = cv.inRange(hsv, lower_red, upper_red)
    yellow = cv.inRange(hsv, lower_yellow, upper_yellow)
    green = cv.inRange(hsv, lower_green, upper_green)
    orange = cv.inRange(hsv, lower_orange, upper_orange)
    white = cv.inRange(hsv, lower_white, upper_white)

    return blue, red, yellow, green, orange, white


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


def draw_cube_placeholder(image):
    # first column
    cv.rectangle(image, (400, 200), (450, 250), (0, 0, 0), 2)
    cv.rectangle(image, (400, 250), (450, 300), (0, 0, 0), 2)
    cv.rectangle(image, (400, 300), (450, 350), (0, 0, 0), 2)

    # second column
    cv.rectangle(image, (450, 200), (500, 250), (0, 0, 0), 2)
    cv.rectangle(image, (450, 250), (500, 300), (0, 0, 0), 2)
    cv.rectangle(image, (450, 300), (500, 350), (0, 0, 0), 2)

    # third column
    cv.rectangle(image, (500, 200), (550, 250), (0, 0, 0), 2)
    cv.rectangle(image, (500, 250), (550, 300), (0, 0, 0), 2)
    cv.rectangle(image, (500, 300), (550, 350), (0, 0, 0), 2)



def find_color_code():
    bgr_color = np.array([[[5, 250, 29]]], dtype=np.uint8)
    x = cv.cvtColor(bgr_color, cv.COLOR_RGB2HSV)

    print(x[0][0])


if __name__ == "__main__":
    detect_multiple_colors()
