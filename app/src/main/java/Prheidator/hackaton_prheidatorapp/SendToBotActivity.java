package Prheidator.hackaton_prheidatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SendToBotActivity extends AppCompatActivity {

    Session sessionFromMain=new Session();
    String url = "https://nodejs-express-app-cxlkb-2020-11-30.eu-gb.mybluemix.net/ai/";
    String json ;
    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_bot);
        // modification de l'actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        TextView ID = findViewById(R.id.getActivity_ID);
        TextView RepText = findViewById(R.id.getActivity_response);
        ID.setText(sessionFromMain.getId());

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 2000ms
                setSessionID();
                ID.setText(sessionFromMain.getId());
            }
        }, 2000);


        Button sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sendingPass = (EditText) findViewById(R.id.editMdp);
                sessionFromMain.setSendMessage(sendingPass.getText().toString());
                String rep = sessionFromMain.getSendMessage();
                json = "{\"sessionId\":\"" + sessionFromMain.getId() + "\",\"reqText\":\"" + sessionFromMain.getSendMessage() + "\"}";
                Log.i("JSON",json);


                try {
                   post(url,json);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                //Log.i("POST_JSON",rep);

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 2000ms
                        RepText.setText("Bot response: "+ sessionFromMain.getResponse());
                    }
                }, 2000);

            }
        });
    }


    public void post(String _url,String _json) throws IOException {
        RequestBody body = RequestBody.create(_json,MediaType.parse("application/json; charset=uft-8"));
        Request requestPOST = new Request.Builder().url(_url).post(body).build();
        Call postCall = httpClient.newCall(requestPOST);
        postCall.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String rep = response.body().string();
                response.close();
                sessionFromMain.setResponse(rep.substring(27,rep.length()-2));
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });


    }

    public void setSessionID(){

        sessionFromMain.setId(Session_Singleton.getInstance().getSession().getId());
    }

}