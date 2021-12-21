package com.asi.moviereviewer.Listeners;

import com.asi.moviereviewer.Models.SearchAPIResponse;

public interface OnSearchAPIListner {

    void onResponse(SearchAPIResponse response);
    void onError(String message);

}
