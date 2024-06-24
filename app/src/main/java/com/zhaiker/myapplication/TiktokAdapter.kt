package com.zhaiker.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemTiktokBinding

class TiktokAdapter : RecyclerView.Adapter<TiktokAdapter.MyViewHolder>(){

    var count = 10

    class MyViewHolder(private val binding : ListItemTiktokBinding) : RecyclerView.ViewHolder(binding.root){
        private val colorArray = arrayOf(Color.RED,Color.GREEN,Color.BLUE)
        fun bind(position: Int){
            binding.view.setBackgroundColor(colorArray[position%colorArray.size])
            binding.text.text = position.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemTiktokBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return count
    }

}