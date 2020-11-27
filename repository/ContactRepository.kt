package com.example.contacts.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import com.example.contacts.database.Contact
import com.example.contacts.database.ContactDatabase
import com.example.contacts.database.ContactsDao
import com.example.contacts.ui.Home

class ContactRepository(val c:Context ){

    lateinit var context: Context
    lateinit var database:ContactDatabase
    private lateinit var contactsDao: ContactsDao
    private lateinit var allContacts:LiveData<List<Contact>>

    init {
        context=c
        database= ContactDatabase.getInstance(c)
        contactsDao=database.contactsDao
        allContacts=contactsDao.getAllContacts()

    }
    fun getAllContacts():LiveData<List<Contact>>
    {
        return allContacts
    }


    fun insert(c:Contact)
    {
        InsertTask(contactsDao).execute(c)



    }

    companion object{



        class InsertTask(val dao:ContactsDao):AsyncTask<Contact,Void,Void>()
        {
            lateinit var insert_dao: ContactsDao
            init {
                insert_dao=dao
            }
            override fun doInBackground(vararg params: Contact): Void? {

                insert_dao.insert(params[0])
                return null
            }

        }



    }






}