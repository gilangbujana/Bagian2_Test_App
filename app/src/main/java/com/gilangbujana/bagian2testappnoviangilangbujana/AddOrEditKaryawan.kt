package com.gilangbujana.bagian2testappnoviangilangbujana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gilangbujana.bagian2testappnoviangilangbujana.db.DatabaseHandler
import com.gilangbujana.bagian2testappnoviangilangbujana.models.Karyawan
import kotlinx.android.synthetic.main.activity_add_or_edit_karyawan.*

class AddOrEditKaryawan : AppCompatActivity() {

    var dbHandler : DatabaseHandler? = null
    var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit_karyawan)
        initDB()
        initOperations()
    }

    private fun initDB(){
        dbHandler = DatabaseHandler(this)
        if(intent != null && intent.getStringExtra("Mode") == "edit") {
            isEditMode = true
            val karyawan : Karyawan = dbHandler!!.getKaryawan(intent.getIntExtra("Id",0))
            etNama.setText(karyawan.nama)
            etPosisi.setText(karyawan.posisi)
            etTelp.setText(karyawan.telp)
            etEmail.setText(karyawan.email)
            etJoin.setText(karyawan.joinDate)
            etAlamat.setText(karyawan.alamat)
        }
    }

    private fun initOperations(){
        btnSimpan.setOnClickListener {
            var sukses = false
            val karyawan: Karyawan = Karyawan()
            karyawan.nama = etNama.text.toString()
            karyawan.posisi = etPosisi.text.toString()
            karyawan.telp = etTelp.text.toString()
            karyawan.email = etEmail.text.toString()
            karyawan.joinDate = etJoin.text.toString()
            karyawan.alamat = etAlamat.text.toString()

            if(isEditMode) {
                karyawan.id = intent.getIntExtra("Id", 0)
                sukses = dbHandler?.updateKaryawan(karyawan) as Boolean
            }else
                sukses = dbHandler?.addKaryawan(karyawan) as Boolean

            if(sukses)
                finish()
        }
    }
}