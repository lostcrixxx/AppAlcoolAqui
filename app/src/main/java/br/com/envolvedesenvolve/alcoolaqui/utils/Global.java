package br.com.envolvedesenvolve.alcoolaqui.utils;

import android.content.Context;

public class Global {

    static Global mGlobal;

    public static Global getInstance() {
        if (mGlobal == null) {
            mGlobal = new Global();
        }
        return mGlobal;
    }

    public int idTest;

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

}
