package subwaybuddy.miage.paris10.com.subapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import subwaybuddy.miage.paris10.com.subapp.libraries.HttpRequestsApi;

public class TravelViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_view);

        final ListView lstView1 = (ListView)findViewById(R.id.listView1);
        String reponse          = null ;
        HttpRequestsApi api     = null;
        try {
            // TODO: 02/12/2015 changer l'id de l'user en dynamique en passant par la session Manager 
            String url      = "http://christian-hiroz.com/SubwayBuddy/web/app_dev.php/api/users/1/travel" ;
            api             = new HttpRequestsApi( url );
            reponse         = api.get();
            JSONArray data = new JSONArray(reponse);
            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("id", c.getString("id"));
                map.put("name", c.getString("name"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(TravelViewActivity.this, MyArrList, R.layout.activity_column,
                    new String[] {"id", "name"}, new int[] {R.id.ColMemberID, R.id.ColName});
            lstView1.setAdapter(sAdap);

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
            // OnClick Item
            lstView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String sMemberID = MyArrList.get(position).get("id")
                            .toString();
                    String sName = MyArrList.get(position).get("name")
                            .toString();

                    viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                    viewDetail.setTitle("Detail du trajet");
                    viewDetail.setMessage("ID : " + sMemberID + "\n"
                            + "Nom : " + sName + "\n");
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    viewDetail.show();

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println(reponse);

    }
}
