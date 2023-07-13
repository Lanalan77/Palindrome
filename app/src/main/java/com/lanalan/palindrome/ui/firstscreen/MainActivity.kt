package com.lanalan.palindrome.ui.firstscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import com.lanalan.palindrome.databinding.ActivityMainBinding
import com.lanalan.palindrome.ui.secondscreen.SecondActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkButton.setOnClickListener { checkPalindrome() }
        binding.nextButton.setOnClickListener { goToSecondScreen() }
    }

    private fun checkPalindrome() {
        val sentence = binding.sentenceInput.text.toString().replace(" ", "")
        val isPalindrome = sentence.equals(sentence.reversed(), ignoreCase = true)

        val message = if (isPalindrome) "Palindrome" else "Not Palindrome"
        showMessageDialog(message)
    }

    private fun goToSecondScreen() {
        val name = binding.nameInput.text.toString()
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    private fun showMessageDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog: DialogInterface?, which: Int -> dialog?.dismiss() }
            .show()
    }
}


