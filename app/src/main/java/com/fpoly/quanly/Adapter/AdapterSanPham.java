package com.fpoly.quanly.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.quanly.R;
import com.fpoly.quanly.Model.Uploadinfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.Viewhoder> {
    private List<Uploadinfo> list;
    private Context context;
    DecimalFormat formatter = new DecimalFormat("#,###,###");




    public AdapterSanPham(List<Uploadinfo> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_sanpham, parent, false);

        return new Viewhoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, @SuppressLint("RecyclerView") int position) {

        Uploadinfo uploadinfo = list.get(position);
        Picasso.get().load(uploadinfo.getImage()).placeholder(R.drawable.dienthoai).fit().centerCrop().into(holder.img_avata);

        holder.tv_name.setText(" Tên: "+uploadinfo.getName());
        holder.tv_gia.setText(" Giá: "+formatter.format(uploadinfo.getGia())+"VND");

        holder.tv_khuyenmai.setText(" Giảm giá: "+uploadinfo.getKhuyenmai()+"%");
        if(holder.tv_khuyenmai.equals("")){
            holder.tv_khuyenmai.setText(" Giảm giá: 0%");
        }

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img_avata.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1500).create();
//                dialogPlus.show();
                View view = dialogPlus.getHolderView();

                String[] items =  {"Điện Thoại","Laptop","Phụ Kiện","Table","Ốp Lưng"};
                AutoCompleteTextView autoCompleteTxt;
                ArrayAdapter<String> adapterItems;

                autoCompleteTxt = view.findViewById(R.id.auto_complete_txt);
                EditText ten = view.findViewById(R.id.txtNameUp);
                EditText giamGia = view.findViewById(R.id.txtGiaCuUp);
                EditText gia = view.findViewById(R.id.txtGiaMoiUp);
                EditText moTaUp = view.findViewById(R.id.txtMoTaUp);
                EditText loaiUp = view.findViewById(R.id.txtLoaiUp);

                Button btnUp = view.findViewById(R.id.btUP);

                adapterItems = new ArrayAdapter<String>(view.getContext(),R.layout.select_item,items);
                autoCompleteTxt.setAdapter(adapterItems);

                autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();
                        loaiUp.setText(item);
                    }
                });




                ten.setText(uploadinfo.getName());
                giamGia.setText(uploadinfo.getKhuyenmai());
                gia.setText(uploadinfo.getGia()+"");
                moTaUp.setText(uploadinfo.getMoTa());
                loaiUp.setText(uploadinfo.getLoai());

                dialogPlus.show();

                btnUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", ten.getText().toString());
                        map.put("khuyenmai", giamGia.getText().toString());
                        map.put("Gia", Integer.parseInt(gia.getText().toString()));
                        map.put("moTa", moTaUp.getText().toString());
                        map.put("loai", loaiUp.getText().toString());



                            FirebaseDatabase.getInstance().getReference().child("SanPham")
                                    .child(uploadinfo.getId()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.tv_name.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.tv_name.getContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });
                    }
                });


            }
        });



        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tv_name.getContext());
                builder.setTitle("Bạn muốn xoá sản phẩm?");
                builder.setMessage("Bấm xoá để xoá");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SanPham");
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("TAG", uploadinfo.getId());
                        reference.child(uploadinfo.getId()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.tv_name.getContext(), "Đã huỷ xoá.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewhoder extends RecyclerView.ViewHolder {
        private ImageView img_avata;
        private TextView tv_name,tv_gia,tv_khuyenmai;
        private ImageView btnDelete, btnUpdate;


        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            img_avata = itemView.findViewById(R.id.image);
            tv_name = itemView.findViewById(R.id.name);
            tv_gia = itemView.findViewById(R.id.gia);
            tv_khuyenmai = itemView.findViewById(R.id.khuyenmai);
            btnDelete = itemView.findViewById(R.id.delete);
            btnUpdate = itemView.findViewById(R.id.edit);

        }
    }
}