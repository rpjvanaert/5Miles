package com.example.failurism.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.failurism.ApiManagement.ApiImage;
import com.example.failurism.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
    }

    public void toSave(View view){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_G_'at'_HH'hours'_mm'min'");
        String fileName = "ImageNumber_" + sdf.format(new Date());
        String file = detailImage.getUrl();
        String fileExtension = file.substring(file.length() - 4);
        File dir = getFilesDir();
        Log.d(DetailActivity.class.getName(), "File link: " + file);

        try {
            InputStream in = new URL(file).openStream();
            FileOutputStream fos = getApplicationContext().openFileOutput("fiveMiles_" + fileName + fileExtension, Context.MODE_PRIVATE);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] target = new byte[in.available()];
            stream.write(target);
            fos.write(stream.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
