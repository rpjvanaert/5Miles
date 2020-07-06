package com.example.failurism.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.failurism.ApiManagement.ApiImage;
import com.example.failurism.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    public static final String DUMMY = "DETAIL_FIVE_MILES";

    private ApiImage detailImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        detailImage = (ApiImage) getIntent().getSerializableExtra(DUMMY);

        ImageView detailImageView = findViewById(R.id.detailIV);

        Glide.with(this).load(detailImage.getUrl()).into(detailImageView);

        if (Build.VERSION.SDK_INT >= 23){
            int permissionCheck = ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public void saveImage(){
        final ProgressDialog progress = new ProgressDialog(DetailActivity.this);
        class SaveThisImage extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progress.setTitle("Proecessing");
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.show();
            }

            Bitmap bitmap;
            FileOutputStream fos;
            Uri fileUri;

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    File sdCard = Environment.getExternalStorageDirectory();
//                    @SuppressLint("DefaultLocale") String fileName = String.format("%.d.jpg", System.currentTimeMillis());
                    String pattern = "dd-MM-yyyy_HH-mm-ss";
                    String fileName = detailImage.getApi().toString().toLowerCase() + "Image_at_" + new SimpleDateFormat(pattern).format(new Date());
                    File dir = new File(sdCard.getAbsolutePath() + "/fiveMiles/" + detailImage.getApi().toString().toLowerCase() + "ImgFolder");
                    dir.mkdirs();
                    final File myImageFile = new File (dir, fileName + ".jpg");

                    try{
                        fos = new FileOutputStream(myImageFile);
                        Glide.with(DetailActivity.this).asBitmap().load(detailImage.getLargeURL()).into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                bitmap = resource;
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                fileUri = Uri.fromFile(myImageFile);
                                intent.setData(fileUri);
                                DetailActivity.this.sendBroadcast(intent);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });

                    } catch (FileNotFoundException e){
                        Log.e("Error Saving image", e.toString() + ":___ " + e.getMessage());
                    }
                } catch (Exception e){
                    Log.e("Error saving image", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result){
                super.onPostExecute(result);
                if (progress.isShowing()){
                    progress.dismiss();
                }
                Toast.makeText(DetailActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(fileUri);
                sendBroadcast(scanIntent);
            }
        }
        SaveThisImage shareImg = new SaveThisImage();
        shareImg.execute();
    }

    public void toSave(View view){
        if (Build.VERSION.SDK_INT >= 23){
            int permissionCheck = ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                saveImage();
            }
        }
    }
}
