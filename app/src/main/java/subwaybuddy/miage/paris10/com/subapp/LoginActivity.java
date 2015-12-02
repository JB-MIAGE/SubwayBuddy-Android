package subwaybuddy.miage.paris10.com.subapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

import subwaybuddy.miage.paris10.com.subapp.libraries.DashboardActivity;
import subwaybuddy.miage.paris10.com.subapp.libraries.HttpRequestsApi;

public class LoginActivity extends AppCompatActivity
{
    EditText ET_NAME,ET_PASS;
    String login_name,login_pass;
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_NAME = (EditText)findViewById(R.id.user_name);
        ET_PASS = (EditText)findViewById(R.id.user_pass);
        // Session Manager
        session = new SessionManager(getApplicationContext());
    }

    public void userReg(View view)
    {
        startActivity(new Intent(this,Register.class));
    }

    public void userLogin(View view) throws IOException, JSONException {
        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        //BackgroundTask backgroundTask = new BackgroundTask(this);
        //backgroundTask.execute( "login", login_name, login_pass );
        // on POST le contenu des champs textes pour authentifier l'utilisateur.
        String url          = "http://christian-hiroz.com/SubwayBuddy/web/app_dev.php/api/users/login" ;
        HttpRequestsApi api = new HttpRequestsApi( url ) ;
        String reponse      = api.post("username=" + login_name + "&password=" + login_pass);
        System.out.println(reponse );
        if(reponse.equalsIgnoreCase("0")){
            Toast toast = Toast.makeText(this, "Authendification échoué, veuillez réessayer svp", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            JSONObject json = new JSONObject(reponse);
            String str_valueId = json.getString("id");// on récupère l'id de l'user
            String str_valueUsername = json.getString("username");// on récupère l'id de l'user
            String str_valueEmail = json.getString("email");// on récupère l'id de l'user

            String tempreponse = str_valueId;
            System.out.println("id" + str_valueId);
            // Creating user login session
            session.createLoginSession(str_valueId,str_valueUsername, str_valueEmail);
            startActivity(new Intent(this, TravelViewActivity.class));


        }



    }
}
