package in.avimarine.rcrecorder.activities;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import in.avimarine.rcrecorder.OrcscExporter;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.dao.EventsRoomDatabase;
import in.avimarine.rcrecorder.dao.RaceViewModel;
import in.avimarine.rcrecorder.dao.ResultViewModel;
import in.avimarine.rcrecorder.listadapters.NewRaceListAdapter;
import in.avimarine.rcrecorder.objects.Race;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RaceSelectActivity extends AppCompatActivity {

    private static final String TAG = "RaceSelectActivity";
    private static final int PERMISSIONRESULTCODE = 1245;
    EventsRoomDatabase db;
    NewRaceListAdapter adapter;
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
        adapter = new NewRaceListAdapter(this);
        mRaceViewModel = ViewModelProviders.of(this).get(RaceViewModel.class);
        mRaceViewModel.getRacesByEventKey(eventKey).observe(this, adapter::setRaces);
        final ListView listview = findViewById(R.id.listview);
        final Button selectButton = findViewById(R.id.races_select_btn);
        selectButton.setOnClickListener(view -> {
            final List<Race> selectedRaces = getSelectedItems(listview);
            if (selectedRaces != null && selectedRaces.size() > 0) {
                startRaceInputActivity(eventKey, getRaceIds(selectedRaces));
            }
        });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            Log.d(TAG, "In itemclick listener");
            adapter.setSelected(getSelectedItems(listview));
        });
    }

    private ArrayList<Integer> getRaceIds(List<Race> selectedRaces) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Race r : selectedRaces) {
            ret.add(r.orcRaceId);
        }
        return ret;
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportOrcscFile() {
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSIONRESULTCODE)) {
            ResultViewModel rvm = ViewModelProviders.of(this).get(ResultViewModel.class);
            rvm.getAllResults().observe(this, results -> {
                exporter = new OrcscExporter();
                exporter.openFileForExport(this, results);
            });
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONRESULTCODE) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportOrcscFile();
            } else {
                Toast.makeText(RaceSelectActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (exporter != null) {
            exporter.openFileResult(requestCode, resultCode, resultData);
        }
    }

    // Function to check and request permission
    public boolean checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(RaceSelectActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(RaceSelectActivity.this, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

    private void startRaceInputActivity(String eventkey, ArrayList<Integer> raceIDs) {
        Log.d(TAG, "Opening race input activity, eventkey: " + eventkey + ", raceId: " + raceIDs);
        Bundle b = new Bundle();
        b.putString("EVENTKEY", eventkey);
        b.putIntegerArrayList("RACEIDS", raceIDs);
        Intent i = new Intent(this, RaceInputActivity.class);
        i.putExtras(b);
        startActivity(i);
    }


    private List<Race> getSelectedItems(ListView listView) {
        List<Race> result = new ArrayList<>();
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        if (checkedItems.size() == 0) {
            return result;
        }
        for (int i = 0; i < checkedItems.size(); ++i) {
            if (checkedItems.valueAt(i)) {
                result.add((Race) listView.getItemAtPosition(checkedItems.keyAt(i)));
            }
        }
        return result;
    }
}
