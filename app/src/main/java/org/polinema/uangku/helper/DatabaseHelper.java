package org.polinema.uangku.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //versi database
    private static final int DATABASE_VERSION = 1;

    //set nama database
    static String DATABASE_NAME="uangku.db";

    //membuat tabel user
    public static final String TABLE_NAME_User="UserUangku";
    public static final String Table_Column_ID_User ="id";
    public static final String Table_Column_1_Username="username";
    public static final String Table_Column_2_Password="password";

    //membuat tabel uang
    public static final String TABLE_NAME_UANG="DataTransaki";
    public static final String Table_Column_ID_Uang="id_transaksi";
    public static final String Table_Column_1_Tgl="tgl_transaksi";
    public static final String Table_Column_2_Nominal="nominal_transaksi";
    public static final String Table_Column_3_Keterangan="ket_transaksi";
    public static final String Table_Column_4_Status="status_transaksi";

    //query create table user
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME_User + "("
            + Table_Column_ID_User + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Table_Column_1_Username + " TEXT,"
            + Table_Column_2_Password + " TEXT" + ")";

    //query create table uang
    private String CREATE_UANG_TABLE = "CREATE TABLE " + TABLE_NAME_UANG + "("
            + Table_Column_ID_Uang + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Table_Column_1_Tgl + " TEXT,"
            + Table_Column_2_Nominal + " NUMERIC,"
            + Table_Column_3_Keterangan +" TEXT, "
            + Table_Column_4_Status + " TEXT)";

    //query drop table user
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_User;
    private String DROP_UANG_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_UANG;

    //create user
    private String INPUT_USER_TABLE = "INSERT INTO " + TABLE_NAME_User + " VALUES ('1','user','user')";

    //cek user
    private String CHECK_PASS_TABLE = "SELECT * FROM " + TABLE_NAME_User + " WHERE " + Table_Column_2_Password + " = ?";

    //cek user & pass
    private String CHECK_USER_PASS_TABLE = "SELECT * FROM " + TABLE_NAME_User + " WHERE "
            + Table_Column_1_Username + " = ? and " + Table_Column_2_Password + "= ?";

    //select tabel uang

    private String SELECT_UANG = "SELECT * FROM " +TABLE_NAME_UANG;
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_UANG_TABLE);
        db.execSQL(INPUT_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop tabel user jika ada
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_UANG_TABLE);

        //Membuat ulang tabel
        onCreate(db);
    }

    public Boolean checkUserPass(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(CHECK_USER_PASS_TABLE, new String[] {username, password});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean insertDataTransaksi(String tgl_transaksi, String nominal, String keterangan, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table_Column_1_Tgl, tgl_transaksi);
        contentValues.put(Table_Column_2_Nominal, nominal);
        contentValues.put(Table_Column_3_Keterangan, keterangan);
        contentValues.put(Table_Column_4_Status, status);
        long hasilInput = db.insert(TABLE_NAME_UANG, null, contentValues);
        if (hasilInput==-1)
            return false;
        else
            return true;

    }

    public Boolean updatePass(String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table_Column_2_Password, pass);

        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NAME_User + " WHERE id = 1",
                null);

        if (cursor.getCount()>0){
            long hasilupdate = db.update(
                    TABLE_NAME_User, contentValues,  " id = 1",null);
            if(hasilupdate==-1){
                return false;
            }else{
                return true;
            }
        }else {
            return false;
        }
    }

    public Cursor getDataTransaksi(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NAME_UANG
                + " ORDER BY " + Table_Column_ID_Uang+" AND "+Table_Column_1_Tgl+" DESC", null);
        return cursor;

    }

    public Boolean getPassword(String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME_User +" WHERE password=?", new String[] {password});
        return cursor.getCount()>0;

    }

    public ArrayList<String> getListTransaksi(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        String getTransaksi = SELECT_UANG;
        Cursor cursor = db.rawQuery(getTransaksi, null);

        if (cursor.moveToFirst()){
            do {
                arrayList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return arrayList;
    }

    public Cursor totalPemasukan(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT SUM("+Table_Column_2_Nominal+") AS totalKeluar FROM "
                + TABLE_NAME_UANG+"  WHERE " +
                "strftime('%m',tgl_transaksi) = strftime('%m',date('now')) AND " +
                "strftime('%Y',tgl_transaksi) = strftime('%Y',date('now')) AND " +
                Table_Column_4_Status+ "= 'Pemasukan'", null);
//        return db.rawQuery("SELECT SUM("+Table_Column_2_Nominal+") AS TotalPemasukan FROM "
//                + TABLE_NAME_UANG+" WHERE "+Table_Column_4_Status+" = 'Pemasukan'", null);

    }

    public Cursor totalPengeluaran(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT SUM(nominal_transaksi) AS totalKeluar FROM "
                + TABLE_NAME_UANG+"  WHERE " +
                "strftime('%m',tgl_transaksi) = strftime('%m',date('now')) AND " +
                "strftime('%Y',tgl_transaksi) = strftime('%Y',date('now')) AND " +
                Table_Column_4_Status+ "= 'Pengeluaran'", null);
//        return db.rawQuery("SELECT SUM("+Table_Column_2_Nominal+") AS TotalPemasukan FROM "
//                + TABLE_NAME_UANG+" WHERE "+Table_Column_4_Status+" = 'Pengeluaran'", null);

    }
}
