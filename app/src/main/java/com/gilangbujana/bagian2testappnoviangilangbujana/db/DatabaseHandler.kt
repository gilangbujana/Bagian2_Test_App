package com.gilangbujana.bagian2testappnoviangilangbujana.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.gilangbujana.bagian2testappnoviangilangbujana.models.Karyawan
import java.util.ArrayList

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, " +
                "$NAMA TEXT," +
                "$POSISI TEXT," +
                "$TELP TEXT," +
                "$EMAIL TEXT," +
                "$JOIN TEXT," +
                "$ALAMAT TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addKaryawan(karyawan: Karyawan): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAMA, karyawan.nama)
        values.put(POSISI, karyawan.posisi)
        values.put(TELP, karyawan.telp)
        values.put(EMAIL, karyawan.email)
        values.put(JOIN, karyawan.joinDate)
        values.put(ALAMAT, karyawan.alamat)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getKaryawan(_id: Int): Karyawan {
        val karyawan = Karyawan()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        karyawan.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        karyawan.nama = cursor.getString(cursor.getColumnIndex(NAMA))
        karyawan.posisi = cursor.getString(cursor.getColumnIndex(POSISI))
        karyawan.telp = cursor.getString(cursor.getColumnIndex(TELP))
        karyawan.email = cursor.getString(cursor.getColumnIndex(EMAIL))
        karyawan.joinDate = cursor.getString(cursor.getColumnIndex(JOIN))
        karyawan.alamat = cursor.getString(cursor.getColumnIndex(ALAMAT))
        cursor.close()
        return karyawan
    }

    fun karyawann(): List<Karyawan> {
        val karyawanList = ArrayList<Karyawan>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val karyawan = Karyawan()
                    karyawan.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    karyawan.nama = cursor.getString(cursor.getColumnIndex(NAMA))
                    karyawan.posisi = cursor.getString(cursor.getColumnIndex(POSISI))
                    karyawan.telp = cursor.getString(cursor.getColumnIndex(TELP))
                    karyawan.email = cursor.getString(cursor.getColumnIndex(EMAIL))
                    karyawan.joinDate = cursor.getString(cursor.getColumnIndex(JOIN))
                    karyawan.alamat = cursor.getString(cursor.getColumnIndex(ALAMAT))
                    karyawanList.add(karyawan)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return karyawanList
    }

    fun updateKaryawan(karyawan: Karyawan): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAMA, karyawan.nama)
        values.put(POSISI, karyawan.posisi)
        values.put(TELP, karyawan.telp)
        values.put(EMAIL, karyawan.email)
        values.put(JOIN, karyawan.joinDate)
        values.put(ALAMAT, karyawan.alamat)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(karyawan.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteKaryawan(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllKaryawan(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "MyKaryawan"
        private val TABLE_NAME = "Karyawan"
        private val ID = "Id"
        private val NAMA = "Nama"
        private val POSISI = "Posisi"
        private val TELP = "Telp"
        private val EMAIL = "Email"
        private val JOIN = "JoinDate"
        private val ALAMAT = "Alamat"
    }
}