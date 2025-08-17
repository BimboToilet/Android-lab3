package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val recyclerview = findViewById<RecyclerView>(R.id.satan)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val repo = NewsRepository()
        val data = ArrayList<Article>()
        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter
        val editText = findViewById<EditText>(R.id.searchfield)
        findViewById<Button>(R.id.searchbutton).setOnClickListener{
            if (editText.text.isNotEmpty()){
                runBlocking {
                    launch {
                        data.clear()
                        data.addAll(repo.getArticles(editText.text.toString()) as ArrayList<Article>)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

}

