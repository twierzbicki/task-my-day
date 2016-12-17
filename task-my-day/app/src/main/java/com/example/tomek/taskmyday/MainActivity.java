package com.example.tomek.taskmyday;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    DatabaseAdapter DbAdapter = new DatabaseAdapter(this);

    EditText footerEventName;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lview = (ListView) findViewById(R.id.list);
        lview.setEmptyView(findViewById(R.id.empty_list_item));

        TableRow footer = (TableRow) getLayoutInflater().inflate(R.layout.listview_footer, null);
        lview.addFooterView(footer);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        footerEventName = (EditText) findViewById(R.id.footerEventName);

        List<Event> event = DbAdapter.getAllEvents();
        for (Event ev : event) {
            Cursor cursor = DbAdapter.queueAll();
            String[] from = new String[]{DatabaseAdapter.NAME, DatabaseAdapter.DUE_DATE, DatabaseAdapter.PRIORITY};
            int[] to = new int[]{R.id.eventName, R.id.dueDate, R.id.priority};

            SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.listview_item, cursor, from, to, 0);
           // MyCursorAdapter cursorAdapter = new MyCursorAdapter(this, R.layout.listview_item, cursor, from, to, 0);
            lview.setAdapter(cursorAdapter);
        }

        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DbAdapter.deleteEvent(id);
                refreshList();
                return true;
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle b = new Bundle();
                b.putLong("id", id);
                Intent in = new Intent(getApplicationContext(), EventActivity.class);
                in.putExtras(b);
                startActivity(in);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (footerEventName.getText().toString().trim().length() == 0) {
                    Bundle b = new Bundle();
                    b.putLong("id", -1);
                    Intent in = new Intent(this, EventActivity.class);
                    in.putExtras(b);
                    startActivity(in);
                    footerEventName.getText().clear();
                    break;
                }
                if (footerEventName.getText().toString().trim().length() != 0) {
                    DateHandler dh = new DateHandler();
                    Event addev = new Event(id,
                            footerEventName.getText().toString(),
                            2,
                            false,
                            1,
                            "",
                            String.valueOf(new StringBuilder().append(dh.day).append("-").append(dh.month + 1).append("-").append(dh.year).append(" ")),
                            "",
                            1,
                            "");
                    DbAdapter.addEvent(addev);
                    refreshList();
                    footerEventName.getText().clear();
                    break;
                }
        /*    case R.id.activity_main:
                if (footerEventName.getText().toString().trim().length() != 0) {
                    Event addev = new Event(id,
                            footerEventName.getText().toString(),
                            2, false, 1, "", "", "", 1, "");
                    DbAdapter.addEvent(addev);
                    refreshList();
                    footerEventName.getText().clear();
                    break;
                }*/
        }
    }
    private class MyCursorAdapter extends SimpleCursorAdapter {

        public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }
/*        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
        }*/

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            RelativeLayout element = (RelativeLayout) view.findViewById(R.id.listview_item);
            TextView content = (TextView) view.findViewById(R.id.eventName);
            TextView date = (TextView) view.findViewById(R.id.dueDate);

            content.setText(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.NAME)));
            date.setText(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.DUE_DATE)));
            int priority = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.PRIORITY));

            if (priority == 0) {
                element.setBackgroundColor(Color.argb(50, 0, 0, 0));
            }
            if (priority == 1) {
                element.setBackgroundColor(Color.argb(50, 0, 200, 0));
            }
            if (priority == 2) {
                element.setBackgroundColor(Color.argb(50, 255, 0, 0));
            }
        }
    }
    private void refreshList() {
        ListView lview = (ListView) findViewById(R.id.list);
        lview.setEmptyView(findViewById(R.id.empty_list_item));

        final DatabaseAdapter adapter = new DatabaseAdapter(this);

        List<Event> event = adapter.getAllEvents();
        if (!event.isEmpty()) {
            for (Event ev : event) {
                Cursor cursor = adapter.queueAll();

                String[] from = new String[]{DatabaseAdapter.NAME, DatabaseAdapter.DUE_DATE, DatabaseAdapter.PRIORITY};
                int[] to = new int[]{R.id.eventName, R.id.dueDate, R.id.priority};
                //SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.listview_item, cursor, from, to, 0);
                MyCursorAdapter cursorAdapter = new MyCursorAdapter(this, R.layout.listview_item, cursor, from, to, 0);
                lview.setAdapter(cursorAdapter);
            }
        } else {
            lview.setEmptyView(findViewById(R.id.empty_list_item));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

