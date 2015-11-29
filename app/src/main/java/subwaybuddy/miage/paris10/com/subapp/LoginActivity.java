package subwaybuddy.miage.paris10.com.subapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.MalformedURLException;

import subwaybuddy.miage.paris10.com.subapp.libraries.HttpRequestsApi;

public class LoginActivity extends AppCompatActivity
{
    EditText ET_NAME,ET_PASS;
    String login_name,login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_NAME = (EditText)findViewById(R.id.user_name);
        ET_PASS = (EditText)findViewById(R.id.user_pass);

    }

    public void userReg(View view)
    {
        startActivity(new Intent(this,Register.class));
    }

    public void userLogin(View view) throws IOException
    {
        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        //BackgroundTask backgroundTask = new BackgroundTask(this);
        //backgroundTask.execute( "login", login_name, login_pass );
        // on POST le contenu des champs textes pour authentifier l'utilisateur.
        String url          = "http://christian-hiroz.com/SubwayBuddy/web/app_dev.php/users/login" ;
        HttpRequestsApi api = new HttpRequestsApi( url ) ;
        String reponse      = api.post( "username=" + login_name + "&password=" + login_pass );
        // @TODO Kuga : parser le JSON pour voir le résultat
        // par exemple, si tu trouves "id" dans le JSON, c'est que l'utilisateur est authentifié
        // dans ce cas, préparer la suite.
        System.out.println( reponse );
    }
}
