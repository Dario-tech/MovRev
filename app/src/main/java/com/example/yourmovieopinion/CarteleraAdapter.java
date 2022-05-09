package com.example.yourmovieopinion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarteleraAdapter extends RecyclerView.Adapter<CarteleraAdapter.ViewHolderPeliculas> {

    ArrayList<Pelicula> listaPeliculas;

    public CarteleraAdapter(ArrayList<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    @NonNull
    @Override
    public ViewHolderPeliculas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cartelera = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_cartelera,null,false);
        return new ViewHolderPeliculas(cartelera);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPeliculas holder, int position) {
        holder.asignarDatos(listaPeliculas.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }

    public class ViewHolderPeliculas extends RecyclerView.ViewHolder {

        ImageView caratula;
        TextView titulo;

        public ViewHolderPeliculas(@NonNull View itemView) {
            super(itemView);

            caratula=(ImageView) itemView.findViewById(R.id.item_caratula);
            titulo=(TextView) itemView.findViewById(R.id.item_titulo);
        }

        public void asignarDatos(Pelicula pelicula) {
            System.out.println(pelicula.getImagen());
            //Picasso.get().load(pelicula.getImagen()).resize(500, 750).centerCrop().into(caratula);
            Picasso.get().load(pelicula.getImagen()).resize(250, 375).centerCrop().into(caratula);
            titulo.setText(pelicula.getTitulo());
        }
    }
}
