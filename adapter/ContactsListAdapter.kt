package com.example.contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.compose.runtime.invalidate
import androidx.compose.runtime.resetSourceInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.database.Contact
import com.example.contacts.databinding.ContactItemBinding
import java.util.*

class ContactsListAdapter (c:Context,val listener: ContactListItemListener): ListAdapter<Contact,ContactsListAdapter.ContactsListViewHolder>(ContactListUpdatedCallBack()),Filterable {

    lateinit var orignal:MutableList<Contact>
    lateinit var inflater: LayoutInflater
    lateinit var contacts:List<Contact>
    lateinit var filteredContacts:List<Contact>
    init {
        inflater= LayoutInflater.from(c)
        contacts= emptyList()
        filteredContacts= mutableListOf<Contact>()
    }

    override fun onCurrentListChanged(previousList: MutableList<Contact>, currentList: MutableList<Contact>) {
        orignal=previousList
        super.onCurrentListChanged(previousList, currentList)
    }

    class ContactsListViewHolder(val items: ContactItemBinding): RecyclerView.ViewHolder(items.root) {

//         val contact_name:TextView=items.contactName
//         val contact_number:TextView=items.contactNumber
        fun bind(item: Contact, mlistener: ContactListItemListener)
        {
            items.apply {
                 contact=item
                listener=mlistener
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsListViewHolder {

        val view=ContactItemBinding.inflate(inflater,parent,false)
//        val v=inflater.inflate(R.layout.contact_item,parent,false)
        return ContactsListViewHolder(view)
        //        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ContactsListViewHolder, position: Int) {

            if(contacts!=null)
            {

                var contact_item=getItem(position)
              holder.bind(contact_item,listener)
            }


    }




    override fun getCurrentList(): MutableList<Contact> {
        return super.getCurrentList()
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getFilter(): Filter {

        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch=constraint.toString()
                var result= mutableListOf<Contact>()
                if(charSearch.isNotEmpty())
                {


                    result.addAll(currentList.filter { it->it.contact_name!!.toLowerCase(Locale.ROOT).contains(charSearch) })


//                    currentList.forEach{
//                        if(it.contact_name!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT)))
//                        {
//                            result.add(it)
//                        }
//                    }
//

                }
                else
                {
                            
                }


                filteredContacts=result
                val filteredResults=FilterResults()
                filteredResults.values=filteredContacts
                return filteredResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filteredContacts=results?.values as MutableList<Contact>
                submitList(filteredContacts)
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return super.convertResultToString(resultValue)
            }
        }
//        TODO("Not yet implemented")
    }


}


    class ContactListUpdatedCallBack:DiffUtil.ItemCallback<Contact>()
{
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {

        return oldItem.contact_number==newItem.contact_number
//
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem==newItem
    }


}
class ContactListItemListener(val clicklistener: (number:String)->Unit)
{

    fun onClick(c:Contact)=clicklistener(c.contact_number)


}
