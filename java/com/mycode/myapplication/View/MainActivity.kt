package com.mycode.myapplication.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mycode.myapplication.Model.ToDoObjects
import com.mycode.myapplication.R
import com.mycode.myapplication.ViewModel.ToDoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var toDoViewModel : ToDoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val float : FloatingActionButton = findViewById(R.id.button)
        float.setOnClickListener {
            val intent = Intent (this, AddToDoObjectActivity::class.java)
            //to get input back
            startActivityForResult(intent, 1)
        }
        val recycler : RecyclerView = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager (this)
        recycler.setHasFixedSize(true)
        val adapter = RecyclerViewAdapter()
        recycler.adapter = adapter
        //android system will destroy this model when this activity finishes
        //ask android system for view model since it knows when to create new viewmodel instance or provide an existing one
        //get instance of viewmodel.class
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        toDoViewModel.getEverything().observe(this, object :Observer<MutableList<ToDoObjects>> {
            //triggered everytime data in table changes
            override fun onChanged(todo : MutableList<ToDoObjects>) {
                adapter.setToDoObjects(todo)
            }
        })
        val itemTouch = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                toDoViewModel.remove(adapter.getToDoObject(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(recycler)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1  && resultCode == RESULT_OK){
            val name = data!!.getStringExtra(AddToDoObjectActivity.edittitle)
            val description = data!!.getStringExtra(AddToDoObjectActivity.editdescrip)
            val time = data!!.getStringExtra(AddToDoObjectActivity.edittime)
            val todo : ToDoObjects =
                ToDoObjects(name, description, time)
            toDoViewModel.add(todo)
            Toast.makeText(applicationContext,"saved", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(applicationContext,"not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if(item.itemId == R.id.deleteAll){
                toDoViewModel.removeAll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
