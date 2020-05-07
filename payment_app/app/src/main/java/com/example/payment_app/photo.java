package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Build;
import android.net.Uri;
import android.content.Intent;
import android.content.ContentValues;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import java.io.File;
import java.io.FileOutputStream;


public class photo extends AppCompatActivity {

    private static final int Permission_code=1000;
    private static final int Image_cap_code=1001;
    Button captureimg;
    ImageView image;

    Uri imguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        image=findViewById(R.id.imageView);
        captureimg=findViewById(R.id.Capture);

        captureimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED ){
                        String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,Permission_code);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }
            }
        });
    }


    private void openCamera(){
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,0);
    }


    protected void onActivityResult(int requestcode,int resultcode,Intent data){
       super.onActivityResult(requestcode,resultcode,data);

       Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bitmap);
    }

}

/*
 ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        imguri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
         cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);


FileOutputStream out = new FileOutputStream("C:/Users/Spoorthi S/Desktop/bmpimg");
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);*/
