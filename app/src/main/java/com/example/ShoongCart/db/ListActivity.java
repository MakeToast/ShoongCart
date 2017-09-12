package com.example.ShoongCart.db;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ShoongCart.MemoActivity;
import com.example.ShoongCart.R;
import com.example.ShoongCart.TextFileManager;

/**
 * Created by choeyujin on 2017. 9. 13..
 */

public class ListActivity extends Activity {
    EditText mMemoEdit = null;
    TextFileManager mTextFileManager = new TextFileManager(ListActivity.this);

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mMemoEdit = (EditText)findViewById(R.id.memo_edit);
        mMemoEdit.setText("칙촉 2500\n테니스 스커트 20000\n카레 3500\n니트 30000\n청바지 29900\n라면 1000\n총 가격 : 86900");
    }

}
