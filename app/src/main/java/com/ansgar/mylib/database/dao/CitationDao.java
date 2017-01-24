package com.ansgar.mylib.database.dao;

import com.ansgar.mylib.database.entity.Citation;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

public interface CitationDao {

    void addCitation(Citation citation);

    void updateCitation(Citation citation);

    void deleteCitation(Citation citation);

    List<Citation> getAllCitations();

}
