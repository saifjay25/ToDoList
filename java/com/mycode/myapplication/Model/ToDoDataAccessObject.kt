package com.mycode.myapplication.Model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mycode.myapplication.Model.ToDoObjects


//DAO have to be interfaces because it doesn't provide method body only method and annotate it
//Room will generate all necessary code for the methods with annotations
@Dao
interface ToDoDataAccessObject {

    @Update
    fun update (toDoObject : ToDoObjects)

    @Insert
    fun add(toDoObject : ToDoObjects)

    @Delete
    fun remove(toDoObject : ToDoObjects)

    @Query("Delete FROM toDoTable")
    fun removeEverything()

    //instead of getting a cursor room turn result into toDo kotlin objects
    //live data makes list object observable so as soon there any changes in the table livedata value will be updated
    //and the activity will be notified
    @Query("SELECT * FROM toDoTable ORDER BY time DESC")
    fun getEverything() : LiveData<MutableList<ToDoObjects>>
}