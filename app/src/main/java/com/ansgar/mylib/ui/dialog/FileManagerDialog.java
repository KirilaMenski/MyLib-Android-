package com.ansgar.mylib.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ansgar.mylib.ui.listener.FileManagerDialogListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kirill on 31.1.17.
 */

public class FileManagerDialog extends AlertDialog.Builder {

    public static final String TYPE = "just_photo";

    private int mSelectedIndex = -1;

    private TextView mTitle;
    private TextView mCurrentFile;
    private ListView mListView;

    private FilenameFilter mFileNameFilter;

    private String mCurrentPath = Environment.getRootDirectory().getAbsolutePath();
    private String mFile;

    private List<File> mFiles = new ArrayList<>();
    private WeakReference<FileManagerDialogListener> mListener;

    public FileManagerDialog(final Context context, final String type) {
        super(context);
        mTitle = createTitle(context);
        LinearLayout linearLayout = createMainLayout(context);
        linearLayout.addView(createBackItem(context));
        mFiles.addAll(getFiles(mCurrentPath));
        mListView = createListView(context);
        mListView.setAdapter(new FileAdapter(context, mFiles));
        linearLayout.addView(mListView);
        setCustomTitle(mTitle)
                .setView(linearLayout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String path = mTitle.getText() + "/" + mFile;
                        if (type.equals(TYPE)) {
                            mListener.get().photoSelected(path);
                        } else {
                            addBook(context, path);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
    }

    private void addBook(Context context, String path) {

        try {
            InputStream is = new FileInputStream(path);
            mListener.get().fileSelected(is, path);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "File not found ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public AlertDialog show() {
        mFiles.addAll(getFiles(mCurrentPath));
        mListView.setAdapter(new FileAdapter(getContext(), mFiles));
        return super.show();
    }

    public FileManagerDialog setFilter(final String filter) {
        mFileNameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                File tempFile = new File(String.format("%s/%s", file.getPath(), s));
                if (tempFile.isFile()) return tempFile.getName().matches(filter);
                return true;
            }
        };
        return this;
    }

    private TextView createTextView(Context context, int style) {
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, style);
        int itemHeight = getItemHeight(context);
        textView.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
        textView.setMinHeight(itemHeight);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(15, 0, 0, 0);
        return textView;
    }

    private LinearLayout createMainLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setMinimumHeight(getLinearLayoutMinHeight(context));
        return linearLayout;
    }

    private void RebuildFiles(ArrayAdapter<File> adapter) {
        try {
            List<File> fileList = getFiles(mCurrentPath);
            mFiles.clear();
            mSelectedIndex = -1;
            mFiles.addAll(fileList);
            adapter.notifyDataSetChanged();
            mTitle.setText(mCurrentPath);
        } catch (NullPointerException e) {
            Toast.makeText(getContext(),
                    android.R.string.unknownName, Toast.LENGTH_LONG).show();
        }
    }

    private ListView createListView(Context context) {
        ListView listView = new ListView(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                final ArrayAdapter<File> adapter = (FileAdapter)
                        adapterView.getAdapter();
                File file = adapter.getItem(i);
                if (file.isDirectory()) {
                    mCurrentPath = file.getPath();
                    RebuildFiles(adapter);
                } else {
                    if (i != mSelectedIndex) mSelectedIndex = i;
                    else mSelectedIndex = -1;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return listView;
    }

    private class FileAdapter extends ArrayAdapter<File> {
        public FileAdapter(Context context, List<File> files) {
            super(context, android.R.layout.simple_list_item_1, files);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            mCurrentFile = (TextView) super.getView(position, convertView, parent);
            File file = getItem(position);
            mCurrentFile.setText(file.getName());
            if (mSelectedIndex == position) {
                mCurrentFile.setBackgroundColor(getContext()
                        .getResources().getColor(android.R.color.holo_blue_light));
                mFile = mCurrentFile.getText().toString();

            } else {
                mCurrentFile.setBackgroundColor(getContext()
                        .getResources().getColor(android.R.color.darker_gray));
            }
            return mCurrentFile;
        }
    }

    private List<File> getFiles(String directoryPath) {
        File directory = new File(directoryPath);
        List<File> fileList = Arrays.asList(directory.listFiles());
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file, File file2) {
                if (file.isDirectory() && file2.isFile())
                    return -1;
                else if (file.isFile() && file2.isDirectory())
                    return 1;
                else
                    return file.getPath().compareTo(file2.getPath());
            }
        });
        return fileList;
    }

    private static Display getDefaultDisplay(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    private static Point getScreenSize(Context context) {
        Point screenSize = new Point();
        getDefaultDisplay(context).getSize(screenSize);
        return screenSize;
    }

    private static int getLinearLayoutMinHeight(Context context) {
        return getScreenSize(context).y;
    }

    private TextView createTitle(Context context) {
        TextView textView = createTextView(context,
                android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        return textView;
    }

    private TextView createBackItem(Context context) {
        TextView textView = createTextView(context,
                android.R.style.TextAppearance_DeviceDefault_Small);
        Drawable drawable = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_directions);
        drawable.setBounds(0, 0, 60, 60);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(mCurrentPath);
                File parentDirectory = file.getParentFile();
                if (parentDirectory != null) {
                    mCurrentPath = parentDirectory.getPath();
                    RebuildFiles((FileAdapter) mListView.getAdapter());
                }
            }
        });
        return textView;
    }

    private int getItemHeight(Context context) {
        TypedValue value = new TypedValue();
        DisplayMetrics metrics = new DisplayMetrics();
        context.getTheme().resolveAttribute(android.R.attr.rowHeight, value, true);
        getDefaultDisplay(context).getMetrics(metrics);
        return (int) TypedValue.complexToDimension(value.data, metrics);
    }

    public void setListener(FileManagerDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

}
