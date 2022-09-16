package org.polinema.uangku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.polinema.uangku.helper.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    ImageButton imageButtonPemasukan, imageButtonPengeluaran, imageButtonPengaturan, imageButtonDetailCashFlow;

    TextView txtPemasukan, txtPengeluaran;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = new DatabaseHelper(this);

        DateFormat dateFormat = new SimpleDateFormat("MM", Locale.US);
        Date date = new Date();

        String currentMonth = dateFormat.format(date);
        Log.d("Current month", currentMonth);

        imageButtonPemasukan = (ImageButton) findViewById(R.id.imgBtnPemasukan);
        imageButtonPengeluaran = (ImageButton) findViewById(R.id.imgBtnPengeluaran);
        imageButtonPengaturan = (ImageButton) findViewById(R.id.imgBtnPengaturan);
        imageButtonDetailCashFlow = (ImageButton) findViewById(R.id.imgBtnDetailCashFlow);

        txtPemasukan = (TextView) findViewById(R.id.txt_Pemasukan);
        txtPengeluaran = (TextView) findViewById(R.id.txt_Pengeluaran);

        imageButtonPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pemasukan = new Intent(HomeActivity.this , PemasukanActivity.class);
                startActivity(pemasukan);
            }
        });

        imageButtonPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pengeluaran = new Intent(HomeActivity.this , PengeluaranActivity.class);
                startActivity(pengeluaran);
            }
        });

        imageButtonDetailCashFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailcashflow = new Intent(HomeActivity.this , DetailCashFlowActivity.class);
                startActivity(detailcashflow);
            }
        });

        imageButtonPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pengaturan = new Intent(HomeActivity.this , PengaturanActivity.class);
                startActivity(pengaturan);
            }
        });

        setTotalPengeluaran();
        setTotalPemasukan();
    }

    private void setTotalPemasukan() {
        Cursor cursor = databaseHelper.totalPemasukan();
        StringBuilder str = new StringBuilder();
        while(cursor.moveToNext()){
            str.append("Pemasukan : Rp. ").append(cursor.getInt(0));
        }

        txtPemasukan.setText(str);
    }

    private void setTotalPengeluaran() {
        Cursor cursor = databaseHelper.totalPengeluaran();
        StringBuilder str = new StringBuilder();
        while(cursor.moveToNext()){
            str.append("Pengeluaran : Rp. ").append(cursor.getInt(0));
        }

        txtPengeluaran.setText(str);
    }
}