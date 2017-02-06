package com.ansgar.mylib.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.ui.listener.CitationAdapterListener;
import com.daimajia.swipe.SwipeLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.1.17.
 */
public class CitationAdapter extends RecyclerView.Adapter<CitationAdapter.CitationHolder> {

    private static final int LAYOUT = R.layout.item_citation;

    private List<Citation> mCitations;
    private WeakReference<FragmentActivity> mFragmentActivity;
    private WeakReference<CitationAdapterListener> mListener;

    public CitationAdapter(List<Citation> citations, FragmentActivity fragmentActivity, CitationAdapterListener listener) {
        mCitations = citations;
        mFragmentActivity = new WeakReference<>(fragmentActivity);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public CitationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new CitationHolder(view);
    }

    @Override
    public void onBindViewHolder(final CitationHolder holder, int position) {
        final Citation citation = mCitations.get(position);
        holder.bindView(citation, position + 1);
        holder.mLikeCitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.get().likeCitation(citation);
            }
        });
        holder.mDeleteCitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.get().deleteCitation(citation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCitations.size();
    }

    public class CitationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.like)
        ImageView mLiked;
        @BindView(R.id.like_citation)
        ImageView mLikeCitation;
        @BindView(R.id.delete_citation)
        ImageView mDeleteCitation;
        @BindView(R.id.citation)
        TextView mCitation;
        @BindView(R.id.citation_date)
        TextView mCitationDate;
        @BindView(R.id.citation_item)
        SwipeLayout mSwipeLayout;

        public CitationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Citation citation, int position) {
            mCitation.setText(citation.getCitation());
            mCitationDate.setText(citation.getDate());
            mLiked.setVisibility(citation.getLiked() == 1 ? View.VISIBLE : View.GONE);
            mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        }

    }
}
