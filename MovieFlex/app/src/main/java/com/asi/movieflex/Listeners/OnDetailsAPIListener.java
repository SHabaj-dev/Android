package com.asi.movieflex.Listeners;

import com.asi.movieflex.Models.DetailAPIResponse;

public interface OnDetailsAPIListener {
    void onResponse(DetailAPIResponse response);
    void onError(String message);
}
