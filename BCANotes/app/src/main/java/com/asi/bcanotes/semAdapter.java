package com.asi.bcanotes;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class semAdapter extends ArrayAdapter<DataModel> {


    public semAdapter(@NonNull Context context, ArrayList<DataModel> dataModelArrayList){
        super(context, 0, dataModelArrayList);
    }
}
