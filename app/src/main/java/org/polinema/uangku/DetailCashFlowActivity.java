package org.polinema.uangku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.polinema.uangku.helper.Adapter;
import org.polinema.uangku.helper.DatabaseHelper;

import java.util.ArrayList;

public class DetailCashFlowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> nominal, keterangan, tanggal, status;

    DatabaseHelper databaseHelper;

    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cash_flow);

        btnKembali = (Button) findViewById(R.id.btnKembaliDetail);

        databaseHelper = new DatabaseHelper(this);

        nominal = new ArrayList<>();
        keterangan = new ArrayList<>();
        tanggal = new ArrayList<>();
        status = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleView);
        adapter = new Adapter(this, nominal, keterangan, tanggal, status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalidet = new Intent(DetailCashFlowActivity.this , HomeActivity.class);
                startActivity(kembalidet);
                finish();
            }
        });
    }

    private void displayData() {
        Cursor cursor = databaseHelper.getDataTransaksi();
        if (cursor.getCount()==0){
            Toast.makeText(this, "Data Transaksi Tidak Ada", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()){
                nominal.add(cursor.getString(1));
                keterangan.add(cursor.getString(2));
                tanggal.add(cursor.getString(3));
                status.add(cursor.getString(4));
            }
        }
    }


}