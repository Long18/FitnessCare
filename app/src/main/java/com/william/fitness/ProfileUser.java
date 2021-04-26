package com.william.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.william.fitness.Login.Login;

public class ProfileUser extends Fragment {

    TextView btnHome, txtName, txtNumber,txtAddress,txtBirth, txtEmail, txtNameUser,txtEmailUser;
    String name, number,email,address,birth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        btnHome = view.findViewById(R.id.btnHome);
        txtName = view.findViewById(R.id.txtName);
        txtNumber = view.findViewById(R.id.txtPhone);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtBirth = view.findViewById(R.id.txtBirth);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtNameUser = view.findViewById(R.id.txtNameUser);
        txtEmailUser = view.findViewById(R.id.txtEmailUser);

        txtName.setText(MainActivity.name);

        //showAllUserData();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Login.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showAllUserData();
    }

    private void showAllUserData() {
//        Intent intent = getIntent();
//
//        name = intent.getStringExtra("name");
//        number = intent.getStringExtra("phone");
//        email = intent.getStringExtra("email");
//        address = intent.getStringExtra("address");
//        birth = intent.getStringExtra("birthday");

        txtName.setText(MainActivity.name);
        txtNumber.setText(MainActivity.number);
        txtEmail.setText(MainActivity.email);
        txtAddress.setText(MainActivity.address);
        txtBirth.setText(MainActivity.birth);

        txtNameUser.setText(MainActivity.name);
        txtEmailUser.setText(MainActivity.email);
    }

}
