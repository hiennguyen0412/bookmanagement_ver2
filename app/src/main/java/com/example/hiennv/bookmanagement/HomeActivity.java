package com.example.hiennv.bookmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hiennv.bookmanagement.model.Book;
import com.example.hiennv.bookmanagement.utils.DBUtils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    final String DATABASE_NAME = "BookManagement.sqlite";
    SQLiteDatabase database;

    ListView listView;
    ArrayList<Book> list;
    AdapterBook adapter;
    Button btnAdd, btnShowAllType, btnSearch;
    RadioGroup radioCondition;
    RadioButton radioSelected;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        loadAllBooks();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        btnShowAllType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ShowAllTypeActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBook();
            }
        });
    }

    private void init() {
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterBook(this, list);
        listView.setAdapter(adapter);

        //Mapping layout with Activity
        btnAdd = findViewById(R.id.btnAdd);
        btnShowAllType = findViewById(R.id.btnShowType);
        btnSearch = findViewById(R.id.btnSearch);

        radioCondition = findViewById(R.id.radioCondition);

        edtSearch = findViewById(R.id.edtSearch);



    }

    private void loadAllBooks(){
        database = DBUtils.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Book", null);
        list.clear();
        for(int i = 0; i <cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String type = cursor.getString(3);
            Double price = cursor.getDouble(4);
            byte[] image = cursor.getBlob(5);
            list.add(new Book(id, name, author,type,price,image));

        }
        adapter.notifyDataSetChanged();
    }

    private void searchBook(){
        int selectedID = radioCondition.getCheckedRadioButtonId();
        Cursor cursor = null;
        radioSelected = findViewById(selectedID);

        String conditionName = radioSelected.getText().toString();
        String condition = edtSearch.getText().toString();
        String sqlCommand = "";
        database = DBUtils.initDatabase(this, DATABASE_NAME);
        if(conditionName.equals("Name")){
            sqlCommand = "SELECT * FROM Book WHERE Name = ?";
            cursor = database.rawQuery(sqlCommand, new String[]{condition});
        }
        else if(conditionName.equals("Type")){
            sqlCommand = "SELECT * FROM Book WHERE Type = ?";
            cursor = database.rawQuery(sqlCommand, new String[]{condition});
        }

        list.clear();
        for(int i = 0; i <cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String type = cursor.getString(3);
            Double price = cursor.getDouble(4);
            byte[] image = cursor.getBlob(5);
            list.add(new Book(id, name, author,type,price,image));

        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

}