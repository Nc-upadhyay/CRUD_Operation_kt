package com.nc.crudopertion_kt.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nc.crudopertion_kt.Adaptor.MyAdaptor
import com.nc.crudopertion_kt.Model.Pojo
import com.nc.crudopertion_kt.R
import com.nc.crudopertion_kt.database.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class show_Frag : Fragment() {
    private lateinit var recyclerView: RecyclerView
    lateinit var adaptor: MyAdaptor
    lateinit var dbhander: DBHelper
    var p_list: List<Pojo> = ArrayList<Pojo>()
    lateinit var linearlayout: LinearLayoutManager;



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_show_, container, false)

        recyclerView = view.findViewById(R.id.recyclerview);
        dbhander = DBHelper(this.requireContext())
        fetchlist();
        return view;
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun fetchlist() {
        p_list = dbhander.getAllDATA();
        adaptor = MyAdaptor(p_list, requireContext())
        linearlayout = LinearLayoutManager(context);
        recyclerView.layoutManager=linearlayout;
        recyclerView.adapter = adaptor;
        adaptor?.notifyDataSetChanged()
    }


}