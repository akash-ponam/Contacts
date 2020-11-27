package com.example.contacts.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactsDao {

    @Insert
    fun insert(c:Contact)
    @Delete
    fun delete(c:Contact)
    @Update
    fun update(c:Contact)
    @Query("SELECT * FROM table_contacts ORDER BY contact_name ASC")
    fun getAllContacts():LiveData<List<Contact>>

}