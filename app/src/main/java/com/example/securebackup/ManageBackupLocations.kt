package com.example.securebackup


import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class ManageBackupLocations() : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_backup_locations)
        val spinner: Spinner=findViewById<Spinner>(R.id.spinner)
        val button: Button = findViewById<Button>(R.id.button3)
        // Spinner Drop down elements
        val categories: MutableList<String> = ArrayList()
        categories.add("Google Drive")

        // Creating adapter for spinner
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        spinner.adapter = dataAdapter

        button.setOnClickListener {

            when(spinner.selectedItem.toString()){
                "Google Drive" -> addGdriveAccount()
            }
        }

    }

    fun addGdriveAccount(){
        val intent= Intent(this, AddGDriveActivity::class.java)
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}