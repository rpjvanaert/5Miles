package com.example.failurism.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.failurism.ApiManagement.Api;
import com.example.failurism.ApiManagement.ApiImage;
import com.example.failurism.ApiManagement.ApiListener;
import com.example.failurism.ApiManagement.CatApiManager;
import com.example.failurism.ApiManagement.DogApiManager;
import com.example.failurism.GridAutofitLayoutManager;
import com.example.failurism.ApiManagement.ApiImageAdapter;
import com.example.failurism.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiListener, OnItemClickListener {

    private RecyclerView imageListRV;
    private ArrayList<ApiImage> images;
    private ApiImageAdapter imageAdapter;
    CatApiManager catApiManager;
    DogApiManager dogApiManager;
    Api chosenApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        images = new ArrayList<>();
        imageListRV = findViewById(R.id.image_list);
        imageAdapter = new ApiImageAdapter(this, images, this);
        imageListRV.setAdapter(imageAdapter);
        imageListRV.setLayoutManager(new GridAutofitLayoutManager(this, 400));

        chosenApi = Api.CAT;
        catApiManager = new CatApiManager(this, this);
        catApiManager.getImages();
    }

    @Override
    public void onItemClick(int clickedPosition){
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.DUMMY, images.get(clickedPosition));
        startActivity(detailIntent);
        Log.d(MainActivity.class.getName(), "Clicked on item no. " + clickedPosition);
    }

    @Override
    public void onPhotoAvailable(ApiImage image){
        images.add(image);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoError(Error error) {
    }

    public void toToggle(View view){
        switch (chosenApi){
            case CAT:
                chosenApi = Api.DOG;
                break;
            case DOG:
                chosenApi = Api.CAT;
                break;
        }
        reloadList();
    }

    public void toQuotes(View view){
        Intent intent = new Intent(this, QuoteActivity.class);
        startActivity(intent);
    }

    public void toReload(View view){
        reloadList();
    }

    private void reloadList(){
        images = new ArrayList<>();
        imageAdapter = new ApiImageAdapter(this, images, this);
        imageListRV.setAdapter(imageAdapter);
        switch (chosenApi){
            case CAT:
                catApiManager = new CatApiManager(this, this);
                catApiManager.getImages();
                break;
            case DOG:
                dogApiManager = new DogApiManager(this, this);
                dogApiManager.getImages();
                break;
        }
        imageAdapter.notifyDataSetChanged();
    }
}