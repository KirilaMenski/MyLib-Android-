package com.ansgar.mylib.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.listener.SortTypeAdapterListener;
import com.ansgar.mylib.util.MyLibPreference;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 1.2.17.
 */
public class SortTypeAdapter extends RecyclerView.Adapter<SortTypeAdapter.SortTypeHolder> {

    private static final int LAYOUT = R.layout.item_sort_type;

    private List<String> mTypes;
    private WeakReference<SortTypeAdapterListener> mListener;
    private WeakReference<FragmentActivity> mFragmentActivity;

    public SortTypeAdapter(String type, SortTypeAdapterListener listener, FragmentActivity fragmentActivity) {
        mListener = new WeakReference<>(listener);
        mFragmentActivity = new WeakReference<>(fragmentActivity);
        if(type.equals(MyLibPreference.SORT_TYPE_AUTHOR)){
           mTypes = Arrays.asList(mFragmentActivity.get().getResources().getStringArray(R.array.author_sort_types));
        }
        if(type.equals(MyLibPreference.SORT_TYPE_BOOK)){
            mTypes = Arrays.asList(mFragmentActivity.get().getResources().getStringArray(R.array.book_sort_types));
        }
    }

    @Override
    public SortTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new SortTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(SortTypeHolder holder, final int position) {
        final String type = mTypes.get(position);
        holder.bindHolder(type);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.get().selectedSortType(type, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }

    public class SortTypeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sort_type_ll)
        LinearLayout mLinearLayout;
        @BindView(R.id.sort_type)
        TextView mSortType;
        @BindView(R.id.selected_sort_type)
        ImageView mSelectedSortType;

        public SortTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(String type) {
            mSortType.setText(type);
        }

    }
}
