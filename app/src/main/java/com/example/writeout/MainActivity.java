package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    FirebaseAuth mAuth;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter PagerAdapter;
    int tapCounter=0;
    ExtendedFloatingActionButton fab;
    private String[] titles={"Your Articles","Other Articles","Favourites"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbarcolor)));
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        firebaseUser=mAuth.getCurrentUser();
        fab=findViewById(R.id.floatingActionButton);
        fab.shrink();
        tabLayout=findViewById(R.id.TabLayout);
        viewPager=findViewById(R.id.ViewPager);
        tabLayout.setupWithViewPager(viewPager);
        PagerAdapter=new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        PagerAdapter.addFragment(new YourArticleFragment(),"Your Articles");
        PagerAdapter.addFragment(new OtherArticlesFragment(),"Other Articles");
        PagerAdapter.addFragment(new FavouritesFragment(),"Favourite");
        viewPager.setAdapter(PagerAdapter);
        if(firebaseUser==null){
            startActivity(new Intent(MainActivity.this,SignInActivity.class));
            finish();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tapCounter+=1;
                if(!(tapCounter%2==0))
                    fab.extend();
                else{
                startActivity(new Intent(MainActivity.this,WritingActivity.class));
                fab.shrink();}

            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLogOut:
                                mAuth.signOut();
                                Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        Toast.makeText(this, "You Logged out", Toast.LENGTH_LONG).show();
                                        finish();
                                        return true;

            case R.id.itemProfile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                return true;
            case R.id.itemShare:
                Intent intent2=new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT,"Try out Write Out app");
                startActivity(intent2);
                return true;
                }
                return  super.onContextItemSelected(item);
            }





}