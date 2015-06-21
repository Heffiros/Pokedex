package com.example.max.pokedex;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Detail extends ActionBarActivity {


    String Id;
    JSONObject rec = null;
    JSONArray json = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle objectbundle = this.getIntent().getExtras();
        if (objectbundle != null) {
            Id = objectbundle.getString("Id");
        }

        jsonReader jsonReader = new jsonReader();
        try {
            this.json = jsonReader.execute("http://92.222.9.170/PokedexApi/GetDataWithPokemonId.php?id="+Id).get();
            datasReceive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void datasReceive() throws JSONException {
        JSONObject test = json.getJSONObject(0);
        TextView desc = (TextView) findViewById(R.id.toto);
        TextView stat1 = (TextView) findViewById(R.id.stat1);
        TextView stat2 = (TextView) findViewById(R.id.stat2);
        TextView stat3 = (TextView) findViewById(R.id.stat3);
        TextView stat4 = (TextView) findViewById(R.id.stat4);
        TextView stat5 = (TextView) findViewById(R.id.stat5);

        desc.setText(test.getString("description"));
        ImageView imgVw = (ImageView)findViewById(R.id.imageView);
        imgReader imgg = new imgReader(imgVw);
        imgg.execute(test.getString("img_gif"));
        imgVw.setScaleX(10);
        imgVw.setScaleY(10);

        stat1.setText("PV : " + test.getString("stat1"));
        stat2.setText("ATT : " + test.getString("stat2"));
        stat3.setText("DEF : " + test.getString("stat3"));
        stat4.setText("ATT SPE : " + test.getString("stat4"));
        stat5.setText("DEF SPE : " + test.getString("stat5"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
