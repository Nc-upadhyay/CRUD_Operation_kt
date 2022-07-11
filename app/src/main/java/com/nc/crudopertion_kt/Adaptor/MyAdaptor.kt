package com.nc.crudopertion_kt.Adaptor

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.nc.crudopertion_kt.Model.Pojo
import com.nc.crudopertion_kt.R
import com.nc.crudopertion_kt.Update
import com.nc.crudopertion_kt.database.DBHelper

class MyAdaptor(pojo_list: List<Pojo>, internal var context: Context) :
    RecyclerView.Adapter<MyAdaptor.viewHolder>() {

    internal var pojo_list: List<Pojo> = ArrayList()

    init {
        this.pojo_list = pojo_list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.design, parent, false)
        return viewHolder(view)

    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val p = pojo_list[position];
        holder.name.text = p.name;
        holder.pass.text = p.pass;
        holder.phone.text = p.phone.toString();
        holder.note.text = p.note;

        holder.delete.setOnClickListener {
            var diaog = AlertDialog.Builder(context)
                .setTitle("Information")
                .setMessage("You want to be delete")
                .setPositiveButton("Yes", { diaog, i ->
                    deleteData(p.phone)
                    diaog.dismiss()

                })
                .setNegativeButton("No", { diaog, i ->

                    diaog.dismiss()
                })

            diaog.show();
        }



        holder.update.setOnClickListener {

            val bundle = Bundle();

            bundle.putString("name", p.name)
            bundle.putString("pass", p.pass)
            bundle.putString("phone", p.phone.toString());
            bundle.putString("note", p.note)
            val intent: Intent = Intent(context, Update::class.java)
            intent.putExtras(bundle)
            startActivity(context, intent,bundle);

        }


    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun deleteData(phone: Long) {

        var dbHelper: DBHelper = DBHelper(context)
        if (dbHelper.deleteData(phone)) {
            Toast.makeText(context, "Data deleted", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(context, "Data Not deleted", Toast.LENGTH_LONG).show()

        notifyDataSetChanged();
    }

    override fun getItemCount(): Int {
        return pojo_list.size
    }


    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.t_name);
        var pass: TextView = itemView.findViewById(R.id.t_pass);
        var phone: TextView = itemView.findViewById(R.id.t_phone);
        var note: TextView = itemView.findViewById(R.id.t_note);
        var delete: Button = itemView.findViewById(R.id.delete_btn);
        var update: Button = itemView.findViewById(R.id.update_btn);
    }

}