package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Second extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView textViewName;
    TextView textViewPrice;
    TextView textViewDescription;
    EditText quantity;
    ImageButton btn1;
    ImageButton btn2;
    ImageView imageView;
    //Navigation Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //Database
    //Firebase
    private DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference("Cart");
    String name;
    String price;
    String description;
    String image;
    ArrayList<String> content = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        // Navigation Drawer
        /* Hooks */
        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.nav_view3);
        toolbar = findViewById(R.id.toolbar23);
        /* Toolbar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*Navigation Drawer Menu*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        /* Getting info from prev activity */
        //textView = findViewById(R.id.name);
        imageView = findViewById(R.id.image);
        textViewName = findViewById(R.id.name);
        textViewDescription = findViewById(R.id.description);
        textViewPrice = findViewById(R.id.item_price);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        quantity = findViewById(R.id.quantity);
        quantity.setText("1");
        content = (ArrayList<String>) getIntent().getExtras().get("ItemContent");
        name = content.get(0);
        price = content.get(1);
        description = content.get(2);
        image = content.get(3);
        //Adding data to the view
        Glide.with(getApplicationContext()).load(image).into(imageView);
        textViewName.setText(name);
        textViewDescription.setText(description);
        String price2 = "\u20B9" + price;
        textViewPrice.setText(price2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem item = new CartItem(name,quantity.getText().toString(),price);
                String id = myDatabase.push().getKey();
                myDatabase.child(id).setValue(item);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),cart_activity.class);
                startActivity(intent);
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
                Intent intent2 = new Intent(getApplicationContext(),cart_activity.class);
                startActivity(intent2);
            case R.id.nav_exit:
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("Exit me", true);
                startActivity(intent1);
                finish();
                finishAffinity();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
