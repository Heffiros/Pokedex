package com.example.max.pokedex;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Pokemons extends Activity {

    String Types;
    JSONObject rec = null;
    JSONArray json = null;
    ListView list;

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
            this.json = jsonReader.execute("http://92.222.9.170/PokedexApi/getAllPokemonByTypes.php?type="+Types).get();
            dataHolder.getInstance().setDonnees(1, this.json);
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
        String[] web = new String[json.length()];
        String[] Image = new String[json.length()];
        final String[] ID = new String[json.length()];
        for(int i = 0; i < json.length(); i++) {
            this.rec = json.getJSONObject(i);
            web[i] = rec.getString("identifier_fr");
            Image[i] = rec.getString("lien");
            ID[i] = rec.getString("id");
        }


        adapterPerso adapter = new adapterPerso(Pokemons.this, web, Image);
        list=(ListView)findViewById(R.id.List);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("Id", (String) ID[+ position]);
                Intent intent = new Intent(Pokemons.this, Detail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
