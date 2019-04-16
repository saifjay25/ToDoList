package com.mycode.myapplication.ViewModel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.mycode.myapplication.Model.ToDoDataAccessObject
import com.mycode.myapplication.Model.ToDoObjects
import com.mycode.myapplication.Model.ToDoObjectsDatabase

//application is a subclass of context so we can use application as the context to create the database instance
class Repository(app: Application) {
    private lateinit var toDoDao : ToDoDataAccessObject
    private lateinit var everything : LiveData<MutableList<ToDoObjects>>

    init {
        val database : ToDoObjectsDatabase = ToDoObjectsDatabase.getInstance(app)
        toDoDao =database.ToDoDao()
        everything = toDoDao.getEverything()
    }

    //have to execute the code on background thread manually because room does not allow database ops in main thread since it can freeze the app
    //these methods are the api calls that the repository exposes to viewmodel
    fun add (todo : ToDoObjects){
        addToDoAsyncTask(toDoDao).execute(todo)
    }

    fun remove (todo : ToDoObjects){
        removeToDoAsyncTask(toDoDao).execute(todo)
    }

    fun update (todo : ToDoObjects){
        updateToDoAsyncTask(toDoDao).execute(todo)
    }

    fun removeAll (){
        removeAllToDoAsyncTask(toDoDao).execute()
    }

    //retrieved from the data access object
    //Room execute the database operation that returns livedata on background thread
    fun getEverything() : LiveData<MutableList<ToDoObjects>>{
        return everything
    }
    //asynctask has to be used in order to perform operations in the background thread and its static because it does
    //not have a reference to the repository since it can cause memory leak
    companion object {

        //pass the object, do not need progress and return nothing
        //since class is static you cannot access the DAO of the repository
        private class addToDoAsyncTask(private var toDo: ToDoDataAccessObject) : AsyncTask<ToDoObjects, Void, Void>() {

            override fun doInBackground(vararg params: ToDoObjects?): Void? {
                //only get one todo object
                toDo.add(params[0]!!)
                return null
            }

        }

        private class updateToDoAsyncTask(private var toDo: ToDoDataAccessObject) : AsyncTask<ToDoObjects, Void, Void>() {

            override fun doInBackground(vararg params: ToDoObjects?): Void? {
                //only get one todo object
                toDo.update(params[0]!!)
                return null
            }

        }

        private class removeToDoAsyncTask(private var toDo: ToDoDataAccessObject) : AsyncTask<ToDoObjects, Void, Void>() {

            override fun doInBackground(vararg params: ToDoObjects?): Void? {
                //only get one todo object
                toDo.remove(params[0]!!)
                return null
            }

        }

        private class removeAllToDoAsyncTask(private var toDo: ToDoDataAccessObject) : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids:Void): Void? {
                //only get one todo object
                toDo.removeEverything()
                return null
            }

        }
    }

}