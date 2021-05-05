package com.william.Fitness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.william.Fitness.Database.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends Fragment {

    TextInputEditText fullname, username, phone, email,gender,date, password;
    TextView mFullName, mUserName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        fullname = view.findViewById(R.id.txt_fullname);
        username = view.findViewById(R.id.txt_username);
        phone = view.findViewById(R.id.txt_phone);
        email = view.findViewById(R.id.txt_email);
        gender = view.findViewById(R.id.txt_gender);
        date = view.findViewById(R.id.txt_date);
        password = view.findViewById(R.id.txt_password);
        mFullName = view.findViewById(R.id.full_name);
        mUserName = view.findViewById(R.id.user_name);

        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext(),SessionManager.SESSION_USER);
        HashMap<String,String> userInfomation =  sessionManager.getInfomationUser();
        if (sessionManager.checkUserLogin()) {
            fullname.setText(userInfomation.get(SessionManager.KEY_FULLNAME));
            mFullName.setText(userInfomation.get(SessionManager.KEY_FULLNAME));
            username.setText(userInfomation.get(SessionManager.KEY_USERNAME));
            mUserName.setText(userInfomation.get(SessionManager.KEY_USERNAME));
            phone.setText(userInfomation.get(SessionManager.KEY_PHONENUMBER));
            email.setText(userInfomation.get(SessionManager.KEY_EMAIL));
            gender.setText(userInfomation.get(SessionManager.KEY_GENDER));
            date.setText(userInfomation.get(SessionManager.KEY_DATE));
            password.setText(userInfomation.get(SessionManager.KEY_PASSWORD));
        }


        return view;
    }
}