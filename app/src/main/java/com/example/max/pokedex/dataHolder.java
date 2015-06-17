package com.example.max.pokedex;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by max on 09/05/2015.
 */
public class dataHolder {
    private JSONArray races;
    private JSONArray pokemons;

    public JSONArray getDonnees(Integer type) {
        switch (type){
            case 0:
                return this.races;
            case 1:
                return this.pokemons;
            default:
                return null;
        }
    }

    public void setDonnees(Integer type, JSONArray donnees) {
        switch (type){
            case 0:
                this.races =  donnees;
            case 1:
                 this.pokemons =  donnees;
            default:
        }
    }

    public void populate(JSONArray data, int nb) throws JSONException {
        dataHolder.getInstance().setDonnees(nb, data);
    }

    private static final dataHolder holder = new dataHolder();
    public static dataHolder getInstance() {return holder;}
}
