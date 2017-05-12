package si3.ihm.polytech.capsophia;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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
        /*if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

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
        beginTime.set(2017, 4, 14, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 4, 14, 8, 45);
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
        long eventID = Long.parseLong(uri.getLastPathSegment());*/
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 4, 14, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 4, 14, 8, 45);
        EventModel event = new EventModel("Jazzercise", beginTime, endTime, "Group working", true);
        addEvent(event);
    }

    public void addEvent(EventModel event) {
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(act, new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        long calID = getCalendarId();
        if (calID == -1) {
            // no calendar account; react meaningfully
            return;
        }
        long startMillis = event.getStartDate().getTimeInMillis();
        long endMillis = event.getEndDate().getTimeInMillis();
        ContentResolver cr = act.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startMillis);
        values.put(Events.DTEND, endMillis);
        values.put(Events.TITLE, event.getName());
        values.put(Events.DESCRIPTION, event.getDescription());
        values.put(Events.CALENDAR_ID, calID);
        values.put(Events.EVENT_TIMEZONE, "Europe/Paris");
        Uri uri = cr.insert(Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        event.setId(eventID);
    }

    public void delEvent(EventModel event) {
        Uri eventUri;
        if (Build.VERSION.SDK_INT >= 8) {
            eventUri = Uri.parse("content://com.android.calendar/events");
        } else {
            eventUri = Uri.parse("content://calendar/events");
        }

        if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(act, new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }

        String[] selArgs =
                new String[]{Long.toString(event.getId())};
        int deleted =
                act.getContentResolver().
                        delete(
                                eventUri,
                                Events._ID + " =? ",
                                selArgs);
    }

    public List<EventModel> readEvents() {

        List<EventModel> res = new ArrayList<>();

        Uri l_eventUri;
        if (Build.VERSION.SDK_INT >= 8) {
            l_eventUri = Uri.parse("content://com.android.calendar/events");
        } else {
            l_eventUri = Uri.parse("content://calendar/events");
        }
        String[] l_projection = new String[]{"title", "dtstart", "dtend", "description"};
        Cursor l_managedCursor = act.getContentResolver().query(l_eventUri, l_projection, "calendar_id=" + getCalendarId(), null, "dtstart DESC, dtend DESC");
        if (l_managedCursor.moveToFirst()) {
            int l_cnt = 0;
            String l_title;
            Calendar l_begin;
            Calendar l_end;
            String l_description;
            int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);
            int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);
            int l_colEnd = l_managedCursor.getColumnIndex(l_projection[2]);
            int l_colDesc = l_managedCursor.getColumnIndex(l_projection[3]);
            do {
                l_title = l_managedCursor.getString(l_colTitle);
                l_begin = getDate(Long.parseLong(l_managedCursor.getString(l_colBegin)));
                l_end = getDate(Long.parseLong(l_managedCursor.getString(l_colEnd)));
                l_description = l_managedCursor.getString(l_colDesc);
                res.add(new EventModel(l_title, l_begin, l_end, l_description, true));
            } while (l_managedCursor.moveToNext());
            System.out.println(res);
        }

        return res;
    }

    public static Calendar getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar;
    }

    public void clearEvents() {
        Uri eventUri;
        if (Build.VERSION.SDK_INT >= 8) {
            eventUri = Uri.parse("content://com.android.calendar/events");
        } else {
            eventUri = Uri.parse("content://calendar/events");
        }

        Cursor cursor = act.getContentResolver().query(eventUri, new String[]{"_id"}, "calendar_id = " + getCalendarId(), null, null); // calendar_id can change in new versions

        while(cursor.moveToNext()) {
            Uri deleteUri = ContentUris.withAppendedId(eventUri, cursor.getInt(0));

            act.getContentResolver().delete(deleteUri, null, null);
        }
    }

}
