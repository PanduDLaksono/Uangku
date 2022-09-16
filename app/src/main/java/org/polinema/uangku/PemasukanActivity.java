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
import java.util.Date;
import java.util.Locale;

public class PemasukanActivity extends AppCompatActivity {

    EditText editTextTanggal, editTextNominal, editTextKeterangan;
    ImageButton imgTanggal;
    Button btnSimpan, btnKembali;

    private int mDate, mMonth, mYear;

    DatabaseHelper databaseHelper;

    final Calendar Tgl = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        databaseHelper = new DatabaseHelper(this);

        editTextTanggal = (EditText) findViewById(R.id.editTextDatePemasukan);
        editTextNominal = (EditText) findViewById(R.id.editTextNominalPemasukan);
        editTextKeterangan = (EditText) findViewById(R.id.editTextKeteranganPemasukan);

        imgTanggal = (ImageButton) findViewById(R.id.imgTanggal);

        btnSimpan = (Button) findViewById(R.id.btnSimpanPemasukan);
        btnKembali = (Button) findViewById(R.id.btnKembaliPemasukan);

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
                DatePickerDialog datePicker = new DatePickerDialog(PemasukanActivity.this,date,
                        Tgl.get(Calendar.YEAR),Tgl.get(Calendar.MONTH),Tgl.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalipem = new Intent(PemasukanActivity.this , HomeActivity.class);
                startActivity(kembalipem);
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tgl = editTextTanggal.getText().toString();
                String nominalMasuk = editTextNominal.getText().toString();
                String ketMasuk = editTextKeterangan.getText().toString();
                String pem = "Pemasukan";


                if (tgl.equals("") ||nominalMasuk.equals("")|| ketMasuk.equals("")){
                    Toast.makeText(PemasukanActivity.this, "Isi Keseluruhan Data", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean insertPem = databaseHelper.insertDataTransaksi(tgl, nominalMasuk, ketMasuk, pem);
                    if(insertPem){
                        Toast.makeText(PemasukanActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        editTextTanggal.getText().clear();
                        editTextNominal.getText().clear();
                        editTextKeterangan.getText().clear();
                    }
                }
            }
        });
    }

    private void dateFormat(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editTextTanggal.setText(dateFormat.format(Tgl.getTime()));
    }
}