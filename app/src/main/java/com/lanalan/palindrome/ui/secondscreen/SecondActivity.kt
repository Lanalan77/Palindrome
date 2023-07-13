package com.lanalan.palindrome.ui.secondscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.lanalan.palindrome.R
import com.lanalan.palindrome.databinding.ActivitySecondBinding
import com.lanalan.palindrome.ui.thirdscreen.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Second Screen"

        val name = intent.getStringExtra("name")
        binding.nameLabel.text = "Name: $name"

        val selectedUserName = intent.getStringExtra("selectedUserName")
        if (selectedUserName != null) {
            binding.selectedUserLabel.text = "Selected User Name: $selectedUserName"
        }

        binding.chooseUserButton.setOnClickListener {
            goToThirdScreen()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToThirdScreen() {
        val name = intent.getStringExtra("name")
        val intent = Intent(this, ThirdActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }


}
