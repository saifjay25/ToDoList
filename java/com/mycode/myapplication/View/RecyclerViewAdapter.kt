package com.mycode.myapplication.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.myapplication.Model.ToDoObjects
import com.mycode.myapplication.R


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.holder>() {
    private var itemList : MutableList<ToDoObjects> = mutableListOf()

    //create and return a holder since this is the layout to use for the single items in recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        //parent in recycler get context is calling main activity for recyclerview
        val item : View = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return holder(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    //take care of getting single todo object into view of holder
    override fun onBindViewHolder(hold: holder, position: Int) {
        val current : ToDoObjects = itemList[position]
        hold.name.text = current.getName()
        hold.description.text = current.getDescription()
        hold.time.text = current.getTime()
    }
    //need a way to get to do objects from on change function of livedata in recyclerview
    fun setToDoObjects (todos : MutableList<ToDoObjects>){
        this.itemList = todos
        //adapter has to redraw layout
        notifyDataSetChanged()
    }

    fun getToDoObject (position : Int) : ToDoObjects {
        return itemList[position]
    }
    //this will hold views in single recyclerview items
    class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView
        var description : TextView
        var time : TextView
        init {
            name = itemView.findViewById(R.id.name)
            description = itemView.findViewById(R.id.description)
            time = itemView.findViewById(R.id.time)
        }
    }
}