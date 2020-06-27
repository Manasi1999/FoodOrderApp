package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cart_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //Navigation Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //Recycler View
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<CartItem> list;
    DatabaseReference myDatabase;

    Button clear,order;
    TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cart_activity);

        // Navigation Drawer
        /* Hooks */
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        toolbar = findViewById(R.id.toolbar22);
        /* Toolbar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*Navigation Drawer Menu*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        /*Database Reference*/
        myDatabase= FirebaseDatabase.getInstance().getReference("Cart");
        /*Recycler View*/
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view2);
        setRecyclerView();

        //Button Functionality
        clear = findViewById(R.id.clear);
        order = findViewById(R.id.order);
        empty = findViewById(R.id.empty);
        if(list.size()==0)  empty.setText("Empty Cart");
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==0)
                    Toast.makeText(getApplicationContext(),"Your Cart is empty",Toast.LENGTH_LONG).show();
                else{
                    clearCart(myDatabase);
                    Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Clear the cart*/
                clearCart(myDatabase);
            }
        });

    }

    public void setRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    public void clearCart(DatabaseReference databaseReference){
            databaseReference.setValue(null);
            list.clear();
            setRecyclerView();
            empty.setText("Empty Cart");
    }



    @Override
    protected void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    CartItem content = item.getValue(CartItem.class);
                    list.add(content);
                    empty.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_menu:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            case R.id.nav_cart:
                break;
            case R.id.nav_exit:
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("Exit me", true);
                startActivity(intent1);
                clearCart(myDatabase);
                finish();
                finishAffinity();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
