package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.quanly.Model.Order;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKe extends AppCompatActivity {
    List<Order> oderList;
    int soluong = 0;
    int soluong1 = 0;
    int soluong3 = 0;
    int soluong2 = 0;
    int tong;
    ArrayList<PieEntry> entries;
    PieChart pieChart;
    TextView tv_tongtien;
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        pieChart = findViewById(R.id.bieuDo);
        oderList = new ArrayList<>();
        entries = new ArrayList<>();
        tv_tongtien = findViewById(R.id.tv_tongtien);
        setDataHistoryProductAdapter();
        pieChart.animate();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soluong = 0;
                soluong1 = 0;
                soluong2 = 0;
                soluong3 = 0;
                tong = 0;
                entries.clear();
                for (DataSnapshot dataDetail : snapshot.getChildren()) {
                    Order detailOrder = dataDetail.getValue(Order.class);
                    detailOrder.setOrderNo(dataDetail.getKey());
                    if (detailOrder.getTrangthai().equalsIgnoreCase("Đã Nhận")) {
                        HashMap<String,Object> hashMap= (HashMap<String, Object>)dataDetail.getValue();
                        Object tien =hashMap.get("tongtien");
                        int tongtien =Integer.parseInt(String.valueOf(tien));
                        tong+=tongtien;
                        Log.e("sfgshflf", "" + detailOrder.getTongtien());
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("Đã Hủy")) {
                        soluong++;
                        Log.e("eeeeee", "" + soluong);
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("Đã Nhận")) {
                        soluong1++;
                        Log.e("eeeeee", "" + soluong);
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("Đang Chờ Xác Nhận")) {
                        soluong2++;
                        Log.e("eeeeee", "" + soluong);
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("Đang vận chuyển")) {
                        soluong3++;
                    }
                }
                tv_tongtien.setText(formatter.format( tong)+ " VND");

                // set data HistoryProductAdapter
                setDataHistoryProductAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDataHistoryProductAdapter() {
        entries.add(new PieEntry(soluong, "Đã Hủy"));
        entries.add(new PieEntry(soluong1, "Đã Nhận"));
        entries.add(new PieEntry(soluong2, "Đang Chờ Xác Nhận"));
        entries.add(new PieEntry(soluong3, "Đang vận chuyển"));

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