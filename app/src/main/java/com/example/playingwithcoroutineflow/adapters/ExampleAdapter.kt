package com.example.playingwithcoroutineflow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playingwithcoroutineflow.R
import com.example.playingwithcoroutineflow.mvvm.models.Callback
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import kotlinx.android.synthetic.main.item_example.view.*

class ExampleAdapter  : PagedListAdapter<ExampleEntity, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<ExampleEntity>() {
        override fun areItemsTheSame(oldItem: ExampleEntity, newItem: ExampleEntity): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ExampleEntity, newItem: ExampleEntity): Boolean {
            return oldItem == newItem
        }
    }){

    private lateinit var callback: Callback
    fun setOnItemClickListener(callback: Callback){
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_example, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as Holder
        holder.bindViews(getItem(position))
    }

    fun getExampleItem(position: Int):ExampleEntity?{
        return getItem(position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindViews(item: ExampleEntity?) {
            item?.let {
                itemView.textView.text = it.name
                Glide.with(itemView.context).load(item.image).into(itemView.imageView)
            }
            itemView.card.setOnClickListener {
                callback.process(item)
            }

        }

    }

}