package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class ThongKe extends AppCompatActivity {
    List<Order> oderList;
    int soluong = 0;
    int soluong1 = 0;
    int soluong3 = 0;
    int soluong2 = 0;
    ArrayList<PieEntry> entries;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        pieChart = findViewById(R.id.bieuDo);
        oderList = new ArrayList<>();
        entries=new ArrayList<>();
        setDataHistoryProductAdapter();
        pieChart.animate();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soluong=0;soluong1=0;soluong2=0;soluong3=0;
                entries.clear();
                for (DataSnapshot dataDetail : snapshot.getChildren()) {
                    Order detailOrder = dataDetail.getValue(Order.class);
                    detailOrder.setOrderNo(dataDetail.getKey());
                    if (detailOrder.getTrangthai().equalsIgnoreCase("???? H???y")){
                        soluong++;
                        Log.e("eeeeee",""+soluong);
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("???? nh???n")){
                        soluong1++;
                        Log.e("eeeeee",""+soluong);
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("??ang Ch??? X??c Nh???n")){
                        soluong2++;
                        Log.e("eeeeee",""+soluong);
                    }
                    if (detailOrder.getTrangthai().equalsIgnoreCase("??ang v???n chuy???n")){
                        soluong3++;
                    }
                }
                Log.e("sfgshflf",""+entries.size());
                // set data HistoryProductAdapter
                setDataHistoryProductAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDataHistoryProductAdapter() {
        entries.add(new PieEntry(soluong, "???? H???y"));
        entries.add(new PieEntry(soluong1, "???? nh???n"));
        entries.add(new PieEntry(soluong2, "??ang Ch??? X??c Nh???n"));
        entries.add(new PieEntry(soluong3, "??ang v???n chuy???n"));

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
        pieChart.setCenterText("Tr???ng Th??i ????n H??ng");
    }


}