package br.com.envolvedesenvolve.alcoolaqui.model;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import br.com.envolvedesenvolve.alcoolaqui.db.UserTable;

public class User {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String imei;
    private String dt_inc;
    private String dt_upd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDt_inc() {
        return dt_inc;
    }

    public void setDt_inc(String dt_inc) {
        this.dt_inc = dt_inc;
    }

    public String getDt_upd() {
        return dt_upd;
    }

    public void setDt_upd(String dt_upd) {
        this.dt_upd = dt_upd;
    }

    public void setInsert(Context context, ArrayList<User> prodList){
        ContentValues cv;
        UserTable table = new UserTable(context);

        for(User userLine : prodList){
            cv = new ContentValues();
            cv.put(UserTable.COLUMN_ID, userLine.getId());
            cv.put(UserTable.COLUMN_NOME, userLine.getNome());
            cv.put(UserTable.COLUMN_EMAIL, userLine.getEmail());
            cv.put(UserTable.COLUMN_SENHA, userLine.getSenha());

            table.setValuesDatabase(context, cv);
        }
    }
}
