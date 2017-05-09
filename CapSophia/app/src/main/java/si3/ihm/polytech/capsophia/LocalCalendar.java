package si3.ihm.polytech.capsophia;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import static android.provider.CalendarContract.*;

/**
 * Created by user on 09/05/2017.
 */

public class LocalCalendar {

    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 42;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 96;

    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private static final String MY_ACCOUNT_NAME = "TestIHM";
    private final Activity act;

    public LocalCalendar(Activity act) {
        this.act = act;
        ContentValues values = new ContentValues();
        values.put(
                Calendars.ACCOUNT_NAME,
                MY_ACCOUNT_NAME);
        values.put(
                Calendars.ACCOUNT_TYPE,
                ACCOUNT_TYPE_LOCAL);
        values.put(
                Calendars.NAME,
                "GrokkingAndroid Calendar");
        values.put(
                Calendars.CALENDAR_DISPLAY_NAME,
                "GrokkingAndroid Calendar");
        values.put(
                Calendars.CALENDAR_COLOR,
                0xffff0000);
        values.put(
                Calendars.CALENDAR_ACCESS_LEVEL,
                Calendars.CAL_ACCESS_OWNER);
        values.put(
                Calendars.OWNER_ACCOUNT,
                "some.account@googlemail.com");
        values.put(
                Calendars.CALENDAR_TIME_ZONE,
                "Europe/Paris");
        values.put(
                Calendars.SYNC_EVENTS,
                1);
        Uri.Builder builder =
                Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(
                Calendars.ACCOUNT_NAME,
                "com.grokkingandroid");
        builder.appendQueryParameter(
                Calendars.ACCOUNT_TYPE,
                ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(
                CALLER_IS_SYNCADAPTER,
                "true");
        Uri uri = act.getContentResolver().insert(builder.build(), values);
    }

    private long getCalendarId() {
        String[] projection = new String[]{Calendars._ID};
        String selection =
                Calendars.ACCOUNT_NAME +
                        " = ? AND " +
                        Calendars.ACCOUNT_TYPE +
                        " = ? ";
        // use the same values as above:
        String[] selArgs =
                new String[]{
                        MY_ACCOUNT_NAME,
                        CalendarContract.ACCOUNT_TYPE_LOCAL};
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act, new String[]{android.Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);
        }
        Cursor cursor =
                act.getContentResolver().
                        query(
                                Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1;
    }

    public void addEvent() {
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(act, new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        long calID = getCalendarId();
        if (calID == -1) {
            // no calendar account; react meaningfully
            return;
        }
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 9, 14, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 9, 14, 8, 45);
        endMillis = endTime.getTimeInMillis();
        ContentResolver cr = act.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startMillis);
        values.put(Events.DTEND, endMillis);
        values.put(Events.TITLE, "Jazzercise");
        values.put(Events.DESCRIPTION, "Group workout");
        values.put(Events.CALENDAR_ID, calID);
        values.put(Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri = cr.insert(Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
    }

    public List<EventModel> readEvents() {
// Run query
        Cursor cur = null;
        ContentResolver cr = act.getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"hera@example.com", "com.example",
                "hera@example.com"};
// Submit the query and get a Cursor object back.
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act, new String[]{android.Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);
        }
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        return null;
    }

}
