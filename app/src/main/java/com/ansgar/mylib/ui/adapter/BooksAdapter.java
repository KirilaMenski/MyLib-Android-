package com.ansgar.mylib.ui.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.fragments.BookDetailsFragment;
import com.ansgar.mylib.ui.listener.EntitySelectedListener;
import com.ansgar.mylib.ui.pager.BookCitationsPager;
import com.ansgar.mylib.util.FragmentUtil;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksHolder> implements Filterable{

    private static final int LAYOUT = R.layout.item_books;

    private List<Book> mBooks;
    private List<Book> mBooksCopy;
    private WeakReference<FragmentActivity> mFragmentActivity;
    private WeakReference<EntitySelectedListener> mListener;
    private boolean mFromReadList;

    public BooksAdapter(List<Book> books, FragmentActivity fragmentActivity, boolean isFromReadList) {
        mBooks = books;
        mBooksCopy = books;
        mFragmentActivity = new WeakReference<>(fragmentActivity);
        mFromReadList = isFromReadList;
    }

    @Override
    public BooksAdapter.BooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new BooksHolder(view);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.BooksHolder holder, int position) {
        final Book book = mBooks.get(position);
        holder.bindViews(book);
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFromReadList) {
                    mListener.get().bookSelected(book);
                } else {
                    FragmentUtil.replaceAnimFragment(mFragmentActivity.get(),
                            R.id.main_fragment_container, BookCitationsPager.newInstance(book),
                            true, R.anim.right_out, R.anim.left_out);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void setListener(EntitySelectedListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Book> filteredResult = null;
                if (charSequence.length() == 0) {
                    filteredResult = mBooksCopy;
                } else {
                    filteredResult = getFilteredResults(charSequence.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResult;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mBooks = (List<Book>) filterResults.values;
                BooksAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private List<Book> getFilteredResults(String constraint) {
        List<Book> books = new ArrayList<>();
        for (Book book : mBooks) {
            if (book.getTitle().toLowerCase().contains(constraint)) {
                books.add(book);
            }
        }
        return books;
    }


    public class BooksHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_cover)
        ImageView mBookCover;
        @BindView(R.id.item_book_layout)
        RelativeLayout mRelativeLayout;

        public BooksHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(Book book) {
            setBookCover(book.getBitmap());
        }

        private void setBookCover(Bitmap bitmap) {
            mBookCover.setImageBitmap(bitmap);
        }
    }
}
