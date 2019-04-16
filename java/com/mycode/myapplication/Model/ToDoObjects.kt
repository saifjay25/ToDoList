package com.mycode.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

//entity is Room annotation and at compile time it will create the code to make a SQLite table for the object
//Room needs a constructor to recreate the objects from the database
@Entity(tableName = "toDoTable")
class ToDoObjects//Room needs a constructor to recreate the objects from the database
    (private var name: String, private var description: String, private var time: String) {

    //each new row you add to the table, SQLite will automatically increment and add it to the id column
    @PrimaryKey(autoGenerate = true)
    private var id :Int = 0

    //Room cannot recreate id in constructor so it needs a setter method to set id
    fun setId (id:Int) {
        this.id=id
    }
    fun getId() : Int{
        return id
    }

    fun getName() : String {
        return name
    }

    fun getDescription() : String {
        return description
    }

    fun getTime() : String {
        return time
    }

}