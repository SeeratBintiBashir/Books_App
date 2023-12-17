package com.google.books_app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.books_app.database.models.Book

class BooksDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "library.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "books"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ISBN = "isbn"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_AUTHOR = "author"
        private const val COLUMN_DATE = "publishdate"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val tableCreateQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_ISBN INTEGER, $COLUMN_TITLE TEXT, $COLUMN_AUTHOR TEXT, $COLUMN_DATE DATE)"
        db?.execSQL(tableCreateQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertBook(book: Book){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ISBN, book.ISBN)
            put(COLUMN_TITLE, book.book_name)
            put(COLUMN_AUTHOR, book.author_Name)
            put(COLUMN_DATE, book.publish_Date)

        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllBooks(): List<Book> {
        val notesList = mutableListOf<Book>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val isbn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))

            val book = Book(id,title,author,date,isbn)
            notesList.add(book)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun booksUpdate(book: Book){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.book_name)
            put(COLUMN_AUTHOR, book.author_Name)
            put(COLUMN_DATE, book.publish_Date)
            put(COLUMN_ISBN, book.ISBN)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(book.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }


    fun getBookById(bookId: Int): Book {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $bookId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
        val isbn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))

        cursor.close()
        db.close()
        return Book(id, title, author, date, isbn)
    }

    fun deleteBook(bookId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(bookId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }


}