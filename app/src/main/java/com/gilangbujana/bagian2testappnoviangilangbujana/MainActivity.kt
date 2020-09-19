package com.gilangbujana.bagian2testappnoviangilangbujana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gilangbujana.bagian2testappnoviangilangbujana.db.DatabaseHandler
import com.gilangbujana.bagian2testappnoviangilangbujana.models.Karyawan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var dbHandler: DatabaseHandler? = null
    var listKaryawan: ArrayList<Karyawan> = ArrayList<Karyawan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initOperations()
        initDB()
    }

    fun initDB(){
        dbHandler = DatabaseHandler(this)
        listKaryawan = (dbHandler as DatabaseHandler).karyawann() as ArrayList<Karyawan>
        rvKaryawan.adapter = KaryawanAdapter(listKaryawan, this)
    }

    fun initViews(){
        rvKaryawan.adapter = KaryawanAdapter(karyawanList = listKaryawan, context = this)
    }

    fun initOperations(){
        fabTambahKaryawan.setOnClickListener {
            val intent = Intent(this, AddOrEditKaryawan::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }

}