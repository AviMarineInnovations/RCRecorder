package in.avimarine.rcrecorder.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.amitshekhar.DebugDB;
import in.avimarine.rcrecorder.OrcscParser;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.dao.EventViewModel;
import in.avimarine.rcrecorder.dao.EventsRoomDatabase;
import in.avimarine.rcrecorder.general.Utils;
import in.avimarine.rcrecorder.listadapters.EventListAdapter;
import in.avimarine.rcrecorder.objects.Event;
import java.io.IOException;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 29/09/2018.
 */
public class EventSelectActivity extends AppCompatActivity {

  private static final int READ_REQUEST_CODE = 42;
  private static final String TAG = "EventSelectActivity";
  EventsRoomDatabase db;
  private EventViewModel mEventViewModel;
  private EventListAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    DebugDB.getAddressLog();
    db = Room.databaseBuilder(getApplicationContext(),
        EventsRoomDatabase.class, "events_database").build();
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openOrcscFile();
      }
    });
    adapter = new EventListAdapter(this);
    mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
    mEventViewModel.getAllEvents().observe(this, events -> {
      // Update the cached copy of the boats in the adapter.
      adapter.setEvents(events);
    });
    final ListView listview = findViewById(R.id.listview);
    listview.setAdapter(adapter);
    listview.setOnItemClickListener((parent, view, position, id) -> {
      parseAndStartActivity(null); //TODO pass proper data to raceselectactivity
    });

  }

  private void openOrcscFile() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    // Filter to show only images, using the image MIME data type.
    // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
    // To search for all documents available via installed storage providers,
    // it would be "*/*".
    intent.setType("*/*");
    startActivityForResult(intent, READ_REQUEST_CODE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      Intent resultData) {
    Log.d(TAG, "In on ActivityResult");
    // The ACTION_OPEN_DOCUMENT intent was sent with the request code
    // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
    // response to some other intent, and the code below shouldn't run at all.

    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      // The document selected by the user won't be returned in the intent.
      // Instead, a URI to that document will be contained in the return intent
      // provided to this method as a parameter.
      // Pull that URI using resultData.getData().
      Uri uri = null;
      if (resultData != null) {
        uri = resultData.getData();
        String orcscString = null;
        try {
          orcscString = Utils.readTextFromUri(uri, this);
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (orcscString == null) {
          Log.e(TAG, "Error parsing file");
        }
        Event e = OrcscParser.getEvent(orcscString);
        mEventViewModel.insert(e);
        Log.i(TAG, "Uri: " + uri.toString());
        parseAndStartActivity(uri);
      }
    }
  }

  private void parseAndStartActivity(Uri uri) {
    Log.d(TAG, "Opening activity");
    Bundle b = new Bundle();
    b.putString("URI", uri.toString());
    Intent i = new Intent(this, RaceSelectActivity.class);
    i.putExtras(b);
    startActivity(i);
  }
}
