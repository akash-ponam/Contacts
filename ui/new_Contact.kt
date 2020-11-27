package com.example.contacts.ui

import android.app.Service
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.contacts.R
import com.example.contacts.Utilities.Utility
import com.example.contacts.database.Contact
import com.example.contacts.databinding.FragmentNewContactBinding
import com.example.contacts.viewmodels.ContactViewModel
import com.example.contacts.viewmodels.ContactViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [new_Contact.newInstance] factory method to
 * create an instance of this fragment.
 */
class new_Contact : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewModel: ContactViewModel

    lateinit var binding:FragmentNewContactBinding
    lateinit var inputmanager:InputMethodManager

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
         binding=DataBindingUtil.inflate<FragmentNewContactBinding>(inflater,R.layout.fragment_new__contact,container,false)
         inputmanager= activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        viewModel=ViewModelProvider(this,ContactViewModelFactory(requireContext())).get(ContactViewModel::class.java)
//        setHasOptionsMenu(true)


        binding.saveButton.setOnClickListener {

            val name=binding.nameField.editText!!.text.toString()
            val number=binding.numberField.editText!!.text.toString()
            if( name.isNotEmpty() && number.isNotEmpty())
            {
                viewModel.insert(Contact(number,name))
                Toast.makeText(requireContext(),"data saved",Toast.LENGTH_SHORT).show()
                Utility.hideKeyboard(this.requireActivity())
                findNavController(this).navigate(new_ContactDirections.goToHome())

            }
            else
            {
                binding.numberField.error="please enter number correctly"
                binding.nameField.error="plase enter name correctly"
            }

        }

        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_new__contact, container, false)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment new_Contact.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            new_Contact().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}