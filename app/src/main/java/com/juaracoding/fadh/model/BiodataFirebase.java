package com.juaracoding.fadh.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class BiodataFirebase {
    private String nama;
    private String alamat;
    private String telepon;
    private String catatan;
    private String jenis_kelamin;
    private String email;
    private String tanggal_lahir;
    private String pekerjaan;

    public BiodataFirebase(String nama, String alamat, String telepon, String catatan, String jenis_kelamin, String email,String tanggal_lahir, String pekerjaan){
        this.nama = nama;
        this.alamat =alamat;
        this.email = email;
        this.telepon = telepon;
        this.jenis_kelamin = jenis_kelamin;
        this.catatan = catatan;
        this.tanggal_lahir = tanggal_lahir;
        this.pekerjaan = pekerjaan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

//    @Exclude
//    public Map<String, Object> toMap(){
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("nama" , nama);
//        result.put("alamat", alamat);
//        result.put("email", email);
//        result.put("telepon", telepon);
//        result.put("jenis_kelamin", jenis_kelamin);
//        result.put("pekerjaan" , pekerjaan);
//        result.put("catatan", catatan);
//        result.put("tanggal_lahir", tanggal_lahir);
//
//        return result;
//    }
}
