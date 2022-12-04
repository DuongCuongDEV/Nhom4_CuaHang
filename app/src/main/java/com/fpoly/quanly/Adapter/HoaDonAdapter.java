package com.fpoly.quanly.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.quanly.Activity.ChiTietActivity;
import com.fpoly.quanly.Activity.ChitietHoadonActivity;
import com.fpoly.quanly.Model.Hoadon;
import com.fpoly.quanly.Model.Oder;
import com.fpoly.quanly.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHoler>{
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    List<Hoadon> hoaDonList;
    List<Oder> oderList;
    private Oder oder;
    public void setData(List<Hoadon> hoaDonList, List<Oder> oderList) {
        this.hoaDonList = hoaDonList;
        this.oderList = oderList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HoaDonViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new HoaDonViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHoler holder, int position) {
        Hoadon hoadon = hoaDonList.get(position);
        Oder oder1=oderList.get(position);
        if (hoadon == null) {
            return;
        }
        Picasso.get().load(hoadon.getImge()).into(holder.img_anh);
        holder.tv_ten.setText(hoadon.getNamesp());
        holder.tv_soluong.setText(String.valueOf(hoadon.getSoluong()));
        holder.tv_gia.setText(formatPrice.format(hoadon.getGiasp())+" VND");
        holder.tv_trangthai.setText(hoadon.getTrangthai());
        holder.tv_ma.setText(hoadon.getIdOder());
        holder.tv_ngay.setText(oder1.getNgaymua());
        holder.itemView.setOnClickListener(view -> {
            for (Oder od : oderList) {
                if (od.getOrderNo().equals(hoadon.getIdOder())){
                    oder=od;
                    break;
                }
            }
            for (Hoadon hd :hoaDonList){
                if (hoadon.getIdOder().equals(hd.getIdOder())){
                    oder.addListHoaDon(hd);
                }
            }
            Intent intent=new Intent(view.getContext(), ChitietHoadonActivity.class);
            intent.putExtra("oder",oder);
            AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
            view.getContext().startActivity(intent);
        });
        holder.huy.setOnClickListener(v ->{
            oder1.setTrangthai("Đã Hủy");
            hoadon.setTrangthai("Đã Hủy");
            oderList.set(position,oder1);
            hoaDonList.set(position,hoadon);
            FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email1 = user.getEmail();
            email1 = email1.replace(".", "_");
            DatabaseReference mreference = mdatabase.getReference("Oder/" + email1);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("trangthai","Đã Hủy");
            mreference.child(oder1.getOrderNo()).updateChildren(hashMap);
            mreference.child(oder1.getOrderNo()).child("detail").child(hoadon.getIdHoadon()).updateChildren(hashMap);

        });
        if (hoadon.getTrangthai().equals("Đã Hủy")){
            holder.huy.setVisibility(View.GONE);
        }else {
            holder.huy.setVisibility(View.VISIBLE);
        }
        holder.dangvanchuyen.setOnClickListener(v ->{
            oder1.setTrangthai("Đang vận chuyển ");
            hoadon.setTrangthai("Đang vận chuyển");
            oderList.set(position,oder1);
            hoaDonList.set(position,hoadon);
            FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email1 = user.getEmail();
            email1 = email1.replace(".", "_");
            DatabaseReference mreference = mdatabase.getReference("Oder/" + email1);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("trangthai","Đang vận chuyển");
            mreference.child(oder1.getOrderNo()).updateChildren(hashMap);
            mreference.child(oder1.getOrderNo()).child("detail").child(hoadon.getIdHoadon()).updateChildren(hashMap);

        });
        if (hoadon.getTrangthai().equals("Đang vận chuyển")){
            holder.dangvanchuyen.setVisibility(View.GONE);
        }else {
            holder.dangvanchuyen.setVisibility(View.VISIBLE);
        }
        holder.danhan.setOnClickListener(v ->{
            oder1.setTrangthai("Đã nhận");
            hoadon.setTrangthai("Đã nhận");
            oderList.set(position,oder1);
            hoaDonList.set(position,hoadon);
            FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email1 = user.getEmail();
            email1 = email1.replace(".", "_");
            DatabaseReference mreference = mdatabase.getReference("Oder/" + email1);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("trangthai","Đã nhận");
            mreference.child(oder1.getOrderNo()).updateChildren(hashMap);
            mreference.child(oder1.getOrderNo()).child("detail").child(hoadon.getIdHoadon()).updateChildren(hashMap);

        });
        if (hoadon.getTrangthai().equals("Đã nhận")){
            holder.danhan.setVisibility(View.GONE);
        }else {
            holder.danhan.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (hoaDonList.size()!=0){
            return hoaDonList.size();
        }
        return 0;
    }

    public class HoaDonViewHoler extends RecyclerView.ViewHolder{
        TextView tv_ma,tv_ten,tv_gia,tv_ngay,tv_soluong,tv_trangthai;
        Button huy,dangvanchuyen,danhan;
        ImageView img_anh;
        public HoaDonViewHoler(@NonNull View itemView) {
            super(itemView);
            img_anh=itemView.findViewById(R.id.img_anhls);
            tv_ma=itemView.findViewById(R.id.tv_madathang);
            tv_ten=itemView.findViewById(R.id.tv_tenspls);
            tv_soluong=itemView.findViewById(R.id.tv_soluong);
            tv_gia=itemView.findViewById(R.id.tv_gials);
            tv_ngay=itemView.findViewById(R.id.tv_ngay1);
            tv_trangthai=itemView.findViewById(R.id.tv_trangthai);
            huy=itemView.findViewById(R.id.btn_huy);
            dangvanchuyen=itemView.findViewById(R.id.btn_vanchuyen);
            danhan=itemView.findViewById(R.id.btn_danhan);
        }
    }
}
