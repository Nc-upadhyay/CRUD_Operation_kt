package com.nc.crudopertion_kt

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nc.crudopertion_kt.Adaptor.MyAdaptor
import com.nc.crudopertion_kt.Model.Pojo
import com.nc.crudopertion_kt.database.DBHelper
import com.nc.crudopertion_kt.fragments.show_Frag

class Update : AppCompatActivity() {
    lateinit var buttom_nav: BottomNavigationView
    lateinit var name: EditText
    lateinit var pass: EditText
    lateinit var phone: EditText
    lateinit var note: EditText
    lateinit var submit: Button

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        name = findViewById(R.id.edit_f_name)
        pass = findViewById(R.id.edit_f_pass)
        phone = findViewById(R.id.edit_f_phone)
        note = findViewById(R.id.edit_f_note)
        submit = findViewById(R.id.submint_f_btn)
        var bundle = intent.extras;
        if (bundle != null) {
            name.setText(bundle!!.getString("name"))
            phone.setText(bundle.getString("phone"))
            pass.setText(bundle.getString("pass"))
            note.setText(bundle.getString("note"))
            Log.d("nc", "name is when not null" + bundle.getString("name"))
        }

        Log.d("nc", "name is " + bundle!!.getString("name"))


        buttom_nav = findViewById(R.id.naviagtion_bar);
        var transaction = supportFragmentManager.beginTransaction();


        submit.setOnClickListener {
            val dbHelper: DBHelper = DBHelper(this);
            val p: Pojo = Pojo();
            p.name = name.text.toString();
            p.pass = pass.text.toString();
            p.note = note.text.toString();
            p.phone = phone.text.toString().toLong()

            val success = dbHelper.upadateData(p) as Boolean;
            if (success)
                Toast.makeText(this, "Data is Updated", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Data is not Updated", Toast.LENGTH_LONG).show();


        }
        buttom_nav.setOnNavigationItemReselectedListener {
            when (it.itemId) {

                R.id.delete -> {
                    Toast.makeText(applicationContext, "there is nothing", Toast.LENGTH_LONG).show()
                }
                R.id.show -> {
                    val fragment = show_Frag()
                    transaction.replace(R.id.relative_layout, fragment)
                    transaction.commit();
                }
                R.id.home -> {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }
            }
        }
    }
}