package com.ansgar.mylib.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.listener.RatingAdapterListener;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 2.2.17.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder> {

    private static final int LAYOUT = R.layout.item_rating;
    private static final int COUNT = 5;

    private WeakReference<FragmentActivity> mFragmentActivity;
    private WeakReference<RatingAdapterListener> mListener;
    private int mRating;

    public RatingAdapter(int rating, FragmentActivity fragmentActivity, RatingAdapterListener listener) {
        mFragmentActivity = new WeakReference<>(fragmentActivity);
        mListener = new WeakReference<>(listener);
        mRating = rating;
    }

    @Override
    public RatingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new RatingHolder(view);
    }

    @Override
    public void onBindViewHolder(RatingHolder holder, final int position) {
//        for (int i = 0; i < COUNT; i++) {
            holder.mRating.setImageDrawable(ContextCompat.getDrawable(mFragmentActivity.get(),
                    (position + 1) <= mRating ? R.drawable.ic_rating : R.drawable.ic_rating_empty));
//        }
        holder.mRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.get().rating(position + 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

    public class RatingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rating)
        ImageView mRating;

        public RatingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
