package com.fpoly.quanly.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.quanly.Activity.Home;
import com.fpoly.quanly.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    Button btnLogin;
    EditText txtEmail, txtpass;
    FirebaseAuth mAuth;
    TextView tv_dk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btn_login);
        txtEmail = findViewById(R.id.ed_userName);
        txtpass = findViewById(R.id.ed_pasWord);
//        tv_dk = findViewById(R.id.tv_signUp);
//        tv_dk.setOnClickListener(view -> {
//            startActivity(new Intent(this, SignUp.class));
//        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login(){

        if(validate() > 0) {
            String email, pass;
            email = txtEmail.getText().toString();
            pass = txtpass.getText().toString();

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Vui long nhap email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pass)){
                Toast.makeText(this, "Vui long nhap password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignIn.this, Home.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public int validate() {
        int check = 1;
        String checkemail = "[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)";
        if (txtEmail.getText().length() == 0) {
//            dialog.dismiss();
            Toast.makeText(SignIn.this, "Email kh??ng ???????c ????? tr???ng.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        } else if (!txtEmail.getText().toString().matches(checkemail)) {
//            dialog.dismiss();
            Toast.makeText(SignIn.this, "Email kh??ng ????ng ?????nh d???ng.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        } else if (txtpass.getText().length() == 0) {
//            dialog.dismiss();
            Toast.makeText(SignIn.this, "M???t kh???u kh??ng ???????c ????? tr???ng.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        } else if (txtpass.getText().length() < 6) {
//            dialog.dismiss();
            Toast.makeText(SignIn.this, "M???t kh???u ph???i l???n h??n 6 k?? t???.",
                    Toast.LENGTH_LONG).show();
            return check - 1;
        }
        return check;
    }
}