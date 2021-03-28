package Prheidator.hackaton_prheidatorapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    String url = "https://nodejs-express-app-cxlkb-2020-11-30.eu-gb.mybluemix.net/ai";
    private final OkHttpClient httpClient = new OkHttpClient();
    Session session;
    String passSessionID;
    RotateAnimation rotation;
    AlphaAnimation alpha;
    TranslateAnimation move;
    TranslateAnimation moveInit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set starting animation
        ImageView logo = findViewById(R.id.logo);


        moveInit = new TranslateAnimation(0, 0, 500, 0);
        moveInit.setDuration(2000);
        logo.startAnimation(moveInit);

        Button ConnexionButton = findViewById(R.id.main_button);
        TextView legend = findViewById(R.id.main_Info);
        TextView Title = findViewById(R.id.main_Title);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 2000ms
                rotation = new RotateAnimation(0, 1440, 130, 130);
                rotation.setDuration(5000);
                logo.startAnimation(rotation);
            }
        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 7000ms
                move = new TranslateAnimation(-100, 0, 0, 0);
                move.setDuration(1000);
                Title.setVisibility(View.VISIBLE);
                Title.startAnimation(move);
            }
        }, 7000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 6500ms
                ConnexionButton.setVisibility(View.VISIBLE);
                legend.setVisibility(View.VISIBLE);

            }
        }, 8000);

        // Ajout du bouton de connexion et de de la fonction GET session
        ConnexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSession();
                goToSendToBotActivity();
            }
        });

        Switch DM = findViewById(R.id.switch1);
        DM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DarkMode();
                }
                else{
                    LightMode();
                }
            }
        });
    }

    private void LightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Log.i("dm","light");
    }

    private void DarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.i("dm","dark");
    }
    // fonction pour changer d'activité
    public void goToSendToBotActivity(){
        Intent intent = new Intent(this,SendToBotActivity.class);
        startActivity(intent);
    }

    //Fonction pour GET l'id de session (ASync)
    private void getSession() {
        String TAG = "getsesion";

        Request request = new Request.Builder()
                .url(url + "/session")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    // Recupération du body de la réponse
                    String rep=responseBody.string();
                    responseBody.close();
                    String repID = rep.substring(27,rep.length()-2);
                    Log.i("DATA",rep);
                    Log.i("DATAID",repID);
                    // Stovk
                    Session_Singleton.getInstance().setSession(new Session(repID));
                    Log.i("DataSingleton",Session_Singleton.getInstance().getSession().getId());

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}