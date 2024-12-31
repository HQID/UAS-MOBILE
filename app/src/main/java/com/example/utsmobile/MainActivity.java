package com.example.utsmobile;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private EditText etSearch;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tv_username);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            firestore.collection("users").document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            tvUsername.setText("Welcome, " + username);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MainActivity", "Failed to fetch username: " + e.getMessage());
                    });
        }


        etSearch = findViewById(R.id.search_input);

        // Initialize views
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        // Set up adapter and fragments
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentRecommended(), "Recommend");
        adapter.addFragment(new FragmentRamen(), "Food");
        adapter.addFragment(new FragmentIceCream(), "Dessert");
        adapter.addFragment(new FragmentDrink(), "Drink");
        adapter.addFragment(new FragmentFavorite(), "Favorite");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.recommendation);
        tabLayout.getTabAt(1).setIcon(R.drawable.food);
        tabLayout.getTabAt(2).setIcon(R.drawable.dessert);
        tabLayout.getTabAt(3).setIcon(R.drawable.drink);
        tabLayout.getTabAt(4).setIcon(R.drawable.favourite);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            layoutParams.setMargins(20, 0, 20, 0); // Margin kiri, atas, kanan, bawah
            tab.requestLayout();
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (!keyword.isEmpty()) {
                    FragmentRamen fragmentRamen = (FragmentRamen) adapter.getItem(1);
                    if (fragmentRamen != null) {
                        fragmentRamen.searchMakanan(keyword);
                    }
                } else {
                    FragmentRamen fragmentRamen = (FragmentRamen) adapter.getItem(1);
                    if (fragmentRamen != null) {
                        fragmentRamen.showAllMakanan();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}