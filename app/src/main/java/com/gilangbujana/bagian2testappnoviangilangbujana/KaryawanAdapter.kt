package com.gilangbujana.bagian2testappnoviangilangbujana

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gilangbujana.bagian2testappnoviangilangbujana.db.DatabaseHandler
import com.gilangbujana.bagian2testappnoviangilangbujana.models.Karyawan
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_detail_karyawan.view.*
import kotlinx.android.synthetic.main.item_karyawan.view.*
import kotlinx.android.synthetic.main.item_karyawan.view.tvNamaKaryawan
import kotlinx.android.synthetic.main.item_karyawan.view.tvPosisiKaryawan

class KaryawanAdapter (karyawanList : ArrayList<Karyawan>, internal var context: Context)  : RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder>()
{

    internal var karyawanList : java.util.ArrayList<Karyawan>
    val dbhandler : DatabaseHandler = DatabaseHandler(context)
    init {
        this.karyawanList = karyawanList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaryawanViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_karyawan, parent, false)
        return KaryawanViewHolder(view)
    }

    override fun getItemCount(): Int = karyawanList.size

    override fun onBindViewHolder(holder: KaryawanViewHolder, position: Int) {
        val karyawan : Karyawan = karyawanList[position]
        holder.nama.text = karyawan.nama
        holder.posisi.text = karyawan.posisi
        holder.tbDetail.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_detail_karyawan, null)
            view.tvNamaKaryawan.text = karyawan.nama
            view.tvPosisiKaryawan.text = karyawan.posisi
            view.tvTelpKaryawan.text = "Telepon : ${karyawan.telp}"
            view.tvEmailKaryawan.text = "Email : ${karyawan.email}"
            view.tvJoinKaryawan.text = "Join : ${karyawan.joinDate}"
            view.tvAlamatKaryawan.text = "Alamat : ${karyawan.alamat}"
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
            view.tbUbah.setOnClickListener {
                val i = Intent(context, AddOrEditKaryawan::class.java)
                i.putExtra("Mode", "edit")
                i.putExtra("Id", karyawan.id)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                bottomSheetDialog.dismiss()
                context.startActivity(i)
            }

            view.tbLihatPeta.setOnClickListener {

            }
        }


        holder.tbHapus.setOnClickListener {
            hapus(position)
        }
    }

    fun hapus(position: Int) {
        if(dbhandler.deleteKaryawan(karyawanList[position].id)){
            karyawanList.remove(karyawanList[position])
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, karyawanList.size)
        }

    }


   inner class KaryawanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var nama: TextView = itemView.tvNamaKaryawan
       var posisi: TextView = itemView.tvPosisiKaryawan
       var tbDetail: TextView = itemView.tvDetail
       var tbHapus: TextView = itemView.tvHapus
    }


}