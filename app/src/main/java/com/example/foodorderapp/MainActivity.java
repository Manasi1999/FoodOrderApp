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


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuAdapter.OnNoteListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "mainActivity";
    //Variables
    RecyclerView recyclerView ;
    final List<MenuContent> list = new ArrayList<>();
    MenuAdapter menuAdapter;
    //Navigation variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //Firebase variables
    private DatabaseReference myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Navigation Drawer
        /* Hooks */
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        /* Toolbar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*Navigation Drawer Menu*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Set first screen as menu
        navigationView.setNavigationItemSelectedListener(this);
        //To exit the application
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
        }
        //Reference to database
        myDatabase= FirebaseDatabase.getInstance().getReference("Menu");

        //Recycler View
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(list,getApplicationContext(),MainActivity.this);
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        myDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    MenuContent content = item.getValue(MenuContent.class);
                    list.add(content);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }


    @Override
    public void OnNoteClick(int position) {
        //TODO - Add intents and send data via intents
        //To get access of data to pass;
        MenuContent menu_content = list.get(position);
        ArrayList<String> curr = new ArrayList<>();
        curr.add(menu_content.getName());
        curr.add(Integer.toString(menu_content.getPrice()));
        curr.add(menu_content.getDescription());
        curr.add(menu_content.getImage());
        Intent intent = new Intent(getApplicationContext(),Second.class);
        intent.putStringArrayListExtra("ItemContent",curr);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_menu:
                break;
            case R.id.nav_cart:
                Intent intent = new Intent(getApplicationContext(),cart_activity.class);
                startActivity(intent);
                break;
            case R.id.nav_exit:
                finish();
                finishAffinity();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
