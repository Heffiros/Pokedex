package com.example.max.pokedex;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Pokemons extends ActionBarActivity {

    String Types;
    JSONObject rec = null;
    JSONArray json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        Bundle objectbundle = this.getIntent().getExtras();
        if (objectbundle != null) {
            Types = objectbundle.getString("Type");
        }

        jsonReader jsonReader = new jsonReader();
        try {
            dataHolder.getInstance().setDonnees(1,jsonReader.execute("http://92.222.9.170/PokedexApi/getAllPokemonByTypes.php?type="+Types).get());
            datasReceive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void datasReceive() throws JSONException {
        json = dataHolder.getInstance().getDonnees(1);
        TableLayout tableLayout = new TableLayout(this);
        for (int i = 0; i < json.length(); ++i) {
            this.rec = json.getJSONObject(i);
            TableRow tr = new TableRow(this);
            final ImageView img = new ImageView(this);
            imgReader imgg = new imgReader(img);
            imgg.execute(this.rec.getString("lien"));
            img.setScaleX(1);
            img.setScaleY(1);
            img.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            tr.addView(img);
            final Button btn = new Button(this);
            btn.setText(this.rec.getString("identifier_fr"));
            btn.setTag(this.rec.getString("id"));
            btn.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Id", (String) view.getTag());
                    Intent intent = new Intent(Pokemons.this, Detail.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pokemons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
