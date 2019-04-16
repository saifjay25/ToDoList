package com.mycode.myapplication.Model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//versions are incremented by by the number of database changes
@Database(entities = [ToDoObjects::class], version = 1)
abstract class ToDoObjectsDatabase : RoomDatabase() {

    //data access object class used to access database operations in repository class
    //room generates necessary code since instance was created
    abstract fun ToDoDao(): ToDoDataAccessObject

    companion object {
        //variable is created so the class can turned into a singleton (can't create multiple instances of database)
        private var instance : ToDoObjectsDatabase? =null

        //synchronized is for singleton pattern only one thread at a time can access this
        @Synchronized fun  getInstance(context: Context) : ToDoObjectsDatabase {
            if(instance ==null){
                //fallback will delete table and recreate it if the schema is changed
                //can't call new database since it is abstract to you use a builder
                instance = Room.databaseBuilder(context.applicationContext,
                    ToDoObjectsDatabase::class.java,"database").fallbackToDestructiveMigration().build()
            }
            return instance as ToDoObjectsDatabase
        }
    }


}