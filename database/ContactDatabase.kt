package com.example.contacts.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.play.core.splitinstall.c

@Database(entities = [Contact::class],version=3,exportSchema = false)
abstract class ContactDatabase() : RoomDatabase(){

        abstract val contactsDao:ContactsDao

        companion object
        {

                @Volatile
                 var INSTANCE:ContactDatabase?=null
            fun getInstance(c:Context): ContactDatabase {
                kotlin.synchronized(this)
                {
                    var instance= INSTANCE
                    if(instance==null)
                    {

                        instance=Room.databaseBuilder(c.applicationContext,ContactDatabase::class.java,"database_contacts")
                            .fallbackToDestructiveMigration()
                            .build()
                    }

                    INSTANCE=instance

                    return instance
                }


            }




        }


}