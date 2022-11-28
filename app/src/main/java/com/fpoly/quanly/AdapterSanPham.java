package com.fpoly.quanly;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.Viewhoder> {
    private List<Uploadinfo> list;
    private Context context;

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
        holder.tv_name.setText(""+uploadinfo.getName());
        holder.tv_gia.setText(""+uploadinfo.getGia());
        holder.tv_khuyenmai.setText(""+uploadinfo.getKhuyenmai());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            int x = position;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tv_name.getContext());
                builder.setTitle("Ban muon xoa??");
                builder.setMessage("Xoa du lieu");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase data = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = data.getReference("SanPham");

                        list.remove(position);
                        notifyDataSetChanged();



//                        mRef.child().removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
//                            }
//                        });

                    }

                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.tv_name.getContext(), "Canceled.", Toast.LENGTH_SHORT).show();
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
        private ImageView btnDelete;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            img_avata = itemView.findViewById(R.id.image);
            tv_name = itemView.findViewById(R.id.name);
            tv_gia = itemView.findViewById(R.id.gia);
            tv_khuyenmai = itemView.findViewById(R.id.khuyenmai);
            btnDelete = itemView.findViewById(R.id.delete);

//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItem.OnItemClickDelete(getAdapterPosition());
//                }
//            });
        }
    }
}