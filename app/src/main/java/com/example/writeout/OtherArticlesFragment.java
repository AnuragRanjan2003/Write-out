package com.example.writeout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OtherArticlesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String userName;
    RecyclerView recyclerView;
    MyAdapter2 myAdapter2;
    ArrayList<model> arrayList;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    SearchView searchView;


    FirebaseUser firebaseUser;
    private String mParam1;
    private String mParam2;

    public OtherArticlesFragment() {

    }

    public static OtherArticlesFragment newInstance(String param1, String param2) {
        OtherArticlesFragment fragment = new OtherArticlesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    public void LoadArticles() {
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        database.getReference("users").child(firebaseUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                userName = String.valueOf(task.getResult().getValue());
                database.getReference("post").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            model Model = snapshot1.getValue(model.class);
                            if (!(Model.getAuthorName().equals(userName))) {
                                arrayList.add(Model);
                                myAdapter2.completeList.add(Model);
                            }

                        }
                        myAdapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        myAdapter2 = new MyAdapter2(getContext(), arrayList);
        recyclerView.setAdapter(myAdapter2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_articles, container, false);
        recyclerView = view.findViewById(R.id.rec_other_articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LoadArticles();
        searchView = view.findViewById(R.id.Search_Icon2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty())
                    myAdapter2.getFilter().filter(s);
                else {
                    //reload all articles
                    LoadArticles();
                }
                return true;
            }

        });

        return view;

    }


}