package com.google.books_app.ui.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.books_app.database.BooksDatabaseHelper
import com.google.books_app.databinding.ActivityUpdateBookBinding
import com.google.books_app.database.models.Book

class UpdateBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBookBinding
    private lateinit var db: BooksDatabaseHelper
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)


        bookId = intent.getIntExtra("book_id", -1)
        if (bookId == -1) {
            finish()
            return
        }

        val book = db.getBookById(bookId)
        binding.updateTitle.setText(book.book_name)
        binding.updateBookAuthor.setText(book.author_Name)
        binding.updatePublishDate.setText(book.publish_Date)
        binding.updateBookISBN.setText(book.ISBN)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitle.text.toString()
            val newAuthor = binding.updateBookAuthor.text.toString()
            val newPublishDate = binding.updatePublishDate.text.toString()
            val newISBN = binding.updateBookISBN.text.toString()
            val updateBook = Book(bookId, newTitle, newAuthor, newPublishDate, newISBN)
            db.booksUpdate(updateBook)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}