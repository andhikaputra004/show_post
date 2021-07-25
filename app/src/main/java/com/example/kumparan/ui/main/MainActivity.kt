package com.example.kumparan.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kumparan.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}