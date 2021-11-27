package com.example.kotlin_e_commerce.view.bottom_nav.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_e_commerce.R
import com.example.kotlin_e_commerce.adapter.RecyclerViewAdapterTransaction
import com.example.kotlin_e_commerce.model.TransactionList
import com.example.kotlin_e_commerce.view_model.TransactionFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : Fragment() {
    private lateinit var recyclerViewTransaction: RecyclerView
    private lateinit var rcylerTransactionAdapter: RecyclerViewAdapterTransaction
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        iniGetTransactionViewModel(view)
        getTransactionList(currentUser?.email!!)

        return view
    }

    private fun iniGetTransactionViewModel(view: View) {
        recyclerViewTransaction = view.findViewById<RecyclerView>(R.id.recyclerViewTransaction)
        recyclerViewTransaction.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        );

        rcylerTransactionAdapter = RecyclerViewAdapterTransaction()
        recyclerViewTransaction.adapter = rcylerTransactionAdapter
    }

    private fun getTransactionList(email_buyer: String) {
        val viewModel = ViewModelProvider(this).get(TransactionFragmentViewModel::class.java)
        //TODO: WATCH THIS CODE (WARNINGG)
        viewModel.getRecyclerTransactionListObserver()
            .observe(viewLifecycleOwner, Observer<TransactionList> {
                if (it != null) {
                    rcylerTransactionAdapter.transactionList = it.data.toMutableList()
                    rcylerTransactionAdapter.notifyDataSetChanged()
                } else {
                    constraintLayoutTransaction.visibility = View.VISIBLE
                }
        })
        viewModel.getTransactionList(email_buyer)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}