package com.example.login.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Menus
import com.example.login.R

class PlaceYourOrderAdapter(val menuList: List<Menus?>?): RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceYourOrderAdapter.MyViewHolder {
       val view: View = LayoutInflater.from(parent.context).inflate(R.layout.placeyourorder_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceYourOrderAdapter.MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if(menuList == null) 0  else menuList.size
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val menuName: TextView = view.findViewById(R.id.menuName)
        val menuPrice: TextView = view.findViewById(R.id.menuPrice)
        val menuQty: TextView = view.findViewById(R.id.menuQty)

        fun bind(menu: Menus) {
            menuName.text = menu?.name!!
            menuPrice.text = "Harga Rp" + String.format("%.2f",menu?.harga * menu.totalInCart)
            menuQty.text = "Jumlah :" + menu?.totalInCart

            Glide.with(thumbImage)
                .load(menu?.url)
                .into(thumbImage)
        }
    }
}