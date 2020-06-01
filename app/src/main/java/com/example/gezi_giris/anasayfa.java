package com.example.gezi_giris;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class anasayfa extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Toolbar actionbar;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference referans=storage.getReference();
    private Button btn,btn2;
    private ImageView resim,resim2;
    static final int SELECT_IMAGE = 12;
    Uri uri2;


public void init()
{
    actionbar=(Toolbar) findViewById(R.id.actionBar);
    setSupportActionBar(actionbar);
    getSupportActionBar().setTitle("Gezidiğim Yerler");

    auth=FirebaseAuth.getInstance();
    currentUser=auth.getCurrentUser();
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        init();
        resim=(ImageView) findViewById(R.id.resim) ;
        resim2=(ImageView) findViewById(R.id.resim2) ;
        btn=(Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,1);

            }
        });
        btn2=(Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Intent.ACTION_PICK);
                i2.setType("image/*");
                startActivityForResult(i2,2);


            }
        });

    }



    @Override
    protected void onStart() {
        if (currentUser==null)
        {
            Intent  welcomeIntent=new Intent(anasayfa.this,MainActivity.class);
            startActivity(welcomeIntent);
            finish();
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         getMenuInflater().inflate(R.menu.menu_main,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

             auth.signOut();
             Intent loginIntent=new Intent(anasayfa.this,MainActivity.class);
             startActivity(loginIntent);
             finish();

         return  true  ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==2 && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            resim2.setImageURI(uri);
            StorageReference ref =referans.child(uri.getLastPathSegment());
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(anasayfa.this, "BAŞARILI", Toast.LENGTH_LONG).show();


                }
            });
        }
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            resim.setImageURI(uri);
            StorageReference ref =referans.child(uri.getLastPathSegment());
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(anasayfa.this, "BAŞARILI", Toast.LENGTH_LONG).show();


                }
            });
        }
    }


}
