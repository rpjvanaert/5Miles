package com.example.failurism.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.failurism.ApiManagement.ApiImage;
import com.example.failurism.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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

    Bitmap bitmap;

    public void toSave(View view){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_G_'at'_HH'hours'_mm'min'");
//        String fileName = "ImageNumber_" + sdf.format(new Date());
//        String file = detailImage.getUrl();
//        String fileExtension = file.substring(file.length() - 4);
//        Log.d(DetailActivity.class.getName(), "File link: " + file);
//
//        try {
//            InputStream in = new URL(file).openStream();
//            FileOutputStream fos = getApplicationContext().openFileOutput(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + R.string.app_name + "/" + "fiveMiles_" + fileName + fileExtension, Context.MODE_PRIVATE);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            byte[] data = new byte[file.length()];
//            int nRead;
//            while ((nRead = in.read(data, 0, data.length)) != -1){
//                stream.write(data, 0, nRead);
//            }
//            fos.write(stream.toByteArray());
//            in.close();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



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
                    String fileName = detailImage.getApi().toString() + new SimpleDateFormat(pattern).format(new Date());
                    File dir = new File(sdCard.getAbsolutePath() + "/fiveMiles");
                    dir.mkdirs();
                    final File myImageFile = new File (dir, fileName + ".jpg");

                    try{
                        fos = new FileOutputStream(myImageFile);
//                        Bitmap bitmap = Picasso.get().load(detailImage.getUrl()).get();
                        Glide.with(DetailActivity.this).asBitmap().load(detailImage.getUrl()).into(new CustomTarget<Bitmap>() {
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

//        try {
//            bitmap = Picasso.get().load(detailImage.getUrl()).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
//        File path = Environment.getExternalStorageDirectory();
//        File dir = new File(path+"/DCIM");
//        dir.mkdirs();
//        String imageName = time+".JPEG";
//
//        File file = new File (dir, imageName);
//        OutputStream out;
//
//        try {
//            out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//            Toast.makeText(DetailActivity.this, "Image save to DCIM", Toast.LENGTH_SHORT).show();
//        } catch (Exception e){
//            Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//        }
    }
}
