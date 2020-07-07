package com.example.failurism.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.failurism.QuoteManagement.QuoteAdapter;
import com.example.failurism.QuoteManagement.QuoteListener;
import com.example.failurism.QuoteManagement.QuoteManager;
import com.example.failurism.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuoteActivity extends AppCompatActivity {

    private static final String LOGTAG = QuoteActivity.class.getName();

    private RecyclerView quoteListRV;
    private ArrayList<String> quotes;
    private QuoteAdapter quoteAdapter;
    private QuoteManager quoteManager;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        getSupportActionBar().hide();

        quotes = new ArrayList<>();

        quoteListRV = findViewById(R.id.quote_list);

        quoteAdapter = new QuoteAdapter(this, quotes);
        quoteListRV.setAdapter(quoteAdapter);
        quoteListRV.setLayoutManager(new GridLayoutManager(this, 1));

        this.readJson();
        quoteAdapter.notifyDataSetChanged();
//        quoteManager = new QuoteManager(this, this);
//        quoteManager.getQuotes();

    }

    public void readJson(){
//        try {
//            File jsonFile = new File("res/Quotes.json");
//            Scanner reader = new Scanner();
//            String jsonString = "";
//            while (reader.hasNextLine()){
//                jsonString += reader.nextLine();
//            }
//            reader.close();
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONArray quoteArray = jsonObject.getJSONArray("quotes");
//            for (int i = 0; i < quoteArray.length(); ++i){
//                quotes.add(quoteArray.getString(i));
//                Log.d(LOGTAG, "Adding: " + quoteArray.getString(i));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        quotes.addAll(Arrays.asList("Daar kun je niks aan doen, zo ben je geboren.",
                "Dat is oprecht bizar heftig.",
                "Bizar.",
                "Heftig.",
                "Jeetje.",
                "Dat heb je wel eens.",
                "Dat is een lastige kwestie.",
                "Dat is een een hele ervaring.",
                "Maar echt hoor.",
                "Een kat is geen mus.",
                "Een kip is geen mus.",
                "Dat is geheim.",
                "Ochean.",
                "Sorry.",
                "Wat doe je daar aan?",
                "Ik wist niet dat je boos werdt.",
                "Wanneer is het zand vervangen?",
                "JE HEBT MIJN LEVEN VERPEST!",
                "Handelsroute.",
                "William Wallace.",
                "Fantastisch.",
                "Dat is zeker waar.",
                "Ik heb er van geleerd.",
                "-Trekt aan shirt-",
                "To Be Honest",
                "Ik ga eerlijk zijn./Om heel eerlijk te zijn.",
                "Eerlijk gezegd.",
                "There you go.",
                "Ralf je microfoon.",
                "Dus jahh...",
                "Ja kan"));
    }

    public void toMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toTop(View view){
        quoteListRV.getLayoutManager().smoothScrollToPosition(quoteListRV, new RecyclerView.State(), 0);
    }


    public void toHelp(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment fragment = new MainDialogFragment();
        fragment.show(ft, "Dialog");
    }
}
