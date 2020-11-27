package com.example.contacts.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactViewModelFactory(val app:Context):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ContactViewModel::class.java))
        {
            return ContactViewModel(app) as T
        }


        TODO("Not yet implemented")
    }


}