package com.mycode.myapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.mycode.myapplication.R
import kotlinx.android.synthetic.main.activity_main2.*

class AddToDoObjectActivity : AppCompatActivity() {
    companion object {
        var edittitle = "com.mycode.myapplication.name"
        var editdescrip = "com.mycode.myapplication.description"
        var edittime = "com.mycode.myapplication.time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    private fun save(){
        val name = name.text.toString()
        val description = description.text.toString()
        val time = input.text.toString()
        if(description.trim().isEmpty() || name.trim().isEmpty()){
            Toast.makeText(applicationContext, "type title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent()
        intent.putExtra(edittitle,name)
        intent.putExtra(editdescrip,description)
        intent.putExtra(edittime, time)
        //indicate if input was successful or not if back was pressed then it is not successful
        setResult(RESULT_OK, intent)
        //close activity
        finish()
    }

    //attaches save menu with this menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.add_object_menu, menu)
        return true
    }
    //handles click of save item
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if(item.itemId == R.id.save){
                save()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
