package com.asi.moviereviewer.Listeners;

import com.asi.moviereviewer.Models.DetailAPIResponse;

public interface OnDetailsAPIListener {
    void onResponse(DetailAPIResponse response);
    void onError(String message);

}
