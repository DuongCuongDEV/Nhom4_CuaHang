package com.fpoly.quanly.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.quanly.Activity.ChitietHoadonActivity;
import com.fpoly.quanly.Model.Order;
import com.fpoly.quanly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHoler> {
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    List<Order> oderList;

    public void setData(List<Order> oderList) {
        this.oderList = oderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoaDonViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        return new HoaDonViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHoler holder, int position) {
        Order oder1 = oderList.get(position);
        if (oder1 == null) {
            return;
        }
        Picasso.get().load(oder1.getSanphamList().get(0).getImage()).into(holder.img_anh);
        holder.tv_ten.setText(oder1.getSanphamList().get(0).getName());
        holder.tv_soluong.setText(String.valueOf(oder1.getSoluong()));
        holder.tv_gia.setText(formatPrice.format(oder1.getSanphamList().get(0).getGia()) + " VND");
        holder.tv_trangthai.setText(oder1.getTrangthai());
        holder.tv_ma.setText(oder1.getOrderNo());
        holder.tv_ngay.setText(oder1.getNgaymua());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ChitietHoadonActivity.class);
            intent.putExtra("order", oder1);
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            view.getContext().startActivity(intent);
        });
        holder.huy.setOnClickListener(v -> {
            oder1.setTrangthai("???? H???y");
            DatabaseReference mReference=FirebaseDatabase.getInstance().getReference("Order");
            HashMap<String,Object> hashMap =new HashMap<>();
            hashMap.put("trangthai","???? H???y");
            mReference.child(oder1.getOrderNo()).updateChildren(hashMap);
        });
        holder.danhan.setOnClickListener(v -> {
            oder1.setTrangthai("???? Nh???n");
            DatabaseReference mReference=FirebaseDatabase.getInstance().getReference("Order");
            HashMap<String,Object> hashMap =new HashMap<>();
            hashMap.put("trangthai","???? Nh???n");
            mReference.child(oder1.getOrderNo()).updateChildren(hashMap);
        });
        holder.dangvanchuyen.setOnClickListener(v -> {
            oder1.setTrangthai("??ang v???n chuy???n");
            DatabaseReference mReference=FirebaseDatabase.getInstance().getReference("Order");
            HashMap<String,Object> hashMap =new HashMap<>();
            hashMap.put("trangthai","??ang v???n chuy???n");
            mReference.child(oder1.getOrderNo()).updateChildren(hashMap);
        });
        Log.e("666777", "onBindViewHolder: "+oder1.getTrangthai());
        if (oder1.getTrangthai().equals("???? H???y")) {
            holder.huy.setVisibility(View.GONE);
            holder.dangvanchuyen.setVisibility(View.GONE);
            holder.danhan.setVisibility(View.GONE);
        }
        if (oder1.getTrangthai().equals("???? Nh???n")) {
            holder.danhan.setVisibility(View.GONE);
            holder.huy.setVisibility(View.GONE);
            holder.dangvanchuyen.setVisibility(View.GONE);
        }
        if (oder1.getTrangthai().equals("??ang v???n chuy???n")) {
            holder.dangvanchuyen.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
            return oderList.size();
    }
    public class HoaDonViewHoler extends RecyclerView.ViewHolder {
        TextView tv_ma, tv_ten, tv_gia, tv_ngay, tv_soluong, tv_trangthai;
        Button huy, dangvanchuyen, danhan;
        ImageView img_anh;

        public HoaDonViewHoler(@NonNull View itemView) {
            super(itemView);
            img_anh = itemView.findViewById(R.id.img_anhls);
            tv_ma = itemView.findViewById(R.id.tv_madathang);
            tv_ten = itemView.findViewById(R.id.tv_tenspls);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            tv_gia = itemView.findViewById(R.id.tv_gials);
            tv_ngay = itemView.findViewById(R.id.tv_ngay1);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);
            huy = itemView.findViewById(R.id.btn_huy);
            dangvanchuyen = itemView.findViewById(R.id.btn_vanchuyen);
            danhan = itemView.findViewById(R.id.btn_danhan);
        }
    }
}
