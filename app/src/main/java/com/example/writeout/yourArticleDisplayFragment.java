package com.example.writeout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class yourArticleDisplayFragment extends Fragment {
    String Title,Category;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public yourArticleDisplayFragment() {
        // Required empty public constructor
    }
    public yourArticleDisplayFragment(String title,String category){
        this.Title=title;
        this.Category=category;
    }

    public static yourArticleDisplayFragment newInstance(String param1, String param2) {
        yourArticleDisplayFragment fragment = new yourArticleDisplayFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_your_article_display, container, false);
        TextView title=view.findViewById(R.id.tv_title);
        TextView category=view.findViewById(R.id.tv_category);
        EditText article=view.findViewById(R.id.etarticle);
        title.setText(Title);
        category.setText(Category);


        return view;

    }
    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.ViewPager,new YourArticleFragment()).addToBackStack(null).commit();
    }
}