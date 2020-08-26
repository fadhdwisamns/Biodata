package com.juaracoding.hellocodingjuara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juaracoding.hellocodingjuara.model.Biodata;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    EditText txtUsername,txtPassword;
    Button btnLogin;
    Button btnDaftar;
    private FirebaseAuth mAuth;
    private AppDatabase mDb;
    Biodata data1;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnDaftar = findViewById(R.id.btnDaftar);
        mAuth = FirebaseAuth.getInstance();

         currentUser = mAuth.getCurrentUser();


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DaftarUser.class));
            }
        });






        txtUsername.setText("dewabrata");
        txtPassword.setText("12345");





    }

    public void login(View view){




            mAuth.signInWithEmailAndPassword(txtUsername.getText().toString(), txtPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                String pesan = "Isinya adalah " + txtUsername.getText().toString() + "  passwordnya adalah " + txtPassword.getText().toString();
                                Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_LONG).show();

                                Intent nextScreen = new Intent(MainActivity.this, MainMenu.class);
                                nextScreen.putExtra("username", txtUsername.getText().toString());
                                nextScreen.putExtra("password", txtPassword.getText().toString());

                                startActivity(nextScreen);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

        }









}