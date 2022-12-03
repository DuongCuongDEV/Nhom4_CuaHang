package com.fpoly.quanly.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fpoly.quanly.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ThemSanPham extends AppCompatActivity {
    Button btnupload,btnshow;
    EditText txtname;
    EditText txtMoTa;
    EditText txtGiaCu;
    EditText txtGiaMoi;
    EditText txtLoai;
    ImageButton imageButton;
    FirebaseDatabase mdatabase;
    DatabaseReference databaseReference;
    FirebaseStorage mstorage;
    private static final int Gallery_code = 1;
    Uri imgurl = null;
    ProgressDialog dialog;

    String[] items =  {"Điện Thoại","Máy Tính","Tai Nghe"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        imageButton = findViewById(R.id.imageview);
        txtname = findViewById(R.id.txtName);
        txtMoTa = findViewById(R.id.txtMoTa);
        txtGiaCu = findViewById(R.id.txtGiaCu);
        txtGiaMoi = findViewById(R.id.txtGiaMoi);
        txtLoai = findViewById(R.id.txtLoai);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);


        adapterItems = new ArrayAdapter<String>(this,R.layout.select_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                txtLoai.setText(item);
            }
        });


        btnupload = findViewById(R.id.btnupload);
        mdatabase = FirebaseDatabase.getInstance();
        databaseReference = mdatabase.getReference().child("SanPham");
        mstorage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Gallery_code);
            }
        });
//        btnshow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,Showimge.class));
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_code && resultCode == RESULT_OK) {
            imgurl = data.getData();
            imageButton.setImageURI(imgurl);
        }
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtname.getText().toString().trim();
                String moTa = txtMoTa.getText().toString().trim();
                String giaCu = txtGiaCu.getText().toString().trim();
                int giaMoi = Integer.parseInt(txtGiaMoi.getText().toString().trim());
                String loai = txtLoai.getText().toString().trim();

                if (!(name.isEmpty() && imgurl != null)) {
                    dialog.setTitle("Đang tải.....");
                    dialog.show();
                    StorageReference reference = mstorage.getReference().child("imagepost").child(imgurl.getLastPathSegment());
                    DatabaseReference newpost = databaseReference.push();
                    reference.putFile(imgurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    newpost.child("image").setValue(task.getResult().toString());
                                    dialog.dismiss();
                                }
                            });
                            newpost.child("name").setValue(name);
                            newpost.child("moTa").setValue(moTa);
                            newpost.child("khuyenmai").setValue(giaCu);
                            newpost.child("Gia").setValue(giaMoi);
                            newpost.child("loai").setValue(loai);
                        }
                    });
                    startActivity(new Intent(ThemSanPham.this, QuanLySanPham.class));
                }
            }
        });
    }
}