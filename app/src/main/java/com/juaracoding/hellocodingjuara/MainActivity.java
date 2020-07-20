package com.juaracoding.hellocodingjuara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername,txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        txtUsername.setText("dewabrata");
        txtPassword.setText("12345");



    }

    public void login(View view){
        String pesan = "Isinya adalah "+ txtUsername.getText().toString() +"  passwordnya adalah "+txtPassword.getText().toString();
        Toast.makeText(MainActivity.this,pesan,Toast.LENGTH_LONG).show();
    }
}