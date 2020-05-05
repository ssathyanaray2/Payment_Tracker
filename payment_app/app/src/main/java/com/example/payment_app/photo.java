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
                        image.setImageURI(imguri);
                    }
                }
                else{
                    openCamera();
                    image.setImageURI(imguri);
                }
            }
        });
    }
    private void openCamera(){
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        imguri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
        startActivityForResult(cameraIntent,Image_cap_code);

    }

    public void onRequestPermissionsResult( int resultcode,int requestCode, String[] permission, int[] grantResults){
        switch(requestCode){
            case Permission_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                    if(resultcode==RESULT_OK){
                        image.setImageURI(imguri);
                    }

                }
                else {
                    Toast.makeText(photo.this, "Permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /*protected void onActivityResult(int requestcode,int resultcode,Intent data){
        if(resultcode==RESULT_OK){
            image.setImageURI(imguri);
        }
    }*/
}
