package com.example.ShoongCart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by choeyujin on 2017. 9. 12..
 */

public class MemoActivity extends AppCompatActivity {
    EditText mMemoEdit = null;
    TextFileManager mTextFileManager = new TextFileManager(MemoActivity.this);

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        mMemoEdit = (EditText)findViewById(R.id.memo_edit);
     }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.load_btn : {
                String memoData = mTextFileManager.load();
                mMemoEdit.setText(memoData);
                Toast.makeText(MemoActivity.this, "불러오기 완료", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.save_btn : {
                String memoData= mMemoEdit.getText().toString();
                mTextFileManager.save(memoData);
                mMemoEdit.setText("");
                Toast.makeText(MemoActivity.this, "저장 완료", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.delete_btn : {
                mTextFileManager.delete();
                mMemoEdit.setText("");
                Toast.makeText(MemoActivity.this, "뒤로가기", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MemoActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
