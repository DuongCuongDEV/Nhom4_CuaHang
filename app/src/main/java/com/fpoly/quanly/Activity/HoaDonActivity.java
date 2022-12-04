package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.quanly.Adapter.HoaDonAdapter;
import com.fpoly.quanly.Model.Hoadon;
import com.fpoly.quanly.Model.Oder;
import com.fpoly.quanly.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    RecyclerView rcv;
    private List<Hoadon> hoaDonList;
    private List<Oder> oderList;
    HoaDonAdapter adapter;
    TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        hoaDonList = new ArrayList<>();
        oderList = new ArrayList<>();
        rcv =findViewById(R.id.rcv_tatca);
        findViewById(R.id.back).setOnClickListener(view -> {
            super.onBackPressed();
        });

        findOrder();
    }
    private void setDataHistoryProductAdapter(){
        adapter=new HoaDonAdapter();
        adapter.setData(hoaDonList,oderList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
    }
    private void findOrder(){
        // Kết nối tới data base
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email1=user.getEmail();
        email1=email1.replace(".","_");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Oder/"+email1);
        // Lấy thông tin order
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                oderList.clear();
                for (DataSnapshot dataOrder : snapshot.getChildren()){
                    Log.d("TAG", "onDataChange: " + dataOrder.toString());
                    Oder order = dataOrder.getValue(Oder.class);
                    order.setOrderNo(dataOrder.getKey());
                    Log.e("ssssssss",dataOrder.getKey());
                    Log.d("zzzzzzz", "onDataChange: " + order.getTenkhachhang());
                    oderList.add(0,order);
                }
                if (oderList.size() > 0){
                    // Lấy thông tin detail order
                    findDetailOrder(myRef);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HoaDonActivity.this,"Không lấy được thông tin đơn hàng từ firebase",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Lấy thông tin detail order
    private void findDetailOrder( DatabaseReference myRef){
        hoaDonList.clear();
        if (oderList.size() > 0){
            for (int i = 0; i<oderList.size(); i++){
                Oder order = oderList.get(i);
                myRef.child(order.getOrderNo()).child("detail").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataDetail : snapshot.getChildren()){
                            Hoadon detailOrder = dataDetail.getValue(Hoadon.class);
                            detailOrder.setIdHoadon(dataDetail.getKey());
                            hoaDonList.add(detailOrder);
                        }
                        // set data HistoryProductAdapter
                        setDataHistoryProductAdapter();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HoaDonActivity.this,"Không lấy được chi tiết đơn hàng từ firebase",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}