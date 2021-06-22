package com.example.minhacidade.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhacidade.R;
import com.example.minhacidade.activity.CadastraDenunciaActivity;
import com.example.minhacidade.activity.CadastrarReclamacaoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReclamacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReclamacaoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FloatingActionButton fab;

    public ReclamacaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReclamacaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReclamacaoFragment newInstance(String param1, String param2) {
        ReclamacaoFragment fragment = new ReclamacaoFragment();
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
        //return inflater.inflate(R.layout.fragment_reclamacao, container, false);
        View view = inflater.inflate(R.layout.fragment_reclamacao, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fabButtonReclamacao);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CadastrarReclamacaoActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}