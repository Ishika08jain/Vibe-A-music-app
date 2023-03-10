package com.example.vibe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.vibe.helper.helper;

public class SplashScreen extends MainActivity {
    boolean isRunning= true;
    ActivityResultLauncher<String> storagePermissionLauncher;
    final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (granted) {
                loadmusic();

            } else {
                userResponses();
            }

        });
        storagePermissionLauncher.launch(permission);


    }

    private void userResponses() {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            loadmusic();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(permission)) {
                new AlertDialog.Builder(this).setTitle("Requesting Permission").setMessage("Allow us to fetch songs on your device")
                        .setPositiveButton("allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                storagePermissionLauncher.launch(permission);

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }
                        }).show();
            }

        } else {
            Toast.makeText(this, "YOU DENIED THE PERMISSION", Toast.LENGTH_SHORT).show();
        }

    }




//        if(isPermission()){
//            loadmusic();
//        }else{
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
//        }



//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                isPermission();
//            } else if (isPermission()) {
//                    loadmusic();
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
//
//            }
//
//
//        }






    private boolean isPermission() {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                return false;
            }
        }



    private void loadmusic(){
            AsyncTask.execute(() -> {
                helper.getAllMusic(SplashScreen.this);
                check();
            });

        }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning= false;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRunning= true;
        loadmusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning= false;
    }

    private void check() {
        if(isRunning){
            new Handler(Looper.myLooper()).postDelayed(() -> {

                if(helper.isLoaded){
                    startMain();
                }else{
                    check();
                }

            }, 100);

        }
    }


    private void startMain(){
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            isRunning= true;
            loadmusic();
            }

        }
    }



//        if (isPermission()) {
//            AsyncTask.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    helper.getAllMusic(SplashScreen.this);
//                } else
//
//                {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
//                }
//            });
//
//        }
//
//        videoView = findViewById(R.id.videoView);
//
//        String uriPath = "android.resource://com.example.vibe/" + R.raw.vibe_1;
//        Uri uri = Uri.parse(uriPath);
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//        videoView.start();
////  imgAnim.setVideoPath("android.resource://com.myapplication/" + R.drawable.vidio);
//        int SPLASH_DISPLAY_LENGTH = 5000;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(mainIntent);
//                finish();
//            }
//        }, 5000);
//    }
//
//    private boolean isPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//}
//
//


