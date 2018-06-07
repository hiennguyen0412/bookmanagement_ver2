package com.example.hiennv.bookmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hiennv.bookmanagement.utils.DBUtils;

public class RegisterActivity extends AppCompatActivity {
    private final String DATABASE_NAME = "BookManagement.sqlite";
    private SQLiteDatabase database;
    private EditText edtUsername, edtPassword;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addAccount()){
                    Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void init(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

    }
    private boolean addAccount(){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        database = DBUtils.initDatabase(this, DATABASE_NAME);

        ContentValues values = new ContentValues();
        values.put("Account", username);
        values.put("Account", password);

        // Insert to database
        database.insert("Account", null, values);


        return true;
    }
}