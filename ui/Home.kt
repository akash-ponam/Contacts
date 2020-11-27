package com.example.contacts.ui

import android.app.Service
import android.content.Context
import android.net.wifi.hotspot2.pps.HomeSp
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.viewinterop.viewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.Utilities.Utility
import com.example.contacts.adapter.ContactListItemListener
import com.example.contacts.adapter.ContactsListAdapter
import com.example.contacts.database.Contact
import com.example.contacts.database.ContactsDao
import com.example.contacts.databinding.FragmentHomeBinding
import com.example.contacts.viewmodels.ContactViewModel
import com.example.contacts.viewmodels.ContactViewModelFactory
import java.lang.StringBuilder
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Home : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//     lateinit var filteredContacts:MutableList<Contact>
    lateinit var viewmodel:ContactViewModel
    lateinit var adapter:ContactsListAdapter
    lateinit var sb:StringBuilder
    lateinit var im:InputMethodManager
    lateinit var data:List<Contact>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//       filteredContacts = mutableListOf<Contact>()
        val grid_layoutManager=GridLayoutManager(this.requireContext(),2)
        val linearLayoutManager=LinearLayoutManager(this.requireContext(),RecyclerView.VERTICAL,false)
        val binding=DataBindingUtil.inflate<FragmentHomeBinding>(inflater,R.layout.fragment_home,container,false)
         adapter=ContactsListAdapter(this.requireContext(), ContactListItemListener { nun->

            Toast.makeText(this.requireContext(),"${nun}", Toast.LENGTH_SHORT).show()

        })
//        binding.contactsRv.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.contactsRv.layoutManager=linearLayoutManager
        binding.contactsRv.adapter=adapter
        viewmodel=ViewModelProvider(this,ContactViewModelFactory(requireContext())).get(ContactViewModel::class.java)
        viewmodel.getAllContacts().value
//        val iterator=viewmodel.getAllContacts().value?.iterator()
        viewmodel.getAllContacts().observe(viewLifecycleOwner, Observer { changed_data->

            adapter.submitList(changed_data)

        })

//        data= viewmodel.getAllContacts().value!!
        binding.newContactButton.setOnClickListener{

            v:View -> v.findNavController().navigate(HomeDirections.goToNewContact())

        }
        setHasOptionsMenu(true)

//        sb= StringBuilder()


//        return inflater.inflate(R.layout.fragment_home, container, false)

//        binding



        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_options_menu,menu)
        val sv=menu.findItem(R.id.contact_search).actionView as androidx.appcompat.widget.SearchView
        sv.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                Utility.hideKeyboard(this@Home.requireActivity())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.length!=0)
                {
                    adapter.filter.filter(newText)
                }
                else
                {
                    adapter.submitList(viewmodel.getAllContacts().value)
                    Utility.hideKeyboard(this@Home.requireActivity())
                }



                return true
            }
        })
        sv.setOnCloseListener(
                object:OnCloseListener
                {
                    override fun onClose(): Boolean {

//                        adapter.submitList(viewmodel.getAllContacts().va
//                        searchview.isActivated=false
                        Utility.hideKeyboard(this@Home.requireActivity())


                        return false
                    }

                }
        )
        sv.setOnFocusChangeListener { v, hasFocus ->

            if(v.hasFocus() || sv.query.length>0)
            {
                sv.visibility=View.GONE
            }
            else
            {
                sv.visibility=View.VISIBLE
            }

        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {


        /**
         *
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }






}


