package com.example.securebackup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startOneshotBackupActivity(){
        val intent= Intent(this, OneshotBackup::class.java)
        startActivity(intent)
    }
}