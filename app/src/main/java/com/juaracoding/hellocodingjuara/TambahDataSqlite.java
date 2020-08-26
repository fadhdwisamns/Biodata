package com.juaracoding.hellocodingjuara;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juaracoding.hellocodingjuara.model.Biodata;
import com.juaracoding.hellocodingjuara.utility.SharedPrefUtil;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TambahDataSqlite extends AppCompatActivity {
EditText txtNama;
RadioButton rbPria,rbWanita;
Spinner spnPekerjaan;
CalendarView calendarLahir;
EditText txtAlamat,txtTelepon, txtEmail,txtCatatan;
    private DatabaseReference mDatabase;
Button btnSimpan, btnBatal;
    String tanggal="";
    private AppDatabase mDb;

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

        mDb = AppDatabase.getInstance(getApplicationContext());

        calendarLahir.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Date date = new Date(year-1900,month,dayOfMonth);
                tanggal= sdf.format(date);
                Toast.makeText(TambahDataSqlite.this,tanggal ,Toast.LENGTH_LONG).show();
            }
        });


        String dataJson = SharedPrefUtil.getInstance(TambahDataSqlite.this).getString("data_input");

        if(!TextUtils.isEmpty(dataJson)){

           // mappingData(dataJson);
        }else{

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void mappingData(String json){




        Biodata biodata = new Gson().fromJson(json,Biodata.class);


        txtNama.setText(biodata.getNama());

        if(biodata.getJenis_kelamin().equalsIgnoreCase("Pria")){
            rbPria.setChecked(true);
            rbWanita.setChecked(false);
        }else if (biodata.getJenis_kelamin().equalsIgnoreCase("Wanita")){
            rbWanita.setChecked(true);
            rbPria.setChecked(false);
        }else{
            rbPria.setChecked(false);
            rbWanita.setChecked(false);
        }

        List<String> lstPekerjaan = Arrays.asList(getResources().getStringArray(R.array.pekerjaan));

         for(int x = 0 ;  x < lstPekerjaan.size();x++){

             if(lstPekerjaan.get(x).equalsIgnoreCase(biodata.getPekerjaan())){
                 spnPekerjaan.setSelection(x);
             }

         }

         txtAlamat.setText(biodata.getAlamat());
         txtTelepon.setText(biodata.getTelepon());
         txtEmail.setText(biodata.getEmail());
         txtCatatan.setText(biodata.getCatatan());
         
        Date dateDummy = null;
        try {
            dateDummy =new SimpleDateFormat("dd-MMMM-yyyy").parse(biodata.getTanggal_lahir());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarLahir.setDate(dateDummy.getTime());





    }


    public List<Biodata> getModelArrayString(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Biodata>>(){}.getType();
        List<Biodata> biodataList = gson.fromJson(json, type);
        for (Biodata data : biodataList){
            Log.i("Contact Details", data.getNama() + "-" + data.getAlamat() + "-" + data.getEmail());
        }

        return biodataList;
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



            new Thread(new Runnable() {
                @Override
                public void run() {

                    Biodata biodata=null;

                    biodata =  mDb.biodataDao().findByTelepon(txtTelepon.getText().toString());

                    if(biodata != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                showErrorDialog2();
                            }
                        });
                    }else{



                        mDb.biodataDao().insertAll(generateObjectData());
                       mDatabase.child("biodata").child(generateObjectData().getTelepon()).setValue(generateObjectData());
                    }



                }
            }).start();






        }else{
            showErrorDialog();
        }
    }

    public  List<Biodata> getListBiodata(String json){


        return null;
    }


    public Biodata generateObjectData(){
        Biodata biodata = new Biodata();
        biodata.setNama(txtNama.getText().toString());

        if (rbPria.isChecked()) {
            biodata.setJenis_kelamin("Pria");

        } else if (rbWanita.isChecked()) {
            biodata.setJenis_kelamin("Wanita");
        } else {
            biodata.setJenis_kelamin("Tidak diketahui");
        }

        biodata.setPekerjaan(spnPekerjaan.getSelectedItem().toString());

        biodata.setTanggal_lahir(tanggal);
        biodata.setAlamat(txtAlamat.getText().toString());
        biodata.setEmail(txtEmail.getText().toString());
        biodata.setTelepon(txtTelepon.getText().toString());
        biodata.setCatatan(txtCatatan.getText().toString());

        return biodata;
    }

    public void showErrorDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahDataSqlite.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon isi field yang mandatory").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TambahDataSqlite.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void showErrorDialog2(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahDataSqlite.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon masukan telepon yang berbeda").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TambahDataSqlite.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public void showJsonDialog(String json){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahDataSqlite.this);
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