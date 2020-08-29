package com.juaracoding.fadh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juaracoding.fadh.adapter.AdapterListBasic;
import com.juaracoding.fadh.model.Biodata;
import com.juaracoding.fadh.R;
import com.juaracoding.fadh.utility.SharedPrefUtil;

import java.lang.reflect.Type;
import java.util.List;

public class ListBiodata extends AppCompatActivity implements AdapterListBasic.OnItemClickListener {

    RecyclerView lstBiodata;
    private AppDatabase mDb;
    private Button btnCari;
    private EditText txtCari;
    String textCari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        txtCari = findViewById(R.id.txtCari);
        btnCari = findViewById(R.id.btnCari);
        lstBiodata = findViewById(R.id.lstBiodata);
        mDb = AppDatabase.getInstance(getApplicationContext());


        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCari = txtCari.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadDatabase(textCari);
                    }
                }).start();
            }
        });

       new Thread(new Runnable() {
           @Override
           public void run() {
               loadDatabase();
           }
       }).start();



    }

    public List<Biodata> loadData(){
        List<Biodata> biodataList =null;
        if(!SharedPrefUtil.getInstance(ListBiodata.this).getString("data_input").isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Biodata>>(){}.getType();
            biodataList = gson.fromJson(SharedPrefUtil.getInstance(ListBiodata.this).getString("data_input"), type);
            for (Biodata data : biodataList){
                Log.i("Contact Details", data.getNama() + "-" + data.getAlamat() + "-" + data.getEmail());
            }

        }

        return biodataList;
    }

    public void loadDatabase(){
        List<Biodata> biodataList =null;
        biodataList =  mDb.biodataDao().getAll();
        adapter = new AdapterListBasic(ListBiodata.this,biodataList);
        adapter.setOnItemClickListener(ListBiodata.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }});
    }
    AdapterListBasic adapter;
    public void loadDatabase(String cari){
        List<Biodata> biodataList =null;
        biodataList =  mDb.biodataDao().findByNama(cari);
        adapter = new AdapterListBasic(ListBiodata.this,biodataList);
        adapter.setOnItemClickListener(ListBiodata.this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }
        });

    }




    @Override
    public void onItemClick(View view, Biodata obj, int position) {
        ImageView v = view.findViewById(R.id.imgBiodata);
        v.setImageResource(R.drawable.ic_close);
        lstBiodata.invalidate();
    }
}