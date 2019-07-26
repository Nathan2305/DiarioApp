package com.example.diarioapp.utils;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
