package com.ansgar.mylib.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.fragments.AddBookFragment;
import com.ansgar.mylib.ui.fragments.BookDetailsFragment;
import com.ansgar.mylib.ui.pager.BookCitationsPager;
import com.ansgar.mylib.util.FragmentUtil;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public class ReadingListAdapter extends RecyclerView.Adapter<ReadingListAdapter.ReadingListHolder> {

    private static final int LAYOUT = R.layout.item_reading_list;

    private List<Book> mBooks;
    private List<Book> mBooksCopy;
    private WeakReference<FragmentActivity> mFragmentActivity;

    public ReadingListAdapter(List<Book> books, FragmentActivity fragmentActivity) {
        mBooks = books;
        mBooksCopy = books;
        mFragmentActivity = new WeakReference<>(fragmentActivity);
    }

    @Override
    public ReadingListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new ReadingListHolder(view);
    }

    @Override
    public void onBindViewHolder(ReadingListHolder holder, int position) {
        final Book book = mBooks.get(position);
        holder.bindViews(position + 1, book);
        holder.mInfLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.replaceAnimFragment(mFragmentActivity.get(), R.id.main_fragment_container,
                        BookCitationsPager.newInstance(book), true, R.anim.right_out, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ReadingListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.status_icon)
        ImageView mStatus;
        @BindView(R.id.book_title)
        TextView mBookTitle;
        @BindView(R.id.inf)
        TextView mBookInf;
        @BindView(R.id.book_ll)
        LinearLayout mInfLl;

        public ReadingListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(int position, Book book) {
            mBookTitle.setText(position + ". " + book.getTitle());
            mBookInf.setText(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
            mStatus.setImageDrawable(ContextCompat.getDrawable(mFragmentActivity.get(),
                    book.getWasRead() == 1 ? R.drawable.ic_status_true : R.drawable.ic_status_false));
        }

    }
}
