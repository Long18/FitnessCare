package com.william.Fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class updateImageExcercise extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageReference = storage.getReferenceFromUrl("gs://fitness-ver-1.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_excercise);


    }


    private void uploadImageToFirebase(Uri image) {

        final StorageReference fileRef = mStorageReference.child("Exercise/" );
        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Toast.makeText(updateImageExcercise.this, "Upload Image Success!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(updateImageExcercise.this, "Upload Image Failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void backMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}