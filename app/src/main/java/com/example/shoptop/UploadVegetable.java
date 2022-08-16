package com.example.shoptop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class UploadVegetable extends AppCompatActivity {
    Spinner spinner;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Vegetables");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ImageView imageView;
    private FirebaseUser user;
    private String UserId;
    EditText Name, Price, Quantity;
    String UploadName, UploadQuatity, UploadAddress;
    int UploadPrice;
    Button UploadVeg;
    private Uri imageuri;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vegetable);

        spinner = findViewById(R.id.vegmarkets);
        imageView = findViewById(R.id.vegimage);
        Name = findViewById(R.id.uploadvegname);
        Price = findViewById(R.id.uploadvegprice);
        Quantity = findViewById(R.id.uploadvegquantity);
        UploadVeg = findViewById(R.id.uploadveg);

        fetchdata();

        UploadVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageuri != null){
                    UploadToFirebase(imageuri);
                }
                else {
                    Toast.makeText(UploadVegetable.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,2);
            }
        });

    }
    private void UploadToFirebase(Uri uri) {
        StorageReference fileref = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UploadName = Name.getText().toString();
                        UploadPrice = Integer.parseInt(String.valueOf(Price.getText()));
                        UploadQuatity = Quantity.getText().toString();
                        UploadAddress = spinner.getSelectedItem().toString();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        UserId = user.getUid();
                        VegModel vegModel = new VegModel();
                        vegModel.setVpurl(uri.toString());
                        vegModel.setVname(UploadName);
                        vegModel.setVprice(UploadPrice);
                        vegModel.setVquantity(UploadQuatity+" KG");
                        vegModel.setVaddress(UploadAddress);
                        vegModel.setUserid(UserId);
                        String modelId = databaseReference1.push().getKey();
                        databaseReference1.child(modelId).setValue(vegModel);
                        Toast.makeText(UploadVegetable.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadVegetable.this, "Image Upload Failed !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtention(Uri muri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(muri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null)
        {
            imageuri = data.getData();
            imageView.setImageURI(imageuri);
        }
    }

    private void fetchdata(){

        databaseReference.child("Markets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    String marketname = item.child("name").getValue(String.class);
                    list.add(marketname);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UploadVegetable.this, android.R.layout.simple_spinner_dropdown_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}