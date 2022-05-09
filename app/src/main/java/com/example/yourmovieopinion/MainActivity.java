package com.example.yourmovieopinion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewCartelera_encines;
    RecyclerView recyclerViewCartelera_proximamente;
    ArrayList<Pelicula> listapeliculas_encines;
    ArrayList<Pelicula> listapeliculas_proximamente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewCartelera_encines = (RecyclerView) findViewById(R.id.rv_EnCines);
        recyclerViewCartelera_encines.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        recyclerViewCartelera_proximamente = (RecyclerView) findViewById(R.id.rv_Proximamente);
        recyclerViewCartelera_proximamente.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));

        rellenarCartelera("encines");
        rellenarCartelera("proximamente");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.foto_perfil);
        View view = MenuItemCompat.getActionView(menuItem);
        ShapeableImageView fotoPerfil = view.findViewById(R.id.toolbar_fotoperfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Foto de perfil", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.foto_perfil:
//                Toast.makeText(this, "Foto de perfil", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void rellenarCartelera(String queCartelera) {
        listapeliculas_encines = new ArrayList<Pelicula>();
        listapeliculas_proximamente = new ArrayList<Pelicula>();

        final String URL_enCines = "https://imdb-api.com/en/API/InTheaters/k_p0v28841";
        final String URL_proximamente = "https://imdb-api.com/en/API/ComingSoon/k_p0v28841";
        final String URL_poster = "https://imdb-api.com/en/API/Posters/k_p0v28841/";

        final String URL_cartelera = (queCartelera.equals("encines")) ? URL_enCines : URL_proximamente;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_cartelera, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Se ha recibido una respuesta
                            Toast.makeText(getApplicationContext(),
                                    "Películas recibidas",
                                    Toast.LENGTH_SHORT).show();
                            JSONArray lista_recibida = response.getJSONArray("items");
                            for (int i = 0; i < lista_recibida.length(); i++) {
                                JSONObject pelicula = lista_recibida.getJSONObject(i);
                                String id_pelicula = pelicula.getString("id");

                                JsonObjectRequest request_Poster = new JsonObjectRequest(Request.Method.GET, URL_poster + id_pelicula, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response_Poster) {
                                                try {
                                                    JSONArray lista_rec_posters = response_Poster.getJSONArray("posters");
                                                    JSONObject poster = lista_rec_posters.getJSONObject(0);
                                                    String link_poster = poster.getString("link");
                                                    if (queCartelera.equals("encines")) {
                                                        listapeliculas_encines.add(new Pelicula(pelicula.getString("title"), link_poster));
                                                        CarteleraAdapter adapter = new CarteleraAdapter(listapeliculas_encines);
                                                        recyclerViewCartelera_encines.setAdapter(adapter);
                                                    }
                                                    if (queCartelera.equals("proximamente")) {
                                                        listapeliculas_proximamente.add(new Pelicula(pelicula.getString("title"), link_poster));
                                                        CarteleraAdapter adapter = new CarteleraAdapter(listapeliculas_proximamente);
                                                        recyclerViewCartelera_proximamente.setAdapter(adapter);
                                                    }
                                                    System.out.println(pelicula.getString("title") + " añadida.");
                                                    System.out.println(link_poster);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.e("Error: ", error.getMessage());
                                        Toast.makeText(getApplicationContext(),
                                                "No se ha recibido respuesta del poster", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                ColaPeticionesHTTP.getInstance().getRequestQueue().add(request_Poster);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.v("Response:%n %s", response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "No se ha recibido respuesta de peliculas", Toast.LENGTH_SHORT).show();
            }
        });
        // add the request object to the queue to be executed
        ColaPeticionesHTTP.getInstance().getRequestQueue().add(request);
    }

    public void toCarteleraEnCines(View view) {
        Intent intent = new Intent(this, CarteleraActivity.class);
        intent.putExtra("cartelera", "encines");
        startActivity(intent);
    }

    public void toCarteleraProx(View view) {
        Intent intent = new Intent(this, CarteleraActivity.class);
        intent.putExtra("cartelera", "proximamente");
        startActivity(intent);
    }

    public void toPeliculaDetalle(View view) {
        Intent intent = new Intent(this, PeliculaDetalle.class);
        startActivity(intent);
    }

}