package com.example.tomek.taskmyday;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by tomek on 11.03.16.
 */
public class SpinnerHandler implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position).hashCode();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
