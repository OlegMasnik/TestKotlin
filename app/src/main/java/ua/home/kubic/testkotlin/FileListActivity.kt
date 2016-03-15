package ua.home.kubic.testkotlin

import android.R
import android.app.AlertDialog
import android.app.ListActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.list_activity.*
import java.io.File
import java.util.*


class FileListActivity : ListActivity() {

    var file: File? = null
    var fileList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ua.home.kubic.testkotlin.R.layout.list_activity)
        fileList = ArrayList<String>()
        file = File(Environment.getExternalStorageDirectory().toString() + "/files")

        val list = file?.listFiles()

        for (i in 0..(list?.size!! - 1)) {
            fileList?.add((list?.get(i)?.name!!))
        }

        listAdapter = ArrayAdapter<String>(this,
                R.layout.simple_list_item_1, fileList)

        listView.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val text = fileList?.get(i)
            val file_remove = File(Environment.getExternalStorageDirectory().toString() + "/files/" + text)
            Log.d("Some", file_remove.absolutePath)
            AlertDialog.Builder(this).setMessage(getString(ua.home.kubic.testkotlin.R.string.delete_file) + " " + text + "?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, q ->
                        file_remove.delete()
                        val s = Intent(applicationContext, FileListActivity::class.java)
                        startActivity(s)
                    }).setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this, "Скасовано", Toast.LENGTH_SHORT).show()
            }).show()
            true
        })

        fab.setOnClickListener(View.OnClickListener {
            val input = EditText(this)
            AlertDialog.Builder(this).setMessage(getString(ua.home.kubic.testkotlin.R.string.create_file))
                    .setView(input).setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                val i = Intent(applicationContext, EditActivity::class.java)
                val text = input.text
                i.putExtra("fileName", "$text.txt")
                startActivity(i)
            }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this, "Скасовано", Toast.LENGTH_SHORT).show()
            }).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(ua.home.kubic.testkotlin.R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val i = Intent(applicationContext, EditActivity::class.java)
        i.putExtra("fileName", fileList?.get(position))
        startActivity(i)
    }
}