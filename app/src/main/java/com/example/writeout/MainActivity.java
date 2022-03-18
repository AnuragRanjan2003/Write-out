package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseUser firebaseUser;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ExtendedFloatingActionButton fab;
    private String[] titles={"Your Articles","Other Articles","Favourites"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbarcolor)));
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
       // toolbar=findViewById(R.id.toolbar);
        firebaseUser=mAuth.getCurrentUser();
        fab=findViewById(R.id.floatingActionButton);
        tabLayout=findViewById(R.id.TabLayout);
        viewPager=findViewById(R.id.ViewPager);
        viewPagerAdapter=new ViewPagerAdapter(MainActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager,((tab, position) -> tab.setText(titles[position]))).attach();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WritingActivity.class));

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLogOut: startActivity(new Intent(MainActivity.this,SignInActivity.class));

            case R.id.itemProfile: return true;
            case R.id.ShareIcon:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra("share message","Try out this app");
                startActivity(intent);

        }
        return  super.onContextItemSelected(item);

    }

}