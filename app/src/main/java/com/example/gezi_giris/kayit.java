package com.example.gezi_giris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class kayit extends AppCompatActivity {

    private Toolbar actionbarRegister;
    private EditText txtUsername,txtEmail,txtPassword;
    private Button btnRegister;
    private FirebaseAuth auth;

    public void init()
    {
        actionbarRegister=(Toolbar) findViewById(R.id.actionbarRegister);
        setSupportActionBar(actionbarRegister);
        getSupportActionBar().setTitle("Kayıt Ol");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth=FirebaseAuth.getInstance();
        txtUsername=(EditText) findViewById(R.id.txtUsernameRegister);
        txtEmail=(EditText) findViewById(R.id.txtEmailRegister);
        txtPassword=(EditText) findViewById(R.id.txtPasswordRegister);
        btnRegister=(Button) findViewById(R.id.btnRegister);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        init();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }
    private void createNewAccount() {
        String username=txtUsername.getText().toString();
        String email=txtEmail.getText().toString();
        String password=txtPassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email Alanı Boş Bırakılamaz !",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Şifre Alanı Boş Bırakılamaz !",Toast.LENGTH_LONG).show();
        }

        else  {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(kayit.this,"Hesabınız Başarılı Bir Şekilde Oluşturuldu",Toast.LENGTH_LONG).show();
                        Intent  loginIntent = new Intent(kayit.this,giris.class);
                        startActivity(loginIntent);
                        finish();

                    }
                    else{
                        Toast.makeText(kayit.this,"Bir Hata Oluştu !",Toast.LENGTH_LONG).show();

                    }

                }
            });

        }
    }
}
