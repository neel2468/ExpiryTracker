package com.example.expirytracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



import org.json.JSONObject;


public class LoginFragment extends Fragment  implements View.OnClickListener {




    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        Button login_btn = (Button) view.findViewById(R.id.loginBtn);
        Button register_btn = (Button)view.findViewById(R.id.regsiterBtn);

        EditText login_email = (EditText) view.findViewById(R.id.login_email);
        EditText login_password = (EditText) view.findViewById(R.id.login_password);

        Intent myIntent = new Intent(getActivity().getBaseContext(),HomeActivity.class);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent.putExtra("email",String.valueOf(login_email.getText()));
                startActivity(myIntent);
            }
        });
        return  view;
    }

    @Override
    public void onClick(View v) {

    }
}