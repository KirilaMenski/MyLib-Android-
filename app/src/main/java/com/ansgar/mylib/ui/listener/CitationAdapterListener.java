package com.ansgar.mylib.ui.listener;

import com.ansgar.mylib.database.entity.Citation;

/**
 * Created by kirill on 30.1.17.
 */
public interface CitationAdapterListener {

    void likeCitation(Citation citation);

    void deleteCitation(Citation citation);

}
