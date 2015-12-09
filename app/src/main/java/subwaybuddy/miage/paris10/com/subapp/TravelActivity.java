package subwaybuddy.miage.paris10.com.subapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import subwaybuddy.miage.paris10.com.subapp.libraries.DateDialog;
import subwaybuddy.miage.paris10.com.subapp.libraries.HttpRequestsApi;
import subwaybuddy.miage.paris10.com.subapp.libraries.TimeDialog;

public class TravelActivity extends AppCompatActivity {

    EditText ET_DATE,ET_TIME,ET_NAME;
    String travel_date,travel_time,travel_info,travel_name,travel_userid;
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        ET_DATE = (EditText)findViewById(R.id.txtdate);
        ET_TIME = (EditText)findViewById(R.id.txt_time);
        ET_NAME = (EditText)findViewById(R.id.txt_nametravel);
        // Session Manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // id
        travel_userid = user.get(SessionManager.KEY_ID);



    }

    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)findViewById(R.id.txtdate);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });

        EditText txtTime=(EditText)findViewById(R.id.txt_time);
        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    TimeDialog dialog = TimeDialog.newInstance(view);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, "TimeDialog");

                }
            }

        });

    }

    public void sendTravel(View view) throws IOException, JSONException {
        travel_date = ET_DATE.getText().toString();
        travel_time = ET_TIME.getText().toString();
        travel_info = travel_date + travel_time;
        travel_name = ET_NAME.getText().toString();


        String url          = "http://christian-hiroz.com/SubwayBuddy/web/app_dev.php/api/travels" ;
        HttpRequestsApi api = new HttpRequestsApi( url ) ;
        String reponse      = api.post("time=" + travel_info + "&name=" + travel_name + "&user=" + travel_userid);
        System.out.println(reponse);
        if(reponse.equalsIgnoreCase("0")){
            Toast toast = Toast.makeText(this, "Ajout du voyage échoué, veuillez réessayer svp", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {

            Toast toast = Toast.makeText(this, "Ajout du voyage avec succès", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            /*
            JSONObject json = new JSONObject(reponse);
            String str_valueId = json.getString("id");// on récupère l'id de l'user
            String str_valueUsername = json.getString("username");// on récupère l'id de l'user
            String str_valueEmail = json.getString("email");// on récupère l'id de l'user

            String tempreponse = str_valueId;
            System.out.println("id" + str_valueId);
            // Creating user login session
            session.createLoginSession(str_valueUsername, str_valueEmail);
            startActivity(new Intent(this, TravelActivity.class));
            */

        }



    }





}
