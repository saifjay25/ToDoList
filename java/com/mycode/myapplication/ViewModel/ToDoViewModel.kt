package com.mycode.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mycode.myapplication.Model.ToDoObjects

//androdiviewmodel gets passed application in constructor used for when application context in needed
//never store the context of activity or view that refers to activity viewmodel is supposed to go on if activity is destroyed
//androirviewmodel is needed so you can pass context application to repository since we need context to instantiate database instance
class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private var repos : Repository =
        Repository(application)
    private var allToDoObjects : LiveData<MutableList<ToDoObjects>> = repos.getEverything()

    //activities only have reference to viewmodel so you create repeated methods for database ops methods from repository
    fun add (toDo : ToDoObjects){
        repos.add(toDo)
    }

    fun update (toDo : ToDoObjects){
        repos.update(toDo)
    }

    fun remove (toDo : ToDoObjects){
        repos.remove(toDo)
    }

    fun removeAll (){
        repos.removeAll()
    }

    fun getEverything () : LiveData<MutableList<ToDoObjects>>{
        return allToDoObjects
    }


}