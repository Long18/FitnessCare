package com.william.Fitness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.william.Fitness.Database.SessionManager;
import com.william.Fitness.Login.Login;
import com.william.Fitness.Model.User;

import java.util.HashMap;

public class ProfileActivity extends Fragment {

    TextInputLayout getEmail, getPassword;
    TextInputEditText fullname, username, phone, email, gender, date, password;
    TextView mFullName, mUserName;
    Button btnUpdate, btnBackLogin;
    ImageView mProfileImage;
    RelativeLayout turnLogin;
    LinearLayout turnLogin2;

    String _mFullName, _mUserName, _mPhone, _mEmail, _mGender, _mDate, _mPassword;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageReference = storage.getReferenceFromUrl("gs://fitness-ver-1.appspot.com");

    final String ONE_DIGIT = "^(?=.*[0-9]).{6,}$";
    final String ONE_LOWER_CASE = "^(?=.*[a-z]).{6,}$";
    final String ONE_UPPER_CASE = "^(?=.*[A-Z]).{6,}$";
    final String ONE_SPECIAL_CHAR = "^(?=.*[@#$%^&+=]).{6,}$";
    final String NO_SPACE = "^(?=\\S+$).{6,}$";
    final String MIN_CHAR = "^[a-zA-Z0-9._-].{5,}$";
    final String EMAIL_VALIDATE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");


        //Hooks
        fullname = view.findViewById(R.id.txt_fullname);
        username = view.findViewById(R.id.txt_username);
        phone = view.findViewById(R.id.txt_phone);
        email = view.findViewById(R.id.txt_email);
        gender = view.findViewById(R.id.txt_gender);
        date = view.findViewById(R.id.txt_date);
        password = view.findViewById(R.id.txt_password);

        mFullName = view.findViewById(R.id.full_name);
        mUserName = view.findViewById(R.id.user_name);
        getEmail = view.findViewById(R.id.email_TextInputLayout);
        getPassword = view.findViewById(R.id.password_TextInputLayout);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnBackLogin = view.findViewById(R.id.btnBackLogin);
        mProfileImage = view.findViewById(R.id.profile_image);
        turnLogin = view.findViewById(R.id.rll_login);
        turnLogin2 = view.findViewById(R.id.lnl_login);


        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext(), SessionManager.SESSION_USER);
        HashMap<String, String> userInformation = sessionManager.getInfomationUser();
        if (sessionManager.checkUserLogin()) {
            fullname.setText(userInformation.get(SessionManager.KEY_FULLNAME));
            mFullName.setText(userInformation.get(SessionManager.KEY_FULLNAME));
            username.setText(userInformation.get(SessionManager.KEY_USERNAME));
            mUserName.setText(userInformation.get(SessionManager.KEY_USERNAME));
            phone.setText(userInformation.get(SessionManager.KEY_PHONENUMBER));
            email.setText(userInformation.get(SessionManager.KEY_EMAIL));
            gender.setText(userInformation.get(SessionManager.KEY_GENDER));
            date.setText(userInformation.get(SessionManager.KEY_DATE));
            password.setText(userInformation.get(SessionManager.KEY_PASSWORD));
        }

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
        if (signInAccount != null) {

            fullname.setText(signInAccount.getGivenName());
            mFullName.setText(signInAccount.getGivenName());
            username.setText(signInAccount.getDisplayName());
            mUserName.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());

            phone.setText("");
            gender.setText("");
            date.setText("");
            password.setText("");

        }


        getUserInformation();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushUser();
            }
        });


        //User's must loggin to see this page
        if (sessionManager.checkUserLogin()) {
            turnLogin.setVisibility(View.GONE);


        } else {
            turnLogin2.setVisibility(View.GONE);
        }

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallary = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallary, 1000);
            }
        });


        StorageReference mProfileRef = mStorageReference.child("Users/" + _mPhone + "/avata.jpg");
        mProfileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfileImage);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri image = data.getData();
                //mProfileImage.setImageURI(image);

                uploadImageToFirebase(image);
            }
        }
    }

    private void uploadImageToFirebase(Uri image) {

        final StorageReference fileRef = mStorageReference.child("Users/" + _mPhone + "/avata.jpg");
        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(mProfileImage);
                    }
                });
                Toast.makeText(getActivity().getApplicationContext(), "Upload Image Success!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Upload Image Failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void getUserInformation() {
        Intent intent = getActivity().getIntent();

        _mFullName = intent.getStringExtra("fullName");
        _mUserName = intent.getStringExtra("userName");
        _mPhone = intent.getStringExtra("phoneNo");
        _mEmail = intent.getStringExtra("email");
        _mGender = intent.getStringExtra("gender");
        _mDate = intent.getStringExtra("date");
        _mPassword = intent.getStringExtra("password");

        _mFullName = fullname.getText().toString();
        _mUserName = username.getText().toString();
        _mPhone = phone.getText().toString();
        _mEmail = email.getText().toString();
        _mGender = gender.getText().toString();
        _mDate = date.getText().toString();
        _mPassword = password.getText().toString();

    }

    public void pushUser() {

        if (!validateEmail() | !validatePassword()) {
            return;
        }
        isNameChanged();
        isUserNameChanged();
        isPassWordChanged();
        //inputUser();
        Toast.makeText(getActivity().getApplicationContext(), "Data Updated!", Toast.LENGTH_SHORT).show();


    }

    private boolean isPassWordChanged() {
        if (!_mPassword.equals(password.getText().toString())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(_mPhone).child("password").setValue(password.getText().toString());
            return true;

        } else {
            return false;
        }
    }

    private boolean isUserNameChanged() {
        if (!_mUserName.equals(username.getText().toString())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(_mPhone).child("userName").setValue(username.getText().toString());

            return true;

        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!_mFullName.equals(fullname.getText().toString())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(_mPhone).child("fullName").setValue(fullname.getText().toString());

            return true;

        } else {
            return false;
        }
    }

    public void inputUser() {


        User addNewUser = new User(_mFullName, _mUserName, _mEmail, _mPhone, _mPassword, _mDate, _mGender);

        reference.child(_mPhone).setValue(addNewUser);
    }

    private boolean validateEmail() {
        String val = getEmail.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            getEmail.setError("Email không được để trống!");
            return false;
        } else if (!val.matches(EMAIL_VALIDATE)) {
            getEmail.setError("Hãy viết email đúng định dạng!");
            return false;
        } else {
            getEmail.setError(null);
            getEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = getPassword.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            getPassword.setError("Mật khẩu không được để trống!");
            return false;
        } else if (!val.matches(MIN_CHAR)) {
            getPassword.setError("Mật khẩu phải có ít nhất 6 kí tự!");
            return false;
        } else if (!val.matches(ONE_DIGIT)) {
            getPassword.setError("Mật khẩu phải có ít nhất 1 chữ số!");
            return false;
        } else if (!val.matches(ONE_LOWER_CASE)) {
            getPassword.setError("Mật khẩu phải có ít nhất 1 chữ thường!");
            return false;
        } else if (!val.matches(ONE_UPPER_CASE)) {
            getPassword.setError("Mật khẩu phải có ít nhất 1 chữ viết hoa!");
            return false;
        } else if (!val.matches(ONE_SPECIAL_CHAR)) {
            getPassword.setError("Mật khẩu phải có ít nhất 1 kí tự đặc biệt!");
            return false;
        } else if (!val.matches(NO_SPACE)) {
            getPassword.setError("Mật khẩu không được để khoảng cách!");
            return false;
        } else {
            getPassword.setError(null);
            getPassword.setErrorEnabled(false);
            return true;
        }


    }

}