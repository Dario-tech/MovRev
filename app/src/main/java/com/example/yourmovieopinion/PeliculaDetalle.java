package com.example.yourmovieopinion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;

public class PeliculaDetalle extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Review> listaReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula_detalle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rv_Reviews);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        View popup_is = (View) findViewById(R.id.ly_tarjeta_iniciosesion);
        View oscuridad = findViewById(R.id.ly_oscuridad);
        ImageView exit_popup = (ImageView) findViewById(R.id.iv_salir);
        //Pulsa cruz para quitar popup
        exit_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_is.setVisibility(View.GONE);
                oscuridad.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void mostrarResenas(View view){
        rellenarResenas();
        recyclerView.setVisibility(View.VISIBLE);
        ReviewsAdapter adapter = new ReviewsAdapter(listaReviews);

        recyclerView.setAdapter(adapter);

    }

    private void rellenarResenas(){
        listaReviews=new ArrayList<Review>();
        for (int i=0;i<5;i++)
            listaReviews.add(new Review("@usuario "+i,"Título "+i,"Review "+i, new Date()));
    }

    public void escribirResena(View view){
        View popup_is = findViewById(R.id.ly_tarjeta_iniciosesion);
        View oscuridad = findViewById(R.id.ly_oscuridad);
        popup_is.setVisibility(View.VISIBLE);
        oscuridad.setVisibility(View.VISIBLE);
        popup_is.bringToFront();
    }

    public void toRegistrarse(View view){

        Button crearCuenta = (Button) findViewById(R.id.bt_registrarse_iniciosesion);
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}