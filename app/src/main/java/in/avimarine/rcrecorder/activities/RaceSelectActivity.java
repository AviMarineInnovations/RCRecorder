package in.avimarine.rcrecorder.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.dao.EventsRoomDatabase;
import in.avimarine.rcrecorder.dao.RaceViewModel;
import in.avimarine.rcrecorder.listadapters.RaceListAdapter;
import in.avimarine.rcrecorder.objects.Race;

public class RaceSelectActivity extends AppCompatActivity {

  private static final String TAG = "RaceSelectActivity";
  EventsRoomDatabase db;
  private RaceViewModel mRaceViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_select);
    Bundle b = this.getIntent().getExtras();
    String eventKey = b.getString("EVENTKEY");
    db = Room.databaseBuilder(getApplicationContext(),
        EventsRoomDatabase.class, "events_database").build();
    RaceListAdapter adapter = new RaceListAdapter(this);
    mRaceViewModel = ViewModelProviders.of(this).get(RaceViewModel.class);
    mRaceViewModel.getRacesByEventKey(eventKey).observe(this, adapter::setRaces);
    final ListView listview = findViewById(R.id.listview);
    listview.setAdapter(adapter);
    listview.setOnItemClickListener((parent, view, position, id) -> {
      final Race item = (Race) parent.getItemAtPosition(position);
      startRaceInputActivity(eventKey,item.orcRaceId, item.classId);
    });
  }

  private void startRaceInputActivity(String eventkey, int raceID, String classId) {
    Log.d(TAG, "Opening race input activity, eventkey: " + eventkey + ", raceId: " + raceID);
    Bundle b = new Bundle();
    b.putString("EVENTKEY", eventkey);
    b.putInt("RACEID", raceID);
    b.putString("CLASSID", classId);
    Intent i = new Intent(this, RaceInputActivity.class);
    i.putExtras(b);
    startActivity(i);
  }
}
