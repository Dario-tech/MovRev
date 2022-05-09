package com.example.yourmovieopinion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarteleraActivity extends AppCompatActivity {

    RecyclerView recyclerViewCartelera;
    ArrayList<Pelicula> listapeliculas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartelera);

        String queCartelera = getIntent().getStringExtra("cartelera");
        recyclerViewCartelera = (RecyclerView) findViewById(R.id.rv_Cartelera);
        recyclerViewCartelera.setLayoutManager(new GridLayoutManager(this, 2));

        rellenarCartelera(queCartelera);
        //CarteleraAdapter adapter = new CarteleraAdapter(listapeliculas);
        //recyclerViewCartelera.setAdapter(adapter);
    }

    public void rellenarCartelera(String queCartelera) {
        listapeliculas = new ArrayList<Pelicula>();
        //for (int i=0;i<5;i++)
        //    listapeliculas.add(new Pelicula("Avengers: Endgame",R.drawable.engame));
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
                                                    listapeliculas.add(new Pelicula(pelicula.getString("title"), link_poster));

                                                    System.out.println(pelicula.getString("title") + " añadida.");
                                                    System.out.println(link_poster);
                                                    CarteleraAdapter adapter = new CarteleraAdapter(listapeliculas);
                                                    recyclerViewCartelera.setAdapter(adapter);
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

    public void toPeliculaDetalle(View view) {
        Intent intent = new Intent(this, PeliculaDetalle.class);
        startActivity(intent);
    }
}
