package com.ansgar.mylib.database.dao;

import com.ansgar.mylib.database.entity.Book;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

public interface BookDao {

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    List<Book> getAllBooks();

    List<Book> getByGenre(String genre);

    List<Book> getBooksFromReadingList(boolean inList);

    Book getBookById(long id);

}
