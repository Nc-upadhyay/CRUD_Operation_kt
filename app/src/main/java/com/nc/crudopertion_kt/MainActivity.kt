package com.nc.crudopertion_kt

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.nc.crudopertion_kt.Model.Pojo
import com.nc.crudopertion_kt.database.DBHelper
import com.nc.crudopertion_kt.fragments.show_Frag

class MainActivity : AppCompatActivity() {

    lateinit var buttom_nav: BottomNavigationView
    lateinit var name: EditText
    lateinit var pass: EditText
    lateinit var phone: EditText
    lateinit var note: EditText
    lateinit var dbHelper: DBHelper
    lateinit var submit_btn: Button


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCenter.start(
            application, "{Your app secret here}",
            Analytics::class.java, Crashes::class.java
        )
        buttom_nav = findViewById(R.id.naviagtion_bar);
        name = findViewById(R.id.edittext_name)
        pass = findViewById(R.id.edittext_pass)
        phone = findViewById(R.id.edittext_phone)
        note = findViewById(R.id.edittext_note)
        dbHelper = DBHelper(this)
        submit_btn = findViewById(R.id.submint_btn)
        submit_btn.setOnClickListener {
           if( ! checkInputBox()) {
               Toast.makeText(this, "Submit button calls", Toast.LENGTH_LONG).show();
               var pojo: Pojo = Pojo()
               pojo.name = name.text.toString();
               pojo.pass = pass.text.toString();
               var n:String=phone.text.toString();
               var phon_number:Long=n.toLong()
               pojo.phone = phon_number
               pojo.note = note.text.toString();
               var success = dbHelper.inserData(pojo) as Boolean;
               if (success)
                   Toast.makeText(this, "Data inserted", Toast.LENGTH_LONG).show()
               else
                   Toast.makeText(this, "Data not  inserted", Toast.LENGTH_LONG).show()
           }
            else
           {

           }

        }
        buttom_nav.setOnNavigationItemReselectedListener {
            var transaction = supportFragmentManager.beginTransaction();

            when (it.itemId) {

                R.id.delete -> {
                    Toast.makeText(this, " There is nothing", Toast.LENGTH_LONG).show();
                }
                R.id.show -> {
                    submit_btn.visibility = View.GONE
                    val fragment = show_Frag()
                    transaction.replace(R.id.relative_layout, fragment)
                    transaction.commit();
                }
                R.id.update -> {
                    Toast.makeText(this, " There is nothing", Toast.LENGTH_LONG).show();

                }
            }
        }

    }

    private fun checkInputBox() : Boolean {

        val empyt=if(name.text.toString().isEmpty())
            "Plz Enter Name"
        else if (pass.text.toString().isEmpty())
            "Plz Enter Pass"
        else if(phone.text.toString().isEmpty())
            "plz Enter phone"
        else if(note.text.toString().isEmpty())
            "Plz Enter Some Note"
        else if(pass.text.toString().length<8)
            "Password length should be greater then 8"
        else if(pass.text.toString().length>15)
            "Password must be less then 15 Character"
        else if(phone.text.toString().length<10)
            "Invalid Phone Number"
        else if(phone.text.toString().length>10)
            "Invalid phon number"
        else
        {
            ""
        }
        if(!empyt.isEmpty())
        {
            Toast.makeText(this," $empyt",Toast.LENGTH_LONG).show()
            return true
        }else
        {
            return false;
        }

    }


}


