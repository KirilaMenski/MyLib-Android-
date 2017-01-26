package com.ansgar.mylib.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.dialog.SelectDialog;
import com.ansgar.mylib.ui.listener.SelectAdapterListener;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 26.1.17.
 */
public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.SelectHolder> {

    private static final int LAYOUT = R.layout.item_value;

    private List<String> mValues;
    private WeakReference<SelectAdapterListener> mListener;
    private WeakReference<FragmentActivity> mFragmentActivity;
    private String mType;

    public SelectAdapter(List<String> values, FragmentActivity fragmentActivity, SelectAdapterListener listener, String type) {
        mValues = values;
        mFragmentActivity = new WeakReference<>(fragmentActivity);
        mListener = new WeakReference<>(listener);
        mType = type;
    }

    @Override
    public SelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mFragmentActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new SelectHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectHolder holder, final int position) {
        final String value = mValues.get(position);
        holder.bindHolder(value);
        holder.mValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mValues.size() - 1 && mType.equals(SelectDialog.GENRE_TYPE)) {
                    mListener.get().itemSelected(value, true);
                } else {
                    mListener.get().itemSelected(value, false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class SelectHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.value)
        TextView mValue;

        public SelectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(String value) {
            mValue.setText(value);
        }

    }
}
