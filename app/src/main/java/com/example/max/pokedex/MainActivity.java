package com.example.max.pokedex;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    JSONObject rec = null;
    JSONArray json = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonReader jsonReader = new jsonReader();
        try {
            dataHolder.getInstance().setDonnees(0,jsonReader.execute("http://92.222.9.170/PokedexApi/getAllTypesApi.php").get());
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
        json = dataHolder.getInstance().getDonnees(0);
        TableLayout tableLayout = new TableLayout(this);
        for (int i = 0; i < json.length(); ++i) {
            this.rec = json.getJSONObject(i);
            TableRow tr = new TableRow(this);
            final Button btn = new Button(this);
            btn.setText(this.rec.getString("1"));
            btn.setTag(this.rec.getString("1"));
            btn.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.MATCH_PARENT,1f));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Type", (String) view.getTag());
                    Intent intent = new Intent(MainActivity.this, Pokemons.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            tr.addView(btn);
            tableLayout.addView(tr);
        }
        ScrollView sv = new ScrollView(this);
        sv.addView(tableLayout);
        setContentView(sv);
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
