package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fpoly.quanly.Model.Hoadon;
import com.fpoly.quanly.Model.Oder;
import com.fpoly.quanly.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThongKe extends AppCompatActivity {
    List<Oder> oderList;
    int soluong = 0;
    int soluong1 = 0;
    int soluong3 = 0;
    int soluong2 = 0;
    ArrayList<PieEntry> entries;
    List<Hoadon> hoadonList;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        pieChart = findViewById(R.id.bieuDo);
        oderList = new ArrayList<>();
        hoadonList = new ArrayList<>();
        entries=new ArrayList<>();
        setDataHistoryProductAdapter();

        pieChart.animate();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OderAdmin");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Oder oder = dataSnapshot.getValue(Oder.class);
                    oderList.add(oder);
                    oder.setOrderNo(dataSnapshot.getKey());
                    Log.e("13231", oder.getOrderNo());
                }
                findDetailOrder(reference);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void findDetailOrder(DatabaseReference myRef) {
        soluong=0;soluong1=0;soluong2=0;soluong3=0;
        hoadonList.clear();
        if (oderList.size() > 0) {
            for (int i = 0; i < oderList.size(); i++) {
                Oder order = oderList.get(i);
                Log.e("đựoqjodqd",""+oderList.size());
                myRef.child(order.getOrderNo()).child("detailadmin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        entries.clear();
                        for (DataSnapshot dataDetail : snapshot.getChildren()) {
                            Hoadon detailOrder = dataDetail.getValue(Hoadon.class);
                            detailOrder.setIdHoadon(dataDetail.getKey());
                            hoadonList.add(detailOrder);
                            if (detailOrder.getTrangthai().equalsIgnoreCase("Đã Hủy")){
                                soluong++;
                                Log.e("eeeeee",""+soluong);
                            }
                            if (detailOrder.getTrangthai().equalsIgnoreCase("Đã nhận")){
                                soluong1++;
                                Log.e("eeeeee",""+soluong);
                            }
                            if (detailOrder.getTrangthai().equalsIgnoreCase("Đang Chờ Xác Nhận")){
                                soluong2++;
                                Log.e("eeeeee",""+soluong);
                            }
                            if (detailOrder.getTrangthai().equalsIgnoreCase("Đang vận chuyển")){
                                soluong3++;
                            }
                        }
                        entries.add(new PieEntry(soluong, "Đã Hủy"));
                        entries.add(new PieEntry(soluong1, "Đã nhận"));
                        entries.add(new PieEntry(soluong2, "Đang Chờ Xác Nhận"));
                        entries.add(new PieEntry(soluong3, "Đang vận chuyển"));
                        Log.e("sfgshflf",""+entries.size());
                        // set data HistoryProductAdapter
                        setDataHistoryProductAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ThongKe.this, "Không lấy được chi tiết đơn hàng từ firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void setDataHistoryProductAdapter() {
        PieDataSet pieDataSet = new PieDataSet(entries, " ");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(17f);
        PieData pieData = new PieData(pieDataSet);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(18);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Trạng Thái Đơn Hàng");
    }


}