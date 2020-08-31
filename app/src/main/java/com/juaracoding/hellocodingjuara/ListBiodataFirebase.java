package com.juaracoding.hellocodingjuara;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juaracoding.hellocodingjuara.adapter.AdapterListBasic;
import com.juaracoding.hellocodingjuara.model.Biodata;
import com.juaracoding.hellocodingjuara.utility.SharedPrefUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListBiodataFirebase extends AppCompatActivity implements AdapterListBasic.OnItemClickListener{

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
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      //  loadDatabase(textCari);

                    }
                }).start();
                */
                loadDatabaseFirebase(textCari);
            }
        });

     /*  new Thread(new Runnable() {
           @Override
           public void run() {
               loadDatabase();
           }
       }).start();

      */

     loadDatabaseFirebase("");


    }

    public List<Biodata> loadData(){
        List<Biodata> biodataList =null;
        if(!SharedPrefUtil.getInstance(ListBiodataFirebase.this).getString("data_input").isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Biodata>>(){}.getType();
            biodataList = gson.fromJson(SharedPrefUtil.getInstance(ListBiodataFirebase.this).getString("data_input"), type);
            for (Biodata data : biodataList){
                Log.i("Contact Details", data.getNama() + "-" + data.getAlamat() + "-" + data.getEmail());
            }

        }

        return biodataList;
    }

    public void loadDatabase(){
        List<Biodata> biodataList =null;
        biodataList =  mDb.biodataDao().getAll();
        adapter = new AdapterListBasic(ListBiodataFirebase.this,biodataList);
        adapter.setOnItemClickListener(ListBiodataFirebase.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodataFirebase.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }});
    }
    AdapterListBasic adapter;
    public void loadDatabase(String cari){
        List<Biodata> biodataList =null;
        biodataList =  mDb.biodataDao().findByNama(cari);
        adapter = new AdapterListBasic(ListBiodataFirebase.this,biodataList);
        adapter.setOnItemClickListener(ListBiodataFirebase.this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodataFirebase.this));
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

    private DatabaseReference mDatabase;
    public void loadDatabaseFirebase(String nomer){


        final List<Biodata> biodataList =new ArrayList<Biodata>();


        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(nomer.equalsIgnoreCase("")){
            mDatabase.child("biodata").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    biodataList.clear();

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {




                            Biodata user = postSnapshot.getValue(Biodata.class);
                            biodataList.add(user);





                        // here you can access to name property like university.name

                    }

                    //        GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    //         Map<String, Object> objectHashMap = dataSnapshot.getValue(objectsGTypeInd);
                    //         ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());

                    adapter = new AdapterListBasic(ListBiodataFirebase.this,biodataList);
                    adapter.setOnItemClickListener(ListBiodataFirebase.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodataFirebase.this));
                            lstBiodata.setItemAnimator(new DefaultItemAnimator());
                            lstBiodata.setAdapter(adapter);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            mDatabase.child("biodata").orderByChild("nama").equalTo(nomer).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    biodataList.clear();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {




                            Biodata user = postSnapshot.getValue(Biodata.class);
                            biodataList.add(user);





                        // here you can access to name property like university.name

                    }

                    //        GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    //         Map<String, Object> objectHashMap = dataSnapshot.getValue(objectsGTypeInd);
                    //         ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());

                    adapter = new AdapterListBasic(ListBiodataFirebase.this,biodataList);
                    adapter.setOnItemClickListener(ListBiodataFirebase.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodataFirebase.this));
                            lstBiodata.setItemAnimator(new DefaultItemAnimator());
                            lstBiodata.setAdapter(adapter);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }









    }
}