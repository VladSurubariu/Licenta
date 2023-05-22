package com.example.test;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.camera.core.*;

public class CameraActivity extends AppCompatActivity {
    ImageButton capture;
    ImageView imageView;

    public ImageButton middle_center_tile_placeholder, middle_left_tile_placeholder, middle_right_tile_placeholder;
    public ImageButton bottom_center_tile_placeholder, bottom_left_tile_placeholder, bottom_right_tile_placeholder;
    public ImageButton top_center_tile_placeholder, top_left_tile_placeholder, top_right_tile_placeholder;

    private PreviewView previewView;
    public ExecutorService cameraExecutor;
    public int cameraFacing = CameraSelector.LENS_FACING_BACK;

    public int tile_width_pixels = 200;
    public int camera_height_pixels = 300;

    RelativeLayout.LayoutParams middle_center_tile_placeholderRelativeParams, middle_left_tile_placeholderRelativeParams, middle_right_tile_placeholderRelativeParams;
    RelativeLayout.LayoutParams bottom_center_tile_placeholderRelativeParams, bottom_left_tile_placeholderRelativeParams, bottom_right_tile_placeholderRelativeParams;
    RelativeLayout.LayoutParams top_center_tile_placeholderRelativeParams, top_left_tile_placeholderRelativeParams, top_right_tile_placeholderRelativeParams;

    Bitmap bitmap;

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                startCamera(cameraFacing);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.cameraPreview);
        capture = findViewById(R.id.capture);
        imageView = findViewById(R.id.imageView);

        middle_center_tile_placeholder = findViewById(R.id.middle_center_tile_placeholder);
        middle_left_tile_placeholder = findViewById(R.id.middle_left_tile_placeholder);
        middle_right_tile_placeholder = findViewById(R.id.middle_right_tile_placeholder);

        bottom_center_tile_placeholder = findViewById(R.id.bottom_center_tile_placeholder);
        bottom_left_tile_placeholder = findViewById(R.id.bottom_left_tile_placeholder);
        bottom_right_tile_placeholder = findViewById(R.id.bottom_right_tile_placeholder);

        top_center_tile_placeholder = findViewById(R.id.top_center_tile_placeholder);
        top_left_tile_placeholder = findViewById(R.id.top_left_tile_placeholder);
        top_right_tile_placeholder = findViewById(R.id.top_right_tile_placeholder);

        cameraExecutor = Executors.newSingleThreadExecutor();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        middle_center_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) middle_center_tile_placeholder.getLayoutParams();
        middle_left_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) middle_left_tile_placeholder.getLayoutParams();
        middle_right_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) middle_right_tile_placeholder.getLayoutParams();

        bottom_center_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) bottom_center_tile_placeholder.getLayoutParams();
        bottom_left_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) bottom_left_tile_placeholder.getLayoutParams();
        bottom_right_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) bottom_right_tile_placeholder.getLayoutParams();

        top_center_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) top_center_tile_placeholder.getLayoutParams();
        top_left_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) top_left_tile_placeholder.getLayoutParams();
        top_right_tile_placeholderRelativeParams = (RelativeLayout.LayoutParams) top_right_tile_placeholder.getLayoutParams();

        drawCubePlaceholder(displayMetrics);

        if (ContextCompat.checkSelfPermission(CameraActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(android.Manifest.permission.CAMERA);
        } else {
            startCamera(cameraFacing);
        }
    }

    public void startCamera(int cameraFacing) {
        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();

                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();

                ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(cameraFacing).build();

                cameraProvider.unbindAll();

                Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(CameraActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            activityResultLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        } else {
                            takePicture(imageCapture);
                        }
                    }
                });

                preview.setSurfaceProvider(previewView.getSurfaceProvider());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    public void takePicture(ImageCapture imageCapture) {
        try {
            imageCapture.takePicture(cameraExecutor, new ImageCapture.OnImageCapturedCallback() {

                @Override
                public void onCaptureSuccess(ImageProxy imageProxy) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CameraActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Bitmap bitmap = imageProxyToBitmap(imageProxy);
                    bitmap = RotateBitmap(bitmap, 90);

                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight() - camera_height_pixels - 150;
//
                    String image_string = getStringImage(bitmap);
//
                    if (!Python.isStarted()){
                        Python.start(new AndroidPlatform(CameraActivity.this));
                    }
//
                    Python py = Python.getInstance();
                    PyObject pyObject = py.getModule("script");
                    PyObject obj = pyObject.callAttr("main", image_string, width, height);
//
                    String str = obj.toString();
////
                    int index_of_matrix = str.indexOf("matrix:");
                    String encoded_image_string = str.substring(0, index_of_matrix);
                    String matrix_values_string = str.substring(index_of_matrix);
////
                    byte[] arraydata = android.util.Base64.decode(encoded_image_string,Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(arraydata, 0, arraydata.length);
//
//
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CameraActivity.this, "Failed to save: ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    startCamera(cameraFacing);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(CameraActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("logger: ", "Try catch: " + e.getMessage());
        }
    }

    private int aspectRatio(int width, int height) {
        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }

    private void drawCubePlaceholder(DisplayMetrics displayMetrics) {
        int left_margin = displayMetrics.widthPixels/2 - tile_width_pixels/2;
        int top_margin = (displayMetrics.heightPixels - camera_height_pixels)/2 - tile_width_pixels/2 - 75;

        // Draw the center row of the cube placeholder
        middle_center_tile_placeholderRelativeParams.setMargins(left_margin, top_margin, 0, 0);
        middle_left_tile_placeholderRelativeParams.setMargins(left_margin - tile_width_pixels, top_margin, 0, 0);
        middle_right_tile_placeholderRelativeParams.setMargins(left_margin + tile_width_pixels, top_margin, 0, 0);

        // Draw the bottom row of the cube placeholder
        bottom_center_tile_placeholderRelativeParams.setMargins(left_margin, top_margin - tile_width_pixels, 0, 0);
        bottom_left_tile_placeholderRelativeParams.setMargins(left_margin - tile_width_pixels, top_margin - tile_width_pixels, 0, 0);
        bottom_right_tile_placeholderRelativeParams.setMargins(left_margin + tile_width_pixels, top_margin - tile_width_pixels, 0, 0);

        // Draw the top row of the cube placeholder
        top_center_tile_placeholderRelativeParams.setMargins(left_margin, top_margin + tile_width_pixels, 0, 0);
        top_left_tile_placeholderRelativeParams.setMargins(left_margin - tile_width_pixels, top_margin + tile_width_pixels, 0, 0);
        top_right_tile_placeholderRelativeParams.setMargins(left_margin + tile_width_pixels, top_margin + tile_width_pixels, 0, 0);
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ImageProxy.PlaneProxy planeProxy = image.getPlanes()[0];
        ByteBuffer buffer = planeProxy.getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}