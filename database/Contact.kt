package com.example.contacts.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_contacts")
 data class Contact(
    @PrimaryKey
    @ColumnInfo(name = "contact_number")
    var contact_number:String="",
    @ColumnInfo(name = "contact_name")
    var contact_name: String? =""

)
