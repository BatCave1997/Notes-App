package com.example.acer.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView notesList;
    public static ArrayAdapter<String> arrayAdapter ;
    public static ArrayList<String> arrayList;

    boolean isLongPress = false;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = findViewById(R.id.noteslist);

        arrayList = new ArrayList<>();

        sharedPreferences = this.getSharedPreferences("com.example.acer.notepad", Context.MODE_PRIVATE);
        int noteNo = sharedPreferences.getInt("NoteNo", -1) ;
        if(noteNo != -1){
            for(int x = 0; x<=noteNo; x++){
                arrayList.add(x, sharedPreferences.getString(String.valueOf(x), "Press to add new notes"));
            }
        }else{
            arrayList.add("Press to add new notes");
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        notesList.setAdapter(arrayAdapter);

        CheckEmptyNote();

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int p, long id) {
                final int i = p;
                isLongPress = true;
              AlertDialog alertDialog =  new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete it")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();

                                for(int x = i; x<arrayList.size(); x++){
                                    sharedPreferences.edit().putString(String.valueOf(x), arrayList.get(x)).apply();
                                }
                                sharedPreferences.edit().putInt("NoteNo", arrayList.size()-1).apply();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
              alertDialog.show();
              isLongPress = false;
              return true;
            }
        });
        if(!isLongPress) {
            notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    intent.putExtra("index", p);
                    startActivity(intent);

                }
            });
        }
    }

    static void CheckEmptyNote(){

        for (int i = 0; i<arrayList.size(); i++) {
            if(arrayList.get(i).isEmpty()){
                arrayList.set(i, "Press to add new notes");
            }
        }

    }

    public void AddNewNote(View view){
        arrayList.add(arrayList.size(), "Press to add new notes");
        arrayAdapter.notifyDataSetChanged();
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        intent.putExtra("index", arrayList.size()-1);
        startActivity(intent);
    }
}
