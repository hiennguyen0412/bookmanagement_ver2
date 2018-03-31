package com.example.hiennv.bookmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hiennv.bookmanagement.utils.DBUtils;

public class LoginActivity extends AppCompatActivity {
    final String DATABASE_NAME = "BookManagement.sqlite";
    SQLiteDatabase database;
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtUsername = this.findViewById(R.id.edtUsername);
        edtPassword = this.findViewById(R.id.edtPassword);
        btnLogin = this.findViewById(R.id.btnLogin);
        btnRegister = this.findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLogin()) {
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);

            }
        });
    }

    private boolean checkLogin() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        database = DBUtils.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Account WHERE Username = ? " +
                "AND Password = ?", new String[]{username, password});
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            return true;
        }


        return false;
    }


}
