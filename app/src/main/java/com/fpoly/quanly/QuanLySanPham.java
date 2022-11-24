package com.fpoly.quanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class QuanLySanPham extends AppCompatActivity {
    FloatingActionButton fab;
    FirebaseDatabase mdatabase;
    DatabaseReference databaseReference;
    FirebaseStorage mstorage;
    RecyclerView recyclerView;
    AdapterSanPham adapter;
    List<Uploadinfo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        recyclerView = findViewById(R.id.lvSanPham);
        fab = findViewById(R.id.fab);

        mdatabase = FirebaseDatabase.getInstance();
        databaseReference = mdatabase.getReference().child("SanPham");
        mstorage = FirebaseStorage.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Uploadinfo>();
        adapter = new AdapterSanPham(list, QuanLySanPham.this);
        recyclerView.setAdapter(adapter);

//        adapter.setOnItem(new AdapterSanPham.OnItem() {
//            @Override
//            public void OnItemClickDelete(int position) {
//                list.remove(position);
//                adapter.notifyItemRemoved(position);
//            }
//        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Uploadinfo uploadinfo = snapshot.getValue(Uploadinfo.class);
                list.add(uploadinfo);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

























        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLySanPham.this,ThemSanPham.class);
                startActivity(intent);
            }
        });

    }
}