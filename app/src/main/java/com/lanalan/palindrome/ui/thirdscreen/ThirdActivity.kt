package com.lanalan.palindrome.ui.thirdscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanalan.palindrome.R
import com.lanalan.palindrome.data.User
import com.lanalan.palindrome.databinding.ActivityThirdBinding
import com.lanalan.palindrome.ui.secondscreen.SecondActivity

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: ThirdActivityViewModel
    private var currentPage: Int = 1
    private var isLoading: Boolean = false
    private var selectedUserName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Third Screen"

        viewModel = ViewModelProvider(this).get(ThirdActivityViewModel::class.java)

        userAdapter = UserAdapter { user ->
            selectedUserName = user.name
            updateSelectedUserName()
        }
        binding.userRecyclerView.adapter = userAdapter

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ThirdActivity)
            addItemDecoration(DividerItemDecoration(this@ThirdActivity, LinearLayoutManager.VERTICAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadNextPage()
                    }
                }
            })
        }

        viewModel.users.observe(this) { users: List<User> ->
            userAdapter.submitList(users)
            userAdapter.notifyDataSetChanged()
        }

        loadData()
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

    private fun updateSelectedUserName() {
        val name = intent.getStringExtra("name")
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("selectedUserName", selectedUserName)
        startActivity(intent)
    }



    private fun loadData() {
        isLoading = true

        viewModel.fetchUsers(currentPage, 10)
    }

    private fun loadNextPage() {
        if (isLoading) {
            return
        }

        isLoading = true

        currentPage++

        viewModel.fetchUsers(currentPage, 10)
    }
}




