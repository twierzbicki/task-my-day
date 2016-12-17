package com.example.tomek.taskmyday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by tomek on 29.02.16.
 */

public class EventActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    DatabaseAdapter adapter = new DatabaseAdapter(this);

    EditText eventName;
    Spinner group;
    Spinner syncable;
    Spinner type;
    EditText description;
    TextView created;
    TextView dueDate;
    Spinner priority;
    EditText place;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);

        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        eventName = (EditText) findViewById(R.id.eventName);
        group = (Spinner) findViewById(R.id.group);
        syncable = (Spinner) findViewById(R.id.syncable);
        type = (Spinner) findViewById(R.id.type);
        description = (EditText) findViewById(R.id.description);
        created = (TextView) findViewById(R.id.created);
        dueDate = (TextView) findViewById(R.id.dueDate);
        priority = (Spinner) findViewById(R.id.priority);
        place = (EditText) findViewById(R.id.place);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        id = b.getLong("id");
        if (id == -1) {
            View created = findViewById(R.id.event_view).findViewById(R.id.row_created);
            ((ViewGroup) created.getParent()).removeView(created);
            spinnerListener();
        } else {
            eventName.setText(adapter.getEvent(id).getName());
            group.setSelection(adapter.getEvent(id).getGroup());
            syncable.setSelection(booleanToInt(adapter.getEvent(id).getSyncable()));
            type.setSelection(adapter.getEvent(id).getType());
            description.setText(adapter.getEvent(id).getDescription());
            created.setText(adapter.getEvent(id).getCreated());
            dueDate.setText(adapter.getEvent(id).getDueDate());
            priority.setSelection(adapter.getEvent(id).getPriority());
            place.setText(adapter.getEvent(id).getPlace());
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DateHandler();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void spinnerListener() {
        group.setOnItemSelectedListener(new SpinnerHandler());
        syncable.setOnItemSelectedListener(new SpinnerHandler());
        type.setOnItemSelectedListener(new SpinnerHandler());
        priority.setOnItemSelectedListener(new SpinnerHandler());
        syncable.setSelection(0);
        group.setSelection(2);
        type.setSelection(1);
        priority.setSelection(1);
    }

    public boolean intToBoolean(int value) {
        if (value == 0) {
            return false;
        } else return true;
    }

    public int booleanToInt(boolean value) {
        if (value == true) {
            return 1;
        } else return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dueDate.setText(new StringBuilder().append(dayOfMonth).append("-").append(monthOfYear + 1).append("-")
                .append(year).append(" "));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if (eventName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EventActivity.this, "Field Name shouldn't be empty", Toast.LENGTH_LONG).show();
                    break;
                }
                if (id == -1) {
                    DateHandler dh = new DateHandler();
                    Event addev = new Event(id,
                            eventName.getText().toString(),
                            group.getSelectedItemPosition(),
                            intToBoolean(syncable.getSelectedItemPosition()),
                            type.getSelectedItemPosition(),
                            description.getText().toString(),
                            String.valueOf(new StringBuilder().append(dh.day).append("-").append(dh.month + 1).append("-").append(dh.year).append(" ")),
                            dueDate.getText().toString(),
                            priority.getSelectedItemPosition(),
                            place.getText().toString());
                    adapter.addEvent(addev);
                    finish();
                }
                if (id != -1) {
                    Event addev = new Event(id,
                            eventName.getText().toString(),
                            group.getSelectedItemPosition(),
                            intToBoolean(syncable.getSelectedItemPosition()),
                            type.getSelectedItemPosition(),
                            description.getText().toString(),
                            created.getText().toString(),
                            dueDate.getText().toString(),
                            priority.getSelectedItemPosition(),
                            place.getText().toString());
                    adapter.updateEvent(addev);
                    finish();
                }
        }
    }
}
