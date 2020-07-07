package com.example.failurism.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.failurism.ApiManagement.Api;
import com.example.failurism.ApiManagement.ApiImage;
import com.example.failurism.ApiManagement.ApiListener;
import com.example.failurism.ApiManagement.CatApiManager;
import com.example.failurism.ApiManagement.DogApiManager;
import com.example.failurism.ApiManagement.PixabayApiManager;
import com.example.failurism.GridAutofitLayoutManager;
import com.example.failurism.ApiManagement.ApiImageAdapter;
import com.example.failurism.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiListener, OnItemClickListener {

    private RecyclerView imageListRV;
    private ArrayList<ApiImage> images;
    private ApiImageAdapter imageAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    CatApiManager catApiManager;
    DogApiManager dogApiManager;
    PixabayApiManager pixabayApiManager;
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
        imageListRV.setLayoutManager(new GridAutofitLayoutManager(this, 600));

        chosenApi = Api.CAT;
        catApiManager = new CatApiManager(this, this);
        catApiManager.getImages();

        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
                chosenApi = Api.PIXABAY;
                break;
            case PIXABAY:
                chosenApi = Api.CAT;
        }
        reloadList();
    }

    public void toQuotes(View view){
        Intent intent = new Intent(this, QuoteActivity.class);
        startActivity(intent);
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
            case PIXABAY:
                pixabayApiManager = new PixabayApiManager(this, this);
                pixabayApiManager.getImages();
        }
        imageAdapter.notifyDataSetChanged();
    }

    public void toTop(View view) {
        imageListRV.getLayoutManager().smoothScrollToPosition(imageListRV, new RecyclerView.State(), 0);
    }

    public void toHelp(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment fragment = new MainDialogFragment();
        fragment.show(ft, "Dialog");
    }
}