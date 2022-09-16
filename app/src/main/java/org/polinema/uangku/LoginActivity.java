package org.polinema.uangku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.polinema.uangku.helper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity{
    private final AppCompatActivity activity = LoginActivity.this;
    RelativeLayout relativeLayout;

    //Deklarasi EditText
    EditText editTextUsername;
    EditText editTextPassword;

    //Deklarasi Button
    Button btnLogin;

    //Deklarasi SqliteHelper
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        editTextUsername = (EditText) findViewById(R.id.input_user);
        editTextPassword = (EditText) findViewById(R.id.input_pass);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if(username.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this, "Masukan username dan password", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUserPass = databaseHelper.checkUserPass(username, password);
                    if(checkUserPass==true){
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent beranda = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(beranda);
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Gagal! Cek Input", Toast.LENGTH_SHORT).show();
                    }
                }

//                    if (res = true){
//                        if (username != ("") || password !=("")){
//                        Intent beranda = new Intent(LoginActivity.this , HomeActivity.class);
//                        startActivity(beranda);
//                        Toast.makeText(LoginActivity.this,  "Log in Berhasil ", Toast.LENGTH_SHORT).show();
//                    }else
//                        {
//                            Toast.makeText(LoginActivity.this, "Log in Gagal ", Toast.LENGTH_SHORT).show();
//
//                        }
//                        finish();
//                }

            }
        });

    }


}