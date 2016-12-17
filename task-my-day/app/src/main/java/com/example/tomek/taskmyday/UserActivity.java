package com.example.tomek.taskmyday;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by tomek on 29.02.16.
 */
public class UserActivity extends FragmentActivity {
//    public DatabaseAdapter da;
    List<Event> list;
    final User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((EditText) findViewById(R.id.userName)).getText().toString().trim().isEmpty()) {
                    Toast.makeText(UserActivity.this, "Field Name shouldn't be empty", Toast.LENGTH_LONG).show();
                } else {
                    user.setName(((EditText) findViewById(R.id.userName)).getText().toString().trim());
                    user.setGroup(Integer.parseInt(((EditText) findViewById(R.id.group)).getText().toString().trim()));
                    user.setBirthDate(((EditText) findViewById(R.id.birthDate)).getText().toString().trim());
                    user.setNameDay(((EditText) findViewById(R.id.nameDay)).getText().toString().trim());
                    user.setImportantDate(((EditText) findViewById(R.id.importantDate)).getText().toString().trim());
                    user.setOther(((EditText) findViewById(R.id.other)).getText().toString().trim());
                    //da.addUser(user,);
                    //return to layout showing events for user_view -> list = da.getAllEvents();
                }
            }
        });
    }
}
