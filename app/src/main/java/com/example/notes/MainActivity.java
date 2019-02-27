package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private FloatingActionButton fab;
    EditText note, description;
    DbHelper dbhelper;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper  = new DbHelper(MainActivity.this);
        listview = findViewById(R.id.listview);

        // Display data when activity starts
        ArrayList<Notes> notes = dbhelper.getNotes();
        CustomAdapter adapter = new CustomAdapter(MainActivity.this,notes);
        listview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.inflate_activity, null);
                final EditText Inflate_Title = alertLayout.findViewById(R.id.Inflate_Title);
                final EditText Inflate_Description = alertLayout.findViewById(R.id.Inflate_Description);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(alertLayout);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("New Note");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Notes notes = new Notes();
                        notes.setNote(Inflate_Title.getText().toString());
                        notes.setDescription(Inflate_Description.getText().toString());
                        dbhelper.insertNote(notes);
                        Toast.makeText(context, "Note Save", Toast.LENGTH_SHORT).show();


                        ArrayList<Notes> note2 = dbhelper.getNotes();
                        CustomAdapter adapter = new CustomAdapter(MainActivity.this,note2);
                        listview.setAdapter(adapter);
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                if (position == 0) {
//                    Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
//                    startActivityForResult(myIntent, 0);
//                }
//
//                if (position == 1) {
//                    Intent myIntent = new Intent(view.getContext(), ListItemActivity2.class);
//                    startActivityForResult(myIntent, 0);
//                }
//
//                if (position == 2) {
//                    Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
//                    startActivityForResult(myIntent, 0);
//                }
//
//                if (position == 3) {
//                    Intent myIntent = new Intent(view.getContext(), ListItemActivity2.class);
//                    startActivityForResult(myIntent, 0);
//                }
//
//                if (position == 4) {
//                    Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
//                    startActivityForResult(myIntent, 0);
//                }
//            }
//        });
      }
}
