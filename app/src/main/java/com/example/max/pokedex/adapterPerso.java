package com.example.max.pokedex;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by max on 19/06/2015.
 */
public class adapterPerso extends ArrayAdapter<String> {
        private final Activity context;
        private final String[] web;
        private final String[] imageId;
        public adapterPerso(Activity context,String[] web, String[] imageId) {
            super(context, R.layout.list_single, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            imgReader imgg = new imgReader(imageView);
            imgg.execute(imageId[position]);
            imageView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            txtTitle.setText(web[position]);
            return rowView;
        }
}
