package org.polinema.uangku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.polinema.uangku.helper.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PengeluaranActivity extends AppCompatActivity {
    EditText editTxtTgl, editTxtNominal, editTxtKeterangan;

    ImageButton imgTanggal;

    private int mDate, mMonth, mYear;

    Button btnSimpan, btnKembali;

    DatabaseHelper databaseHelper;

    final Calendar Tgl = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        editTxtTgl = (EditText) findViewById(R.id.editTextDatePengeluaran);
        editTxtNominal = (EditText) findViewById(R.id.editTextNominalPengeluaran);
        editTxtKeterangan = (EditText) findViewById(R.id.editTextKeteranganPengeluaran);

        imgTanggal = (ImageButton) findViewById(R.id.imgTanggalPengeluaran);

        btnSimpan = (Button) findViewById(R.id.btnSimpanPengeluaran);
        btnKembali = (Button) findViewById(R.id.btnKembaliPengeluaran);

        databaseHelper = new DatabaseHelper(this);



        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Tgl.set(Calendar.YEAR, year);
                Tgl.set(Calendar.MONTH,month);
                Tgl.set(Calendar.DAY_OF_MONTH,day);
                dateFormat();
            }
        };

        imgTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(PengeluaranActivity.this,date,
                        Tgl.get(Calendar.YEAR),Tgl.get(Calendar.MONTH),Tgl.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalipeng = new Intent(PengeluaranActivity.this , HomeActivity.class);
                startActivity(kembalipeng);
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tgl = editTxtTgl.getText().toString();
                String nominalMasuk = editTxtNominal.getText().toString();
                String ketMasuk = editTxtKeterangan.getText().toString();
                String peng = "Pengeluaran";


                if (tgl.equals("") ||nominalMasuk.equals("")|| ketMasuk.equals("")){
                    Toast.makeText(PengeluaranActivity.this, "Isi Keseluruhan Data", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean insertPeng = databaseHelper.insertDataTransaksi(tgl, nominalMasuk, ketMasuk, peng);
                    if(insertPeng){
                        Toast.makeText(PengeluaranActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        editTxtTgl.getText().clear();
                        editTxtNominal.getText().clear();
                        editTxtKeterangan.getText().clear();
                    }
                }
            }
        });
    }

    private void dateFormat(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editTxtTgl.setText(dateFormat.format(Tgl.getTime()));
    }
}