package com.google.books_app.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.books_app.database.BooksDatabaseHelper
import com.google.books_app.databinding.ActivityAddBookBinding
import com.google.books_app.database.models.Book

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var db: BooksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.bookTitle.text.toString()
            val author = binding.bookAuthor.text.toString()
            val isbn = binding.bookISBN.text.toString()
            val date = binding.bookPublishDate.text.toString()
            val book = Book(0,title,author,date,isbn)
            db.insertBook(book)
            finish()
            Toast.makeText(this,"Book Saved", Toast.LENGTH_SHORT).show()
        }
    }
}