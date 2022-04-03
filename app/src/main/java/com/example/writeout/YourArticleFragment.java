package com.example.writeout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class YourArticleFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<model> articleList;


    public YourArticleFragment() {
        // Required empty public constructor
    }

    public static YourArticleFragment newInstance(String param1, String param2) {
        YourArticleFragment fragment = new YourArticleFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        View view = inflater.inflate(R.layout.fragment_your_article, container, false);
        recyclerView = view.findViewById(R.id.rec_your_articles);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));
        articleList = new ArrayList<>();
        database.getReference("users").child(firebaseUser.getUid()).child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    model Model = snapshot1.getValue(model.class);
                    articleList.add(Model);
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter = new MyAdapter(getContext(),articleList);
        recyclerView.setAdapter(myAdapter);
        return view;
    }
}

