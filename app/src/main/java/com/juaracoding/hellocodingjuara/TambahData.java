package com.juaracoding.hellocodingjuara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.juaracoding.hellocodingjuara.model.Biodata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class TambahData extends AppCompatActivity {
EditText txtNama;
RadioButton rbPria,rbWanita;
Spinner spnPekerjaan;
CalendarView calendarLahir;
EditText txtAlamat,txtTelepon, txtEmail,txtCatatan;
Button btnSimpan, btnBatal;
    String tanggal="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        txtNama = findViewById(R.id.txtNama);
        rbPria = findViewById(R.id.radioPria);
        rbWanita = findViewById(R.id.radioWanita);
        spnPekerjaan = findViewById(R.id.spnPekerjaan);
        calendarLahir = findViewById(R.id.calendarLahir);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtTelepon = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtCatatan = findViewById(R.id.txtCatatan);

        btnBatal = findViewById(R.id.btnBatal);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        calendarLahir.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Date date = new Date(year-1900,month,dayOfMonth);
                tanggal= sdf.format(date);
                Toast.makeText(TambahData.this,tanggal ,Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean checkMandatory(){

        boolean pass = true;
        if(TextUtils.isEmpty(txtNama.getText().toString())){
            pass = false;
            txtNama.setError("Masukan nama, mandatory");

        }



        if(TextUtils.isEmpty(txtEmail.getText().toString() )|| !Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
            pass = false;
            txtEmail.setError("Masukan email dengan format yang benar");
        }

        return pass;
    }

    public void simpan(View view){
        if(checkMandatory()){
            Biodata biodata = new Biodata();
            biodata.setNama(txtNama.getText().toString());

            if (rbPria.isChecked()){
                biodata.setJenis_kelamin("Pria");

            }else if (rbWanita.isChecked()){
                biodata.setJenis_kelamin("Wanita");
            }else{
                biodata.setJenis_kelamin("Tidak diketahui");
            }

            biodata.setPekerjaan(spnPekerjaan.getSelectedItem().toString());

            biodata.setTanggal_lahir(tanggal);
            biodata.setAlamat(txtAlamat.getText().toString());
            biodata.setEmail(txtEmail.getText().toString());
            biodata.setTelepon(txtTelepon.getText().toString());
            biodata.setCatatan(txtCatatan.getText().toString());

            Gson gson = new Gson();
            String json = gson.toJson(biodata);
            showJsonDialog(json);

        }else{
            showErrorDialog();
        }
    }

    public void showErrorDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahData.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon isi field yang mandatory").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TambahData.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public void showJsonDialog(String json){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahData.this);
        alertDialog.setTitle("Json");
        alertDialog.setMessage("Jsonnya adalah : " +json).setIcon(R.drawable.ic_about).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}