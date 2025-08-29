package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)

        button1.setOnClickListener {
            openSecondActivity(button1.text.toString())
        }

        button2.setOnClickListener {
            openSecondActivity(button2.text.toString())
        }

        button3.setOnClickListener {
            openSecondActivity(button3.text.toString())
        }

        button4.setOnClickListener {
            openSecondActivity(button4.text.toString())
        }

        button5.setOnClickListener {
            openSecondActivity(button5.text.toString())
        }
    }
    private fun openSecondActivity(buttonText: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("BUTTON_TEXT", buttonText)
        startActivity(intent)
    }

}

