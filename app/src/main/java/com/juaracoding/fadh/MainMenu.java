package com.juaracoding.hellocodingjuara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String username, password;
    TextView txtUsername;
    ImageView btnTambahData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        txtUsername = findViewById(R.id.txtUsername);
        btnTambahData = findViewById(R.id.btnTambahData);
//        username = getIntent().getStringExtra("username");
//        password = getIntent().getStringExtra("password");
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        txtUsername.setText(firebaseUser.getEmail());


    }

    public void screenTambahData(View view){
        Intent intent = new Intent(MainMenu.this,TambahDataSqlite.class);
        startActivity(intent);

    }

    public void screenListData(View view){
        Intent intent = new Intent(MainMenu.this,ListBiodataFirebase.class);
        startActivity(intent);

    }
    public void screenLogout(View view){
    FirebaseAuth.getInstance().signOut();
    Intent intent = new Intent(MainMenu.this,MainActivity.class);
    startActivity(intent);
    }

}