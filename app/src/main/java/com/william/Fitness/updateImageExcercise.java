package com.william.Fitness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class updateImageExcercise extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageReference = storage.getReferenceFromUrl("gs://fitness-ver-1.appspot.com");

    Spinner cbbType;
    ImageView imageView;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_excercise);

        //Hooks
        cbbType = findViewById(R.id.cbb_Type);
        ArrayAdapter<String> cbbTypeAdapter = new ArrayAdapter<String>(updateImageExcercise.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.type_muscle_array));
        cbbTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbType.setAdapter(cbbTypeAdapter);

        imageView = findViewById(R.id.btnAddImage);
        timePicker = findViewById(R.id.clock);


        timePicker.setIs24HourView(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallary = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallary, 1000);
            }
        });
    }


    private void uploadImageToFirebase(Uri image) {

        final StorageReference fileRef = mStorageReference.child("Exercise/" );
        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(imageView);
                    }
                });
                Toast.makeText(updateImageExcercise.this, "Upload Image Success!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(updateImageExcercise.this, "Upload Image Failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri image = data.getData();
                imageView.setImageURI(image);

            }
        }
    }

    public void backMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}