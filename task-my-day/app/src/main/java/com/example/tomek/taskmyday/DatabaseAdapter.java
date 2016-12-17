package com.example.tomek.taskmyday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DatabaseAdapter extends SQLiteOpenHelper {
    public DatabaseAdapter(Context context, String name, Cursor factory, int version) {
        super(context, name, (SQLiteDatabase.CursorFactory) factory, version);
    }

    public SQLiteDatabase db;
    public DatabaseAdapter DbAdapter;
    private Context context;

    // Logcat tag
    private static final String DEBUG_TAG = "SqLiteTaskMyDay";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "database.db";

    // Table names
    private static final String TABLE_USER = "user";
    public static final String TABLE_EVENT = "event";
    private static final String TABLE_USER_EVENT = "user_event";

    // Common column names
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String GROUP = "_group";
    public static final String DESCRIPTION = "description";

    // USER specific column names
    public static final String LOCAL = "local";
    public static final String JOIN_DATE = "join_date";
    public static final String BIRTH_DATE = "birth_date";
    public static final String NAMEDAY = "nameday";
    public static final String IMPORTANT_DATE = "important_date";
    public static final String OTHER = "other";

    // EVENT specific column names
    public static final String SYNCABLE = "syncable";
    public static final String TYPE = "type";
    public static final String CREATED = "created";
    public static final String DUE_DATE = "due_date";
    public static final String PRIORITY = "priority";
    public static final String PLACE = "place";

    // USER_EVENT column names
    private static final String USER_ID = "user_id";
    private static final String EVENT_ID = "_id";

    // TABLE USER create statement
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + "( "
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_ID + " INTEGER, "
                    + NAME + " TEXT, "
                    + GROUP + " INTEGER, "
                    + LOCAL + " INTEGER, "
                    + JOIN_DATE + " TEXT, "
                    + BIRTH_DATE + " TEXT, "
                    + NAMEDAY + " TEXT, "
                    + IMPORTANT_DATE + " TEXT, "
                    + DESCRIPTION + " TEXT, "
                    + OTHER + " TEXT " +
                    ");";

    // TABLE EVENT create statement
    private static final String CREATE_TABLE_EVENT =
            "CREATE TABLE " + TABLE_EVENT + "( "
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    //   + EVENT_ID + " INTEGER, "
                    + NAME + " TEXT, "
                    + GROUP + " INTEGER, "
                    + SYNCABLE + " INTEGER, "
                    + TYPE + " INTEGER, "
                    + DESCRIPTION + " TEXT, "
                    + CREATED + " TEXT, "
                    + DUE_DATE + " TEXT, "
                    + PRIORITY + " INTEGER, "
                    + PLACE + " TEXT " +
                    ");";

    // TABLE USER_EVENT create statement
    private static final String CREATE_TABLE_USER_EVENT =
            "CREATE TABLE " + TABLE_USER_EVENT + "( "
                    + USER_ID + " INTEGER, "
                    + EVENT_ID + " INTEGER " +
                    ");";

    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseAdapter openToRead() throws android.database.SQLException {
        DbAdapter = new DatabaseAdapter(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = DbAdapter.getReadableDatabase();
        return this;
    }

    public DatabaseAdapter openToWrite() throws android.database.SQLException {
        DbAdapter = new DatabaseAdapter(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = DbAdapter.getWritableDatabase();
        return this;
    }

    public Cursor queueAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = new String[]{EVENT_ID, NAME, DUE_DATE, PRIORITY};
        Cursor cursor = db.query(TABLE_EVENT, columns,
                null, null, null, null, null);

        return cursor;
    }

    public void close() {
        DbAdapter.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
//        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_EVENT);
//        db.execSQL(CREATE_TABLE_USER_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older versions
        //         db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        //           db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_EVENT);

        Log.d(DEBUG_TAG, "Database updating...");
        //         Log.d(DEBUG_TAG, "Table " + TABLE_USER + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "Table " + TABLE_EVENT + " updated from ver." + oldVersion + " to ver." + newVersion);
        //       Log.d(DEBUG_TAG, "Table " + TABLE_USER_EVENT + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");

        onCreate(db);
    }


    // CRUD (Create, Read, Update and Delete) Operations for Event
    public long addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, event.getName());
        values.put(GROUP, event.getGroup());
        values.put(SYNCABLE, event.getSyncable());
        values.put(TYPE, event.getType());
        values.put(DESCRIPTION, event.getDescription());
        values.put(CREATED, event.getCreated());
        values.put(DUE_DATE, event.getDueDate());
        values.put(PRIORITY, event.getPriority());
        values.put(PLACE, event.getPlace());

        long id = db.insert(TABLE_EVENT, null, values);
        db.close();
        return id;
    }

    public Event getEvent(long event_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE "
                + ID + " = " + event_id;

        Log.e(DEBUG_TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Event event = new Event();

        event.setId(c.getInt(c.getColumnIndex(ID)));
        event.setName(c.getString(c.getColumnIndex(NAME)));
        event.setGroup(c.getInt(c.getColumnIndex(GROUP)));
        int index = c.getColumnIndex(SYNCABLE);
        String str = c.getString(index);
        boolean bool = strToBoolean(str);
        event.setSyncable(bool);
        event.setType(c.getInt(c.getColumnIndex(TYPE)));
        event.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
        event.setCreated(c.getString(c.getColumnIndex(CREATED)));
        event.setDueDate(c.getString(c.getColumnIndex(DUE_DATE)));
        event.setPriority(c.getInt(c.getColumnIndex(PRIORITY)));
        event.setPlace(c.getString(c.getColumnIndex(PLACE)));

        c.close();
        return event;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt(c.getColumnIndex(ID)));
                event.setName((c.getString(c.getColumnIndex(NAME))));
                event.setDueDate((c.getString(c.getColumnIndex(DUE_DATE))));
                event.setPriority((c.getInt(c.getColumnIndex(PRIORITY))));
                events.add(event);
            } while (c.moveToNext());
        }
        c.close();
        return events;
    }

    // Update an Event
    public void updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, event.getName());
        values.put(GROUP, event.getGroup());
        values.put(SYNCABLE, event.getSyncable());
        values.put(TYPE, event.getType());
        values.put(DESCRIPTION, event.getDescription());
        values.put(CREATED, event.getCreated());
        values.put(DUE_DATE, event.getDueDate());
        values.put(PRIORITY, event.getPriority());
        values.put(PLACE, event.getPlace());

        db.update(TABLE_EVENT, values, ID + "=?", new String[]{String.valueOf(event.getId())});
        db.close();
    }

    // Delete an Event
    public void deleteEvent(long event_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENT, ID + " =?", new String[]{String.valueOf(event_id)});
    }

    public void closeDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public int booleanToInt(boolean value) {
        if (value == true) {
            return 1;
        } else return 0;
    }

    public boolean intToBoolean(int value) {
        if (value == 0) {
            return false;
        } else return true;
    }

    public boolean strToBoolean(String string) {
        if (string == "0") {
            return false;
        } else return true;
    }

}


    /*
    // CRUD (Create, Read, Update and Delete) Operations for User
    public long addUser(User user, long[] event_ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USER, user.getName());
        values.put(USER_ID, user.getId());
        values.put(GROUP, user.getGroup());
        values.put(LOCAL, user.getLocal());
        values.put(JOIN_DATE, user.getJoinDate());
        values.put(BIRTH_DATE, user.getBirthDate());
        values.put(NAMEDAY, user.getNameDay());
        values.put(IMPORTANT_DATE, user.getImportantDate());
        values.put(DESCRIPTION, user.getGroup());
        values.put(OTHER, user.getOther());

        // insert row
        long user_id = db.insert(TABLE_USER, null, values);

        // assigning Events to User
        for (long event_id : event_ids) {
            createUserEvent(user_id, event_id);
        }

        return user_id;
    }

    public User getUser(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + ID + " = " + user_id;

        Log.e(DEBUG_TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(ID)));
        user.setName((c.getString(c.getColumnIndex(NAME))));
        c.close();
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        Log.e(DEBUG_TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt(c.getColumnIndex(ID)));
                user.setName((c.getString(c.getColumnIndex(NAME))));
                users.add(user);
            } while (c.moveToNext());
        }
        c.close();
        return users;
    }
    // Delete User
    public void deleteUser(long user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, ID + " =?", new String[]{String.valueOf(user_id)});
    }

    // CRUD (Create, Read, Update and Delete) Operations for table User_event
    public long createUserEvent(long user_id, long event_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(EVENT_ID, event_id);
        long id = db.insert(TABLE_USER_EVENT, null, values);
        return id;
    }

    public int updateUserEvent(long id, long event_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_ID, event_id);
        return db.update(TABLE_USER, values, ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteUserEvent(long id){
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_USER, ID + " =?", new String[]{String.valueOf(id)});
    } */
