package in.avimarine.rcrecorder.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.amitshekhar.DebugDB;
import in.avimarine.rcrecorder.OrcscParser;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.dao.BoatRepository;
import in.avimarine.rcrecorder.dao.EventRepository;
import in.avimarine.rcrecorder.dao.EventViewModel;
import in.avimarine.rcrecorder.dao.EventsRoomDatabase;
import in.avimarine.rcrecorder.dao.PopulateDbAsync;
import in.avimarine.rcrecorder.dao.RaceRepository;
import in.avimarine.rcrecorder.dao.ResultRepository;
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
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Select Event");
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
      Log.d(TAG, "Got " + events.size() + "events");
      // Update the cached copy of the boats in the adapter.
      adapter.setEvents(events);
    });
    final ListView listview = findViewById(R.id.listview);
    listview.setAdapter(adapter);
    listview.setOnItemClickListener((parent, view, position, id) -> startRaceSelectActivity(
        adapter.getEvent((int) id).getKey()));
    listview.setOnItemLongClickListener((adapterView, view, i, l) -> {
      mEventViewModel.delete(adapter.getEvent(i));
      return true;
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_event_select, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    } else if(id == R.id.action_reset_all){
      resetAllDb();
    }
    return super.onOptionsItemSelected(item);
  }

  private void resetAllDb() {
    new EventRepository(getApplication()).deleteAll();
    new ResultRepository(getApplication()).deleteAll();
    new RaceRepository(getApplication()).deleteAll();
    new BoatRepository(getApplication()).deleteAll();
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
        Log.i(TAG, "Uri: " + uri.toString());
        addFileToDb(uri);
      }
    }
  }

  private void addFileToDb(Uri uri) {
    String orcscString = null;
    try {
      orcscString = Utils.readTextFromUri(uri, this);
    } catch (IOException e) {
      Log.e(TAG, "Error reading orcsc file", e);
    }
    db = Room.databaseBuilder(getApplicationContext(),
        EventsRoomDatabase.class, "events_database").build();
    Event e = OrcscParser.getEvent(orcscString);
    if (e==null){
      Log.e(TAG,"Unable to parse file");
      Toast.makeText(this,"Unable to parse file",Toast.LENGTH_LONG).show();
      return;
    }
    mEventViewModel.insert(e);
    PopulateDbAsync pda = new PopulateDbAsync(db,e);
    pda.execute(orcscString);
    Log.d(TAG,"Adding new event to DB");
  }

  private void startRaceSelectActivity(String key) {
    Log.d(TAG, "Opening activity");
    Bundle b = new Bundle();
    b.putString("EVENTKEY", key);
    b.putString("URI", "");
    Intent i = new Intent(this, RaceSelectActivity.class);
    i.putExtras(b);
    startActivity(i);
  }
}
