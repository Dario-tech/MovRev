package com.example.yourmovieopinion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmovieopinion.objetos.Review;

import java.util.ArrayList;

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.ViewHolderReviews> {

    ArrayList<Review> listaReviews;

    public ReviewsAdapter(ArrayList<Review> listaReviews) {
        this.listaReviews = listaReviews;
    }

    @Override
    public ViewHolderReviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviews = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_reviews,null,false);
        return new ViewHolderReviews(reviews);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReviews holder, int position) {
        holder.asignarDatos(listaReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return listaReviews.size();
    }

    public class ViewHolderReviews extends RecyclerView.ViewHolder {

        TextView usuario;
        TextView fecha;
        TextView titulo;
        TextView review;

        public ViewHolderReviews(@NonNull View itemView) {
            super(itemView);

            usuario=(TextView) itemView.findViewById(R.id.tv_user_name);
            titulo=(TextView) itemView.findViewById(R.id.tv_titulo_review);
            fecha=(TextView) itemView.findViewById(R.id.tv_fecha_review);
            review=(TextView) itemView.findViewById(R.id.tv_review);
        }

        public void asignarDatos(Review review){
            usuario.setText(review.getUsuario());
            titulo.setText(review.getTitulo());
            fecha.setText(review.getFecha().toString());
            this.review.setText(review.getReview());
        }
    }
}
