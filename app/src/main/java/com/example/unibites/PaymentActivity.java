package com.example.unibites;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PaymentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment); // Your layout file for the reorder screen

        // Set the ActionBar title and enable the back icon in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Reorder");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back icon in the ActionBar
        }

        // Find the custom ImageView for the back icon
        ImageView backIcon = findViewById(R.id.back_icon);

        // Set a click listener to finish the activity when the back icon is clicked
        backIcon.setOnClickListener(v -> {
            finish(); // Go back to MainActivity (Order History)
        });
        
        // Setup footer navigation
        setupFooterNavigation();
    }

    // This is the default back navigation method in case you want to use the ActionBar back button
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Go back to MainActivity (Order History)
        return true;
    }
}
