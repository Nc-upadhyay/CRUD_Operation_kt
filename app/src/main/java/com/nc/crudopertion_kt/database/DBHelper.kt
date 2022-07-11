package com.nc.crudopertion_kt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import com.nc.crudopertion_kt.Model.Pojo

@RequiresApi(Build.VERSION_CODES.P)
class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_name, null, DB_version) {

    companion object {
        private var DB_name = "CRUD"
        private var DB_version = 1
        private var table_name = "operation"
        private var NAME = "name"
        private var PASSWORD = "password"
        private var PHONE = "phone"
        private var NOTE = "note"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $table_name ($PHONE LONG primary key, $NAME TEXT ,$PASSWORD TEXT,$NOTE TEXT)";
        p0?.execSQL(query);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val drop_table = "DROP TABLE IF EXISTS $table_name";
        p0?.execSQL(drop_table);
        onCreate(p0);
    }

    @SuppressLint("Range")
    fun getAllDATA(): List<Pojo> {

        val list = ArrayList<Pojo>();
        val db: SQLiteDatabase = writableDatabase
        val selectQuery = "select * from $table_name";
        val cursor: Cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val p = Pojo()
                    p.name = (cursor.getString(cursor.getColumnIndex(NAME)))
                    p.note = (cursor.getString(cursor.getColumnIndex(NOTE)))
                    p.pass = (cursor.getString(cursor.getColumnIndex(PASSWORD)))
                    p.phone = cursor.getString(cursor.getColumnIndex(PHONE)).toLong()
                    list.add(p);

                } while (cursor.moveToNext())
            }
        }
        cursor.close();
        return list;
    }

    fun inserData(p: Pojo): Boolean {
        val db: SQLiteDatabase = this.writableDatabase;
        val values = contentValuesOf();
        values.put(NAME, p.name)
        values.put(PASSWORD, p.pass)
        values.put(PHONE, p.phone)
        values.put(NOTE, p.note)


        val query = db.insert(table_name, null, values)
        db.close();
        return (!(("$query".toLong()).equals(-1)))

    }

    @SuppressLint("Range")
    fun getData(phone: Long): Pojo {
        val p = Pojo();
        val db: SQLiteDatabase = writableDatabase;
        val select_query = "select * from $table_name where $PHONE=$phone"
        val cursor: Cursor = db.rawQuery(select_query, null)
        cursor.moveToFirst()
        p.name = (cursor.getString(cursor.getColumnIndex(NAME)))
        p.note = (cursor.getString(cursor.getColumnIndex(NOTE)))
        p.pass = (cursor.getString(cursor.getColumnIndex(PASSWORD)))
        p.phone =cursor.getString(cursor.getColumnIndex(PHONE)).toLong()
        cursor.close()
        return p;
    }

    fun deleteData(phone : Long )  :Boolean{

        val db:SQLiteDatabase = this.writableDatabase;
        val sucess_query=db.delete(table_name, PHONE +"=?", arrayOf(phone.toString())).toLong()
        return (Integer.parseInt("$sucess_query")!= -1)

    }
    fun upadateData(p : Pojo)  :Boolean{
        val db:SQLiteDatabase =this.writableDatabase
        val values= ContentValues()
        values.put(NAME,p.name)
        values.put(PHONE,p.phone)
        values.put(PASSWORD,p.pass)
        values.put(NOTE,p.note)
        val sucess=db.update(table_name,values, PHONE+" =?", arrayOf(p.phone.toString())).toLong()
        db.close()
        return (Integer.parseInt("$sucess")!= -1)
    }
}