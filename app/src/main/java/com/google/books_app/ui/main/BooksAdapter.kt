package com.google.books_app.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.books_app.R
import com.google.books_app.database.BooksDatabaseHelper
import com.google.books_app.database.models.Book
import com.google.books_app.ui.update.UpdateBookActivity

class BooksAdapter(private var books: List<Book>, val context: Context):
    RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    private val db: BooksDatabaseHelper = BooksDatabaseHelper(context)

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val date: TextView = itemView.findViewById(R.id.dateTextView)
        val isbn: TextView = itemView.findViewById(R.id.isbnTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = book.book_name
        holder.authorTextView.text = book.author_Name
        holder.date.text = book.publish_Date
        holder.isbn.text = book.ISBN

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateBookActivity::class.java).apply {
                putExtra("book_id", book.id)
            }
          context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteBook(book.id)
            refreshData(db.getAllBooks())
            Toast.makeText(holder.itemView.context,"Book Deleted", Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
    }
