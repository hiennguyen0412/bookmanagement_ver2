package com.example.hiennv.bookmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiennv.bookmanagement.model.Book;
import com.example.hiennv.bookmanagement.utils.DBUtils;

import java.util.ArrayList;

/**
 * Created by hiennv on 23/03/2018.
 */

public class AdapterBook extends BaseAdapter {
    Activity context;
    ArrayList<Book> list;

    public AdapterBook(Activity context, ArrayList<Book> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        TextView txtAuthor = row.findViewById(R.id.txtAuthor);
        TextView txtName = row.findViewById(R.id.txtName);
        TextView txtPrice = row.findViewById(R.id.txtPrice);
        TextView txtType = row.findViewById(R.id.txtType);
        ImageView imgHDD = row.findViewById(R.id.imgHinhDaiDien);
        Button btnEdit = row.findViewById(R.id.btnEdit);
        Button btnDel = row.findViewById(R.id.btnDel);

        final Book book = list.get(i);
        txtAuthor.setText(book.getAuthor());
        txtName.setText(book.getName());
        txtPrice.setText(book.getPrice()+"");
        txtType.setText(book.getType());
        Bitmap bm = BitmapFactory.decodeByteArray(book.getImage(),0,book.getImage().length);
        imgHDD.setImageBitmap(bm);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("ID", book.getId());
                context.startActivity(intent);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Delete book")
                        .setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                deleteBook(book.getId());
                                Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        return row;

    }

    private void deleteBook(int idBook) {
        SQLiteDatabase database = DBUtils.initDatabase(context, "BookManagement.sqlite");
        database.delete("Book", "ID = ?", new String[]{idBook+""});
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
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }


    
}
