package com.juaracoding.hellocodingjuara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juaracoding.hellocodingjuara.model.Biodata;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    EditText txtUsername,txtPassword;
    Button btnLogin;
    private AppDatabase mDb;
    Biodata data1;
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

        Intent nextScreen  = new Intent(MainActivity.this, MainMenu.class);
        nextScreen.putExtra("username",txtUsername.getText().toString());
        nextScreen.putExtra("password",txtPassword.getText().toString());

        startActivity(nextScreen);

    }


}