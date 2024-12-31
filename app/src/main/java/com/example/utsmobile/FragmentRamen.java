package com.example.utsmobile;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRamen extends Fragment {
    private RecyclerView rvMakanan;
    private ArrayList<Makanan> list = new ArrayList<>();
    private ListMakananAdapter adapter;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ramen, container, false);

        rvMakanan = view.findViewById(R.id.rv_makanan);
        rvMakanan.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new ListMakananAdapter(list);
        rvMakanan.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchMakananFromApi(); // Panggil data dari API

        return view;
    }

    public void searchMakanan(String keyword) {
        ArrayList<Makanan> filteredList = new ArrayList<>();
        for (Makanan makanan : list) {
            if (makanan.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(makanan);
            }
        }
        adapter = new ListMakananAdapter(filteredList);
        rvMakanan.setAdapter(adapter);
    }

    public void showAllMakanan() {
        adapter = new ListMakananAdapter(list);
        rvMakanan.setAdapter(adapter);
    }

    public void fetchMakananFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.getRandomRecipes(6, "asian,main-course", "2d4e18e8522f4be3995d74bf74846d54");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Ambil data dari response dan masukkan ke list makanan
                    for (ApiResponse.Recipe recipe : response.body().getRecipes()) {
                        Makanan makanan = new Makanan();
                        makanan.setName(recipe.getTitle());
                        makanan.setDescription(recipe.getSummary());
                        String cleanedSummary = recipe.removeHtmlTagsWithJsoup(recipe.getSummary());
                        makanan.setDescription(cleanedSummary);
                        // Ambil gambar dari URL
                        // Bisa menggunakan Glide atau Picasso untuk loading gambar dari URL
                        makanan.setImageUrl(recipe.getImage());

                        makanan.setHealthScore(recipe.getHealthScore()); // Menambahkan healthScore
                        makanan.setReadyInMinutes(recipe.getReadyInMinutes()); // Menambahkan readyInMinutes
                        makanan.setServings(recipe.getServings()); // Menambahkan servings

                        List<Ingredient> ingredients = recipe.getExtendedIngredients();
                        ArrayList<String> ingredientList = new ArrayList<>();
                        for (Ingredient ingredient : ingredients) {
                            ingredientList.add(ingredient.getOriginal());
                        }
                        makanan.setIngredients(ingredientList);
                        makanan.setInstructions(recipe.getInstructions());

                        list.add(makanan);
                    }
                    adapter = new ListMakananAdapter(list);
                    rvMakanan.setAdapter(adapter);
                }
                else {
                    Log.e("API Error", "Response tidak berhasil atau body null");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());

            }
        });
    }


    private void showRecyclerList() {
        // Gunakan requireContext() untuk mendapatkan Context
        rvMakanan.setLayoutManager(new LinearLayoutManager(requireContext()));
        ListMakananAdapter listMakananAdapter = new ListMakananAdapter(list);
        rvMakanan.setAdapter(listMakananAdapter);
    }

}
