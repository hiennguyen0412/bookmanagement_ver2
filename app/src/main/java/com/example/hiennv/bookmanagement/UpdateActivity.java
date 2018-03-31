package com.example.hiennv.bookmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hiennv.bookmanagement.utils.DBUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {
    final String DATABASE_NAME = "BookManagement.sqlite";
    final int REQUEST_CHOOSE_PHOTO = 321;
    EditText edtID, edtName, edtAuthor, edtType, edtPrice;
    ImageView imgUpdate;
    Button btnSave, btnCancel, btnSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addControls();
        loadToUpdate();

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update()==1){
                    Toast.makeText(UpdateActivity.this, "Update OK", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UpdateActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }

    private void loadToUpdate() {

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", -1);
        SQLiteDatabase database = DBUtils.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Book WHERE Book.ID = ?", new String[]{id+""});
        cursor.moveToFirst();

        String name = cursor.getString(1);
        String author = cursor.getString(2);
        String type = cursor.getString(3);
        double price = cursor.getDouble(4);
        byte[] img = cursor.getBlob(5);

        Bitmap bm = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgUpdate.setImageBitmap(bm);

        edtID.setText(id+"");
        edtName.setText(name);
        edtAuthor.setText(author);
        edtType.setText(type);
        edtPrice.setText(price+"");

    }

    private void addControls() {
        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtType = findViewById(R.id.edtType);
        edtPrice = findViewById(R.id.edtPrice);

        imgUpdate = findViewById(R.id.imgUpdate);
        btnSelect = findViewById(R.id.btnSelect);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CHOOSE_PHOTO){
                Uri imgUri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    imgUpdate.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int update(){
        int id = Integer.parseInt(edtID.getText().toString());
        String name = edtName.getText().toString();
        String author = edtAuthor.getText().toString();
        String type = edtType.getText().toString();
        double price = Double.parseDouble(edtPrice.getText().toString());


        byte[] img = getByteArrayFromImageView(imgUpdate);

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Author", author);
        contentValues.put("Type", type);
        contentValues.put("Price", price);
        contentValues.put("Image", img);
        SQLiteDatabase database = DBUtils.initDatabase(this, DATABASE_NAME);
        database.update("Book", contentValues, "ID = ?", new String[]{id+""});
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        return 1;


    }
    private byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void cancel(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}