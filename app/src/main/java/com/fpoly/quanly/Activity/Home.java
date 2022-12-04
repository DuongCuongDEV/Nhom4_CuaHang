package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.quanly.Adapter.HoaDonAdapter;
import com.fpoly.quanly.Login.SignIn;
import com.fpoly.quanly.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    TextView btnHoaDon,btnThongKe,btnSanPham,btnExit;
    ImageView quanLySP, thongKe, exit,hoadon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHoaDon = findViewById(R.id.btnHoaDon);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnSanPham = findViewById(R.id.btnSanPham);
        btnExit = findViewById(R.id.btnExit);

        thongKe = findViewById(R.id.ThongKe);
        quanLySP = findViewById(R.id.SanPham);
        hoadon=findViewById(R.id.HoaDon);
        exit = findViewById(R.id.Exit);



        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ThongKe.class);
                startActivity(intent);
            }
        });

        thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ThongKe.class);
                startActivity(intent);
            }
        });

        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuanLySanPham.class);
                startActivity(intent);
            }
        });

        quanLySP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,QuanLySanPham.class);
                startActivity(intent);
            }
        });
        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, HoaDonActivity.class);
                startActivity(intent);
            }
        });

        hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,HoaDonActivity.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(exit.getContext());
                builder.setTitle("Bạn muốn thoát ??");
                builder.setMessage("Bấm thoát để thoát");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("TAG", uploadinfo.getId());
                        Intent intent = new Intent(Home.this,SignIn.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Home.this, "Huỷ thoát", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(exit.getContext());
                builder.setTitle("Bạn muốn thoát ??");
                builder.setMessage("Bấm thoát để thoát");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("TAG", uploadinfo.getId());
                        Intent intent = new Intent(Home.this,SignIn.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Home.this, "Huỷ thoát", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
}