package com.ansgar.mylib.database.daoimpl;

import com.ansgar.mylib.MyLibApp;
import com.ansgar.mylib.database.DatabaseHelper;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.util.MyLibPreference;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by kirill on 24.1.17.
 */

public class BookDaoImpl implements BookDao {

    private static BookDaoImpl mInstance;
    private WeakReference<Context> mContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<Book, Integer> mDao;

    public static synchronized BookDaoImpl getInstance() {
        if (mInstance == null) {
            mInstance = new BookDaoImpl();
        }
        return mInstance;
    }

    private BookDaoImpl() {
        mContext = new WeakReference<>(MyLibApp.getAppContext());
        mDatabaseHelper = OpenHelperManager.getHelper(mContext.get(), DatabaseHelper.class);
        mDao = mDatabaseHelper.getBooksDao();
    }

    @Override
    public void addBook(Book book) {
        try {
            mDao.create(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            mDao.update(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(Book book) {
        try {
            mDao.delete(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getBookById(long id) {
        try {
            QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("id", id);
            List<Book> books = queryBuilder.query();
            return (books.size() > 0) ? books.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookByUuid(String uuid) {
        try {
            QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("uuid", uuid);
            List<Book> books = queryBuilder.query();
            return (books.size() > 0) ? books.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Observable<List<Book>> getUserBooks() {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("user_id", MyLibPreference.getUserId());
                    List<Book> books = queryBuilder.query();
                    subscriber.onNext(books);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(new Throwable(e.toString()));
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Observable<List<Book>> getUserBooksByGenre(final String genre) {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("user_id", MyLibPreference.getUserId()).and().eq("genre", genre);
                    List<Book> books = queryBuilder.query();
                    subscriber.onNext(books);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Observable<List<Book>> getUserBooksFromReadingList(final boolean inList) {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("user_id", MyLibPreference.getUserId()).and().eq("in_list", inList ? 1 : 0);
                    List<Book> books = queryBuilder.query();
                    subscriber.onNext(books);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Observable<List<Book>> getUserBooksByReadValue(final int read) {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("user_id", MyLibPreference.getUserId()).and().eq("was_read", read);
                    List<Book> books = queryBuilder.query();
                    subscriber.onNext(books);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Observable<List<Book>> getUserBooksByAuthorId(final long authorId) {
        return Observable.create(new Observable.OnSubscribe<List<Book>>() {
            @Override
            public void call(Subscriber<? super List<Book>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("user_id", MyLibPreference.getUserId()).and().eq("author_id", authorId);
                    List<Book> books = queryBuilder.query();
                    subscriber.onNext(books);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

}
