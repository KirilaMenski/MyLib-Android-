package com.ansgar.mylib.database.dao;

import com.ansgar.mylib.database.entity.Author;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

public interface AuthorDao {

    void addAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Author author);

    List<Author> getAllAuthors();

    Author getById(long id);

}
