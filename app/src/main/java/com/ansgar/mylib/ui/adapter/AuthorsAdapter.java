package com.ansgar.mylib.ui.adapter;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.listener.EntitySelectedListener;
import com.ansgar.mylib.ui.pager.AuthorBooksPager;
import com.ansgar.mylib.util.FragmentUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kirill on 24.1.17.
 */
public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.AuthorsHolder> implements Filterable {

    private static final int LAYOUT = R.layout.item_author;

    private List<Author> mAuthors;
    private List<Author> mAuthorsCopy;
    private WeakReference<FragmentActivity> mFragmentActivity;
    private boolean mCreateBook;
    private boolean mLandscape;
    private WeakReference<EntitySelectedListener> mListener;

    public AuthorsAdapter(List<Author> authors, FragmentActivity fragmentActivity, boolean isCreateBook, boolean landscape) {
        mAuthors = authors;
        mAuthorsCopy = authors;
        mFragmentActivity = new WeakReference<>(fragmentActivity);
        mCreateBook = isCreateBook;
        mLandscape = landscape;
    }

    @Override
    public AuthorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new AuthorsHolder(view);
    }

    @Override
    public void onBindViewHolder(AuthorsHolder holder, int position) {
        final Author author = mAuthors.get(position);
        holder.bindViews(author);
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                if (mCreateBook || mLandscape) {
                    mListener.get().authorSelected(author);
                } else {
                    FragmentUtil.replaceAnimFragment(mFragmentActivity.get(),
                            R.id.main_fragment_container, AuthorBooksPager.newInstance(author),
                            true, R.anim.right_out, R.anim.left_out);
                }
            }
        });
        holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentUtil.replaceAnimFragment(mFragmentActivity.get(),
                        R.id.main_fragment_container, AuthorBooksPager.newInstance(author),
                        true, R.anim.right_out, R.anim.left_out);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAuthors.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setListener(EntitySelectedListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Author> filteredResult;
                if (charSequence.length() == 0) {
                    filteredResult = mAuthorsCopy;
                } else {
                    filteredResult = getFilteredResults(charSequence.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResult;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mAuthors = (List<Author>) filterResults.values;
                AuthorsAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private List<Author> getFilteredResults(String constraint) {
        List<Author> authors = new ArrayList<>();
        for (Author author : mAuthorsCopy) {
            if (author.getFirstName().toLowerCase().contains(constraint)
                    || author.getLastName().toLowerCase().contains(constraint)
                    || (author.getFirstName().toLowerCase() + " " + author.getLastName()).contains(constraint)) {
                authors.add(author);
            }
        }
        return authors;
    }

    public class AuthorsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_img_left)
        CircleImageView mAuthorIconLeft;
        @BindView(R.id.author_name)
        TextView mAuthorName;
        @BindView(R.id.authro_books)
        TextView mBooksCount;
        @BindView(R.id.item_author_layout)
        LinearLayout mLayout;
        @BindView(R.id.ll)
        LinearLayout mLl;

        public AuthorsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(Author author) {
            setAuthorIcon(author.getBitmap());
            mAuthorName.setText(author.getFirstName() + " " + author.getLastName());
            mBooksCount.setText(mFragmentActivity.get()
                    .getResources().getString(R.string.books_count, author.getAuthorBooks().size()));
        }

        private void setAuthorIcon(Bitmap bitmap) {
            mAuthorIconLeft.setImageBitmap(bitmap);
        }

    }
}
