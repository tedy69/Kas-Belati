package tech.riseofdevelopers.catatuang.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.riseofdevelopers.catatuang.model.Transaksi
import tech.riseofdevelopers.catatuang.utils.Helper
import tech.riseofdevelopers.kas.R

class TransaksiRecyclerAdapter(val data : List<Transaksi?>?, val onClickTransaksi: OnClickTransaksi?) : RecyclerView.Adapter<TransaksiRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icoStatus : ImageView = itemView.findViewById(R.id.iv_status_transaksi)
        val tvTransaksiTitle : TextView = itemView.findViewById(R.id.tv_name_transaksi)
        val tvTransaksiDate : TextView = itemView.findViewById(R.id.tv_date_transaksi)
        val tvTransaksiNominal : TextView = itemView.findViewById(R.id.tv_saldo_transaksi)

        fun onBind(item : Transaksi?){
            tvTransaksiTitle.text = item?.title
            tvTransaksiDate.text = item?.date
            tvTransaksiNominal.text = Helper.convertToRupiah(item?.nominal ?: 0)
            checkStatusTransaksi(item?.status)
        }

        // cek status transaksi, untuk mengubah icon dan warna text nya
        fun checkStatusTransaksi(status : String?){
            if (status?.toLowerCase() == "pemasukan"){
                icoStatus.setImageResource(R.drawable.ic_pemasukan)
                tvTransaksiNominal.setTextColor(getColorResource(itemView.context,R.color.hijau))
                tvTransaksiDate.setTextColor(getColorResource(itemView.context,R.color.hijau))
                tvTransaksiTitle.setTextColor(getColorResource(itemView.context,R.color.hijau))
            }else{
                icoStatus.setImageResource(R.drawable.ic_pengeluaran)
                tvTransaksiNominal.setTextColor(Color.RED)
                tvTransaksiDate.setTextColor(Color.RED)
                tvTransaksiTitle.setTextColor(Color.RED)
            }
        }

        // fungsi untuk mengambil warna dari resource
        fun getColorResource(context : Context,color : Int ): Int {
            return context.resources.getColor(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_transaksi,parent,false)
       return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTransaksi = data?.get(position)
        holder.onBind(itemTransaksi)

        holder.itemView.setOnClickListener {
            onClickTransaksi?.onClick(itemTransaksi)
        }
    }
}

// callback
interface OnClickTransaksi{
    fun onClick(item : Transaksi?)
}