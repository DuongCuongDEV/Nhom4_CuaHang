package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.fpoly.quanly.Adapter.HoaDonAdapter;
import com.fpoly.quanly.Model.Order;
import com.fpoly.quanly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    RecyclerView rcv;
    private List<Order> oderList;
    HoaDonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        oderList = new ArrayList<>();
        rcv =findViewById(R.id.rcv_tatca);
        findViewById(R.id.back).setOnClickListener(view -> {
            super.onBackPressed();
        });
        findOrder();
    }
    private void setDataHistoryProductAdapter(){
        rcv.setHasFixedSize(true);
        adapter=new HoaDonAdapter();
        adapter.setData(oderList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
    }
    private void findOrder(){
        // Kết nối tới data base
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Order");
        // Lấy thông tin order
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                oderList.clear();
                for (DataSnapshot dataOrder : snapshot.getChildren()){
                    Order order = dataOrder.getValue(Order.class);
                    order.setOrderNo(dataOrder.getKey());
                    oderList.add(0,order);
                }
                setDataHistoryProductAdapter();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HoaDonActivity.this,"Không lấy được thông tin đơn hàng từ firebase",Toast.LENGTH_SHORT).show();
            }
        });
    }
}