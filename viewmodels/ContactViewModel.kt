package com.example.contacts.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.database.Contact
import com.example.contacts.repository.ContactRepository

class ContactViewModel(app:Context):ViewModel() {

private lateinit var repository: ContactRepository
    private lateinit var allContacts:LiveData<List<Contact>>

    init {
        repository= ContactRepository(app)
        allContacts=repository.getAllContacts()
    }

    fun getAllContacts():LiveData<List<Contact>>
    {
        return allContacts
    }
    fun insert(c:Contact)
    {
        repository.insert(c)
    }
}