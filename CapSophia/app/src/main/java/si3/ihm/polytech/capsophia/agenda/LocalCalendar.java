package si3.ihm.polytech.capsophia.agenda;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
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
import java.util.List;
import java.util.Locale;

import si3.ihm.polytech.capsophia.agenda.event.EventModel;

import static android.provider.CalendarContract.*;

/**
 * Created by user on 09/05/2017.
 */

public class LocalCalendar {

    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 42;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 96;

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
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 4, 14, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 4, 14, 8, 45);
        addEvent(new EventModel("Jazzercise", beginTime, endTime, "Group working", true));
        beginTime = Calendar.getInstance();
        beginTime.set(2017, 4, 19, 14, 30);
        endTime = Calendar.getInstance();
        endTime.set(2017, 4, 19, 15, 20);
        addEvent(new EventModel("Jazzercise", beginTime, endTime, "Group working", true));
    }

    public void addEvent(EventModel event) {
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(act, new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        long calID = getCalendarId();
        if (calID == -1) {
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

        long eventID = Long.parseLong(uri.getLastPathSegment());
        event.setId(eventID);
    }

    public void delEvent(EventModel event) {
        Uri eventUri;
        eventUri = Uri.parse("content://com.android.calendar/events");

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
        l_eventUri = Uri.parse("content://com.android.calendar/events");
        String[] l_projection = new String[]{"title", "dtstart", "dtend", "description"};
        Cursor l_managedCursor = act.getContentResolver().query(l_eventUri, l_projection, "calendar_id=" + getCalendarId(), null, "dtstart DESC, dtend DESC");
        if (l_managedCursor.moveToFirst()) {
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

    private static Calendar getDate(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar;
    }

    void clearEvents() {
        Uri eventUri;
        eventUri = Uri.parse("content://com.android.calendar/events");

        Cursor cursor = act.getContentResolver().query(eventUri, new String[]{"_id"}, "calendar_id = " + getCalendarId(), null, null); // calendar_id can change in new versions

        while(cursor.moveToNext()) {
            Uri deleteUri = ContentUris.withAppendedId(eventUri, cursor.getInt(0));

            act.getContentResolver().delete(deleteUri, null, null);
        }
    }

}
