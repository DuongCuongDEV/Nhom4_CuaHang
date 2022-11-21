package com.fpoly.quanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuanLySanPham extends AppCompatActivity {
    GridView lvSanPham;
//    Button btnThemSP;
FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        lvSanPham = findViewById(R.id.lvSanPham);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLySanPham.this,ThemSanPham.class);
                startActivity(intent);
            }
        });
//        lvSanPham(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                item = list.get(position);
//                openDialog(getActivity(),1);//update
//                return false;
//            }
//        });
    }
}