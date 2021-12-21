package com.asi.movieflex.Listeners;

import com.asi.movieflex.Models.SearchAPIResponse;

public interface OnSearchAPIListner {

    void onResponse(SearchAPIResponse response);
    void onError(String message);
}
