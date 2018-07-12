package com.example.acer.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    EditText para;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferences = this.getSharedPreferences("com.example.acer.notepad", Context.MODE_PRIVATE);
        para = findViewById(R.id.editText3);

        Intent intent = getIntent();
        final int index = intent.getIntExtra("index", -1);

        if(index!=-1){
            para.setText(MainActivity.arrayList.get(index));
           // para.setText(sharedPreferences.getString(String.valueOf(index), "Press to add new notes"));
            para.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainActivity.arrayList.set(index , String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    sharedPreferences.edit().putString(String.valueOf(index), MainActivity.arrayList.get(index)).apply();
                    sharedPreferences.edit().putInt("NoteNo",index).apply();
                    MainActivity.CheckEmptyNote();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
