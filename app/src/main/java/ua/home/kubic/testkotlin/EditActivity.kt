package ua.home.kubic.testkotlin

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class EditActivity : AppCompatActivity() {

    val path = Environment.getExternalStorageDirectory().toString()
    val directory = "/files/".toString()
    var fileName = ""
    var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileName = intent.getStringExtra("fileName")
        file = File(path + directory + fileName)
        if (!file?.exists()!!) file?.createNewFile()
    }

    override fun onResume() {
        super.onResume()
        editText.setText(readFromFile(File(path + directory + fileName)))
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val fSize = preference.getString(getString(R.string.textSize), "20").toFloat()
        editText.setTextSize(fSize)
        val regural = preference.getString(getString(R.string.textStyle), "")
        var typeFace = Typeface.NORMAL
        if (regural.contains(getString(R.string.text_both))) typeFace += Typeface.BOLD
        if (regural.contains(getString(R.string.text_italic))) typeFace += Typeface.ITALIC
        editText.setTypeface(null, typeFace)
        var color = Color.BLACK
        if (preference.getBoolean(getString(R.string.color_red), false)) color += Color.RED
        if (preference.getBoolean(getString(R.string.color_green), false)) color += Color.GREEN
        if (preference.getBoolean(getString(R.string.color_blue), false)) color += Color.BLUE
        editText.setTextColor(color)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                editText.setText("")
                Toast.makeText(this, getString(R.string.text_clean), Toast.LENGTH_SHORT).show()
            }
            R.id.action_save -> {
                writeToFile(fileName, editText.text.toString())
                Toast.makeText(this, getString(R.string.text_saved), Toast.LENGTH_SHORT).show()
            }
            R.id.action_setting -> startActivity(Intent(this, SettingActivity::class.java));
        }
        return super.onOptionsItemSelected(item)
    }

    private fun readFromFile(file: File): String {
        var text = StringBuilder();
        file.readLines().forEach { x -> text.append("$x\n") }
        return text.toString()
    }

    private fun writeToFile(fileName: String, text: String) {
        val root = File("$path$directory")
        if (!root.exists()) {
            root.mkdirs()
        };
        File(root, fileName).writeText(text)
    }
    override fun onBackPressed() {
        writeToFile(fileName, editText.text.toString())
        startActivity(Intent(this, FileListActivity::class.java))
    }

}