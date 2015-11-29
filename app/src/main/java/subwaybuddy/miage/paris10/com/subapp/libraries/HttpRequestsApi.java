package subwaybuddy.miage.paris10.com.subapp.libraries;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jb on 28/11/15.
 */
public class HttpRequestsApi
{
    private URL url ;
    private HttpURLConnection connection ;

    public HttpRequestsApi( String url_adresse ) throws MalformedURLException, IOException
    {
        // petit workaround
        // @TODO : faire les requêtes HTTP en asynchrone ! (cf "Async")
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL obj                 = new URL( url_adresse );
        this.url                = obj ;
        HttpURLConnection con   = (HttpURLConnection) obj.openConnection();
        this.connection         = con ;
    }

    /**
     * Effectue une requête GET sur une PAGE.
     * @return
     * @throws IOException
     */
    public String get() throws IOException
    {
        StringBuilder res   = new StringBuilder() ;

        // méthode GET.
        this.connection.setRequestMethod( "GET" ) ;

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        this.connection.getInputStream()
                )
        );

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ( (inputLine = in.readLine()) != null )
        {
            res.append(inputLine);
        }
        in.close();

        return res.toString() ;
    }

    /**
     * Effectue une requête POST sur une page.
     * @param post_params
     * @return
     */
    public String post( String post_params ) throws IOException
    {
        HttpURLConnection con = this.connection ;
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "fr-FR");

        // envoie la requête POST.
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream( con.getOutputStream() );
        wr.writeBytes(post_params);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream()
                )
        );

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString() ;
    }
}

