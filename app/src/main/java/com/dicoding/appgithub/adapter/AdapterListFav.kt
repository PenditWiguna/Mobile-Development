package com.dicoding.appgithub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.dicoding.appgithub.R
import com.dicoding.appgithub.data.FavItem
import com.dicoding.appgithub.shared.Theme
import com.google.android.material.textview.MaterialTextView

class AdapterListFav(private val itemLists: List<FavItem>, private val context: Context) :
    RecyclerView.Adapter<AdapterListFav.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getSetItem = itemLists[position]

        var theme: Theme?
        theme = Theme(context)

        holder.username.text = getSetItem.Nama
        holder.image.load(getSetItem.Foto){
            transformations(CircleCropTransformation())
        }

        if(theme!!.choose.equals("White")){
            holder.username.setTextColor(ContextCompat.getColor(context, R.color.black))
        }else{
            holder.username.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
//        Glide.with(context).load(getSetItem.Foto).into(holder.image)
    }

    override fun getItemCount(): Int {
        return itemLists.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var username: TextView
        var image: ImageView

        init {
            username = view.findViewById(R.id.username)
            image = view.findViewById(R.id.image)
        }
    }
}