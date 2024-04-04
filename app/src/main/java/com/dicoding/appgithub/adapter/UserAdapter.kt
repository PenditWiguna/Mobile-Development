package com.dicoding.appgithub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.appgithub.Database
import com.dicoding.appgithub.R
import com.dicoding.appgithub.data.ResponsUserGithub
import com.dicoding.appgithub.databinding.ItemUserBinding
import com.dicoding.appgithub.shared.Theme

class UserAdapter (private val data: MutableList<ResponsUserGithub.Item> = mutableListOf(),
private val listener: (ResponsUserGithub.Item) -> Unit):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ResponsUserGithub.Item>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }



    class UserViewHolder(private val v: ItemUserBinding ): RecyclerView.ViewHolder(v.root){
        fun bind (item: ResponsUserGithub.Item){

            var theme: Theme?
            theme = Theme(v.root.context)

            v.image.load(item.avatar_url){
                transformations(CircleCropTransformation())
            }
            v.username.text = item.login
            if(theme!!.choose.equals("White")){
                v.username.setTextColor(ContextCompat.getColor(v.root.context, R.color.black))
            }else{
                v.username.setTextColor(ContextCompat.getColor(v.root.context, R.color.white))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }
}