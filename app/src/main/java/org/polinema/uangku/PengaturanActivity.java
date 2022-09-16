package org.polinema.uangku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.polinema.uangku.helper.DatabaseHelper;

public class PengaturanActivity extends AppCompatActivity {

    EditText editTxtPassOld, editTxtPassNew;

    Button btnSimpan, btnKembali;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        editTxtPassOld = (EditText) findViewById(R.id.inputTxtPassOld);
        editTxtPassNew = (EditText) findViewById(R.id.inputTxtPassNew);

        btnSimpan = (Button) findViewById(R.id.btnSimpanSetting);
        btnKembali = (Button) findViewById(R.id.btnKembaliSetting);

        databaseHelper = new DatabaseHelper(this);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalisett = new Intent(PengaturanActivity.this , HomeActivity.class);
                startActivity(kembalisett);
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passOld = editTxtPassOld.getText().toString();
                String passNew = editTxtPassNew.getText().toString();

                Boolean cekPass = databaseHelper.getPassword(passOld);
                Boolean updatePassword = databaseHelper.updatePass(passNew);

                if (passOld.equals("") || passNew.equals("")) {
                    Toast.makeText(PengaturanActivity.this, "Harap isi semua", Toast.LENGTH_SHORT).show();
                } else {
                    if (cekPass) {
                        if (updatePassword) {
                            Toast.makeText(PengaturanActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                            editTxtPassOld.getText().clear();
                            editTxtPassNew.getText().clear();
                        }
                    } else {
                        Toast.makeText(PengaturanActivity.this, "Password gagal diubah", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}