package com.example.unibites;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Activity to handle reordering of previous orders.
 * It shows order details and asks for confirmation before adding items to cart.
 */
public class ReorderActivity extends BaseActivity {
    private static final String TAG = "ReorderActivity";
    
    private ImageButton mBackButton;
    private TextView mOrderIdTextView;
    private TextView mOrderItemsTextView;
    private TextView mOrderAmountTextView;
    private ImageView mOrderImageView;
    private Button mAddToCartButton;
    private Button mCancelButton;
    
    private String mOrderId;
    private String mOrderItems;
    private double mOrderAmount;
    private String mOrderImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder);
        
        // Get order details from intent
        Intent intent = getIntent();
        if (intent != null) {
            mOrderId = intent.getStringExtra("order_id");
            mOrderItems = intent.getStringExtra("order_items");
            mOrderAmount = intent.getDoubleExtra("order_amount", 0.0);
            mOrderImage = intent.getStringExtra("order_image");
            
            Log.d(TAG, "Reordering: " + mOrderItems);
        }
        
        // Initialize views
        initViews();
        
        // Setup actions
        setupListeners();
        
        // Setup footer navigation
        setupFooterNavigation();
    }
    
    private void initViews() {
        // Find views
        mBackButton = findViewById(R.id.backButton);
        mOrderIdTextView = findViewById(R.id.orderIdTextView);
        mOrderItemsTextView = findViewById(R.id.orderItemsTextView);
        mOrderAmountTextView = findViewById(R.id.orderAmountTextView);
        mOrderImageView = findViewById(R.id.orderImageView);
        mAddToCartButton = findViewById(R.id.addToCartButton);
        mCancelButton = findViewById(R.id.cancelButton);
        
        // Set order details
        if (mOrderId != null) {
            mOrderIdTextView.setText("Order #" + mOrderId);
        }
        
        if (mOrderItems != null) {
            mOrderItemsTextView.setText(mOrderItems);
        }
        
        mOrderAmountTextView.setText(String.format("₹%.2f", mOrderAmount));
        
        // Set image if available
        if (mOrderImage != null && !mOrderImage.isEmpty()) {
            try {
                Resources resources = getResources();
                int resourceId = resources.getIdentifier(
                    mOrderImage, "drawable", getPackageName());
                
                if (resourceId != 0) {
                    mOrderImageView.setImageResource(resourceId);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading image: " + e.getMessage());
            }
        }
    }
    
    private void setupListeners() {
        // Back button returns to previous screen
        mBackButton.setOnClickListener(v -> onBackPressed());
        
        // Cancel button returns to previous screen
        mCancelButton.setOnClickListener(v -> onBackPressed());
        
        // Add to cart button adds items to cart and navigates to cart
        mAddToCartButton.setOnClickListener(v -> {
            Toast.makeText(this, "Items added to cart", Toast.LENGTH_SHORT).show();
            
            // In a real app, this would add the items to cart in a database
            // For now, just navigate to the cart
            Intent intent = new Intent(ReorderActivity.this, CartActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        });
    }
}
