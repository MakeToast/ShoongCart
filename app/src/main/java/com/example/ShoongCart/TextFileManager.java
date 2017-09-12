package com.example.ShoongCart;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by choeyujin on 2017. 9. 12..
 */

public class TextFileManager {

    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseDatabase;
    private static final String FILE_NAME = "Memo.txt";
    Context mContext = null;

    public TextFileManager(Context context)
    {
        mContext = context;
    }
    public void save(String strData)
    {
        if(strData == null || strData.equals(""))
            return;
        FileOutputStream fosMemo = null;
        try{
            fosMemo = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fosMemo.write(strData.getBytes());
            fosMemo.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String load()
    {
        try{
            FileInputStream fisMemo = mContext.openFileInput(FILE_NAME);
            byte[] memoData = new byte[fisMemo.available()];
            while(fisMemo.read(memoData) != -1){}

            return new String(memoData);
        }catch(IOException e)
        {}
        return "";
    }

    public void delete()
    {
        mContext.deleteFile(FILE_NAME);
    }
}
