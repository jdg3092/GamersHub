package com.example.gamershub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamershub.R
import com.example.gamershub.model.MainOption

class MainOptionAdapter(
    var context: Context,
    private val options: List<MainOption>,
    private val onItemClick: (MainOption) -> Unit
) : RecyclerView.Adapter<MainOptionAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.imgOption)
        val text = itemView.findViewById<TextView>(R.id.textOption)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val option = options[position]
        holder.text.text = option.title
        holder.image.setImageResource(option.iconResId)
        holder.itemView.setOnClickListener { onItemClick(option) }

    }
}