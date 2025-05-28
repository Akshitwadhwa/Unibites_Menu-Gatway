package com.example.unibites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.unibites.adapter.CartAdapter;
// import com.example.unibites.firebase.FirebaseManager;
import com.example.unibites.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements CartAdapter.OnQuantityChangeListener {
    private static final String TAG = "CartActivity";

    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mEmptyCartView;
    private Button mMakePaymentButton;
    private ImageButton mBackButton;
    
    // private FirebaseManager mFirebaseManager;
    private List<CartItem> mCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Log.d(TAG, "Starting CartActivity onCreate");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cart);
            
            // Initialize Firebase manager
            // mFirebaseManager = FirebaseManager.getInstance();
            
            // Initialize the cart items list
            try {
                mCartItems = new ArrayList<>();
                Log.d(TAG, "Cart items list initialized");
            } catch (Exception e) {
                Log.e(TAG, "Error initializing cart items list", e);
                Toast.makeText(this, "Error initializing cart", Toast.LENGTH_SHORT).show();
            }
            
            // Initialize views
            try {
                initViews();
                Log.d(TAG, "Views initialized");
            } catch (Exception e) {
                Log.e(TAG, "Error initializing views", e);
                Toast.makeText(this, "Error setting up UI", Toast.LENGTH_SHORT).show();
            }
            
            // Set up RecyclerView
            try {
                setupRecyclerView();
                Log.d(TAG, "RecyclerView set up successfully");
            } catch (Exception e) {
                Log.e(TAG, "Error setting up RecyclerView", e);
                Toast.makeText(this, "Error setting up cart items", Toast.LENGTH_SHORT).show();
            }
            
            // Load cart items from Firebase
            try {
                loadCartItems();
                Log.d(TAG, "Cart items loading initiated");
            } catch (Exception e) {
                Log.e(TAG, "Error loading cart items", e);
                Toast.makeText(this, "Error loading items", Toast.LENGTH_SHORT).show();
            }
            
            Log.d(TAG, "CartActivity onCreate completed");
            
            // Setup footer navigation
            setupFooterNavigation();
        } catch (Exception e) {
            Log.e(TAG, "Fatal error in CartActivity onCreate", e);
            Toast.makeText(this, "Error initializing cart: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish(); // Return to previous activity safely
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the cart items when returning to this activity
        if (mCartAdapter != null) {
            loadCartItems();
        }
    }
    
    private void initViews() {
        try {
            mCartRecyclerView = findViewById(R.id.cartRecyclerView);
            if (mCartRecyclerView == null) {
                Log.e(TAG, "Cart RecyclerView not found in layout!");
            }
            
            mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            mEmptyCartView = findViewById(R.id.emptyCartView);
            mMakePaymentButton = findViewById(R.id.makePaymentButton);
            mBackButton = findViewById(R.id.backButton);
            
            // Set up swipe refresh
            mSwipeRefreshLayout.setOnRefreshListener(this::loadCartItems);
            
            // Set up back button
            mBackButton.setOnClickListener(v -> onBackPressed());
            
            // Set up make payment button
            mMakePaymentButton.setOnClickListener(v -> {
                if (mCartItems.isEmpty()) {
                    Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to payment activity
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error in initViews", e);
            throw e; // Re-throw to be caught by the main try-catch
        }
    }
    
    private void setupRecyclerView() {
        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mCartRecyclerView.setLayoutManager(layoutManager);
            mCartAdapter = new CartAdapter(this, mCartItems, this);
            mCartRecyclerView.setAdapter(mCartAdapter);
            
            // Add decoration to separate items
            // This adds a line between the items if needed
            // mCartRecyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        } catch (Exception e) {
            Log.e(TAG, "Error in setupRecyclerView", e);
            throw e; // Re-throw to be caught by the main try-catch
        }
    }
    
    private void loadCartItems() {
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            
            // Commenting out Firebase implementation
            /*
            mFirebaseManager.getCartItems(new FirebaseManager.OnCartItemsListener() {
                @Override
                public void onCartItemsLoaded(List<CartItem> cartItems) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mCartItems.clear();
                    mCartItems.addAll(cartItems);
                    mCartAdapter.notifyDataSetChanged();
                    
                    // Show empty view if cart is empty
                    if (mCartItems.isEmpty()) {
                        mEmptyCartView.setVisibility(View.VISIBLE);
                        mMakePaymentButton.setEnabled(false);
                    } else {
                        mEmptyCartView.setVisibility(View.GONE);
                        mMakePaymentButton.setEnabled(true);
                    }
                }
                
                @Override
                public void onError(String error) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(CartActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            });
            */
            
            // Adding sample data instead of loading from Firebase
            mSwipeRefreshLayout.setRefreshing(false);
            mCartItems.clear();
            
            // Add sample items - THIS IS HARDCODED FOR NOW AND SHOULD BE REPLACED WITH FIREBASE LATER
            mCartItems.add(new CartItem("1", "Chole Bhature", 150.00, 1, ""));
            mCartItems.add(new CartItem("2", "Chocolate Shake", 60.00, 2, ""));
            mCartItems.add(new CartItem("3", "Cold Coffee", 80.00, 1, ""));
            
            Log.d(TAG, "Added " + mCartItems.size() + " sample items to cart");
            
            if (mCartAdapter != null) {
                mCartAdapter.notifyDataSetChanged();
                Log.d(TAG, "Notified adapter of data change");
            } else {
                Log.e(TAG, "Cart adapter is null when trying to update");
            }
            
            // Update view based on cart contents
            if (mCartItems.isEmpty()) {
                Log.d(TAG, "Cart is empty, showing empty view");
                if (mEmptyCartView != null) {
                    mEmptyCartView.setVisibility(View.VISIBLE);
                }
                if (mMakePaymentButton != null) {
                    mMakePaymentButton.setEnabled(false);
                }
            } else {
                Log.d(TAG, "Cart has items, hiding empty view");
                if (mEmptyCartView != null) {
                    mEmptyCartView.setVisibility(View.GONE);
                }
                if (mMakePaymentButton != null) {
                    mMakePaymentButton.setEnabled(true);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in loadCartItems", e);
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            Toast.makeText(this, "Error loading cart items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onQuantityIncreased(CartItem item, int position) {
        try {
            // Update item quantity
            item.setQuantity(item.getQuantity() + 1);
            mCartAdapter.notifyItemChanged(position);
            
            // Update in Firebase
            // updateCartItemInFirebase(item);
        } catch (Exception e) {
            Log.e(TAG, "Error increasing quantity", e);
            Toast.makeText(this, "Error updating quantity", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onQuantityDecreased(CartItem item, int position) {
        try {
            // Update item quantity
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                mCartAdapter.notifyItemChanged(position);
                
                // Update in Firebase
                // updateCartItemInFirebase(item);
            } else {
                // Remove item if quantity becomes 0
                /*
                mFirebaseManager.removeFromCart(item.getItemId(), new FirebaseManager.OnCartOperationListener() {
                    @Override
                    public void onSuccess(String message) {
                        mCartItems.remove(position);
                        mCartAdapter.notifyItemRemoved(position);
                        mCartAdapter.notifyItemRangeChanged(position, mCartItems.size());
                        
                        // Show empty view if cart is now empty
                        if (mCartItems.isEmpty()) {
                            mEmptyCartView.setVisibility(View.VISIBLE);
                            mMakePaymentButton.setEnabled(false);
                        }
                    }
                    
                    @Override
                    public void onError(String error) {
                        Toast.makeText(CartActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
                */
                
                // Directly remove the item (without Firebase)
                mCartItems.remove(position);
                mCartAdapter.notifyItemRemoved(position);
                mCartAdapter.notifyItemRangeChanged(position, mCartItems.size());
                
                // Show empty view if cart is now empty
                if (mCartItems.isEmpty()) {
                    mEmptyCartView.setVisibility(View.VISIBLE);
                    mMakePaymentButton.setEnabled(false);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error decreasing quantity", e);
            Toast.makeText(this, "Error updating quantity", Toast.LENGTH_SHORT).show();
        }
    }
    
    /*
    private void updateCartItemInFirebase(CartItem item) {
        mFirebaseManager.updateCartItem(item, new FirebaseManager.OnCartOperationListener() {
            @Override
            public void onSuccess(String message) {
                // Item updated successfully
            }
            
            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                // Reload cart to ensure consistency
                loadCartItems();
            }
        });
    }
    */
} 