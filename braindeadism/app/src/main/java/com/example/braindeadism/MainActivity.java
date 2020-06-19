package com.example.braindeadism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiListener {

    private Button button1;
    private Button button2;
    private States state;

    private RecyclerView itemListRV;
    private ArrayList<String> content;
    private ImageAdapter imageAdapter;
    CatApiManager catApiManager;
    DogApiManager dogApiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = new ArrayList<>();
        itemListRV = findViewById(R.id.item_listRV);
        imageAdapter = new ImageAdapter(this, content);

        itemListRV.setAdapter(imageAdapter);

        catApiManager = new CatApiManager(this, this);
        dogApiManager = new DogApiManager(this, this);

        this.state = States.CATS;
        this.setUpState();

        this.button1 = findViewById(R.id.buttonTab1);
        this.button2 = findViewById(R.id.buttonTab2);

        this.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals(States.CATS)){
                    state = States.CATS;
                    setUpState();
                }
            }
        });

        this.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals(States.DOGS)){
                    state = States.DOGS;
                    setUpState();
                }
            }
        });
    }

    private void setUpState() {
        content = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, content);
        itemListRV.setAdapter(imageAdapter);
        if (state.equals(States.CATS)){
            catApiManager = new CatApiManager(this, this);
        } else {
            dogApiManager = new DogApiManager(this, this);
        }

    }

    @Override
    public void onPhotoAvailable(String url) {
        content.add(url);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoError(Error error) {

    }
}