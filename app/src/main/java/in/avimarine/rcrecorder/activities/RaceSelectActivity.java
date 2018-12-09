package in.avimarine.rcrecorder.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import in.avimarine.rcrecorder.OrcscExporter;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.dao.EventsRoomDatabase;
import in.avimarine.rcrecorder.dao.RaceViewModel;
import in.avimarine.rcrecorder.dao.ResultViewModel;
import in.avimarine.rcrecorder.listadapters.RaceListAdapter;
import in.avimarine.rcrecorder.objects.Race;
import in.avimarine.rcrecorder.objects.Result;

public class RaceSelectActivity extends AppCompatActivity {

  private static final String TAG = "RaceSelectActivity";
  EventsRoomDatabase db;
  private RaceViewModel mRaceViewModel;
  private OrcscExporter exporter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_select);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Select Race");
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
      startRaceInputActivity(eventKey, item.orcRaceId, item.classId);
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_race_select, menu);
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
    } else if (id == R.id.action_export) {
      exportOrcscFile();
    }
    return super.onOptionsItemSelected(item);
  }

  private void exportOrcscFile() {
    ResultViewModel rvm = ViewModelProviders.of(this).get(ResultViewModel.class);
    rvm.getAllResults().observe(this, results -> {
      exporter = new OrcscExporter();
      exporter.openFileForExport(this,results);
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      Intent resultData) {
    if (exporter!=null){
      exporter.openFileResult(requestCode,resultCode,resultData);
    }
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
