package com.example.utsmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListMakananAdapter extends RecyclerView.Adapter<ListMakananAdapter.ListViewHolder> {
    private ArrayList<Makanan> listMakanan;

    public ListMakananAdapter(ArrayList<Makanan> list) {
        this.listMakanan = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_makanan, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Makanan makanan = listMakanan.get(position);
        holder.tvName.setText(makanan.getName());
        holder.tvDescription.setText(makanan.getDescription());

        // Menggunakan Glide untuk memuat gambar dari URL
        Glide.with(holder.itemView.getContext())
                .load(makanan.getImageUrl()) // URL gambar dari API
                .into(holder.imgPhoto);

        // Menambahkan aksi ketika tombol "More..." diklik
        holder.btnDetails.setOnClickListener(v -> {
            // Membuat Intent untuk mengirim data ke DetailRecipesActivity
            Intent intent = new Intent(holder.itemView.getContext(), DetailRecipesActivity.class);

            // Mengirimkan data
            intent.putExtra("recipe_name", makanan.getName());
            intent.putExtra("recipe_health_score", makanan.getHealthScore());
            intent.putExtra("recipe_image_url", makanan.getImageUrl());
            intent.putExtra("recipe_health_score", makanan.getHealthScore());  // Mengirimkan healthScore
            intent.putExtra("recipe_ready_in_minutes", makanan.getReadyInMinutes());  // Mengirimkan readyInMinutes
            intent.putExtra("recipe_servings", makanan.getServings());  // Mengirimkan servings
            intent.putStringArrayListExtra("ingredients", makanan.getIngredients());
            intent.putExtra("instructions", makanan.getInstructions());
            holder.itemView.getContext().startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return listMakanan.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView btnDetails;
        ImageView imgPhoto;
        TextView tvName, tvDescription;

        public ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            btnDetails = itemView.findViewById(R.id.btn_details);
        }
    }
}