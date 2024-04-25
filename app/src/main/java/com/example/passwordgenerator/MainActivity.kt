package com.example.passwordgenerator

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.util.toRange
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.passwordgenerator.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.lengthText.text = "LENGTH: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        binding.button.setOnClickListener {
            val newPassword = generateRandomPassword()
            binding.generatedPasswordText.text = newPassword
        }

    }


    private fun generateRandomPassword(): String {
        val length = binding.seekBar.progress
        val lowercaseChars = ('a'..'z')
        val uppercaseChars = ('A'..'Z')
        val numberChars = ('0'..'9')
        val symbolChars = "!@#$%^&*()_+{}[];:<>,.?/".toList()

        var password = ""
        if (binding.switch1.isChecked) {
            password += numberChars.random()
            password += numberChars.random()
        }

        if (binding.switch2.isChecked) {
            password += uppercaseChars.random()
            password += uppercaseChars.random()
        }

        if (binding.switch3.isChecked) {
            password += symbolChars.random()
            password += symbolChars.random()
        }

        val remainingLength = length - password.length
        for (i in 0 until remainingLength) {
            password += lowercaseChars.random()
        }

        val passwordShuffled = password.toMutableList().shuffled().joinToString(separator = "")

        return passwordShuffled
    }

}