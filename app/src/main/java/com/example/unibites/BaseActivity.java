package com.example.unibites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupFooterNavigation() {
        ImageButton navHome = findViewById(R.id.nav_home);
        ImageButton navFood = findViewById(R.id.nav_food);
        ImageButton navCart = findViewById(R.id.nav_cart);
        ImageButton navHistory = findViewById(R.id.nav_history);

        if (navHome != null) {
            navHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to Home page
                    if (!(BaseActivity.this instanceof HomeActivity)) {
                        Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
        }

        if (navFood != null) {
            navFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to food menu page using the new FoodMenuActivity
                    if (!(BaseActivity.this instanceof FoodMenuActivity)) {
                        Intent intent = new Intent(BaseActivity.this, FoodMenuActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        if (navCart != null) {
            navCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to CartActivity
                    try {
                        Log.d(TAG, "Navigating to CartActivity from footer");
                        Intent intent = new Intent(BaseActivity.this, CartActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "Error navigating to cart from footer", e);
                        Toast.makeText(BaseActivity.this, "Error opening cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        
                        // Fallback to under development screen if there's an error
                        Intent intent = new Intent(BaseActivity.this, UnderDevelopmentActivity.class);
                        intent.putExtra("activity_name", "Cart");
                        startActivity(intent);
                    }
                }
            });
        }

        if (navHistory != null) {
            navHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to order history page
                    if (!(BaseActivity.this instanceof OrderHistoryActivity)) {
                        Intent intent = new Intent(BaseActivity.this, OrderHistoryActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
} 