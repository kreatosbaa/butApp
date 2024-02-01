package com.example.butApp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.butApp.R;
import com.example.butApp.activities.SplashActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(requireContext(), SplashActivity.class));

        if (getActivity() != null) {
            getActivity().finish();
        }

        View root = inflater.inflate(R.layout.fragment_logout,container,false);
        return root;
    }
}