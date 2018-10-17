package in.avimarine.rcrecorder.activities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import in.avimarine.rcrecorder.OrcscParser;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.dao.BoatRoomDatabase;
import in.avimarine.rcrecorder.dao.PopulateDbAsync;
import in.avimarine.rcrecorder.general.Utils;
import in.avimarine.rcrecorder.listadapters.RaceListAdapter;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Race;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class RaceSelectActivity extends AppCompatActivity {

  BoatRoomDatabase db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_select);
    Bundle b = this.getIntent().getExtras();
    String uriString = b.getString("URI");
    Uri uri = Uri.parse(uriString);
    String orcscString = null;
    try {
      orcscString = Utils.readTextFromUri(uri,this);
    } catch (IOException e) {
      e.printStackTrace();
    }
    db = Room.databaseBuilder(getApplicationContext(),
        BoatRoomDatabase.class, "boat_database").build();
    List<Race> list = OrcscParser.getRaces(orcscString);
    List<Boat> boats = OrcscParser.getBoats(orcscString);
    if (boats!=null&&!boats.isEmpty()) {
      PopulateDbAsync pda = new PopulateDbAsync(db);
      pda.execute(boats.toArray(new Boat[0]));
    }
    final ListView listview = findViewById(R.id.listview);

    RaceListAdapter adapter = new RaceListAdapter(this, list.toArray(new Race[list.size()]));
    listview.setAdapter(adapter);

    listview.setOnItemClickListener((parent, view, position, id) -> {
      final Race item = (Race) parent.getItemAtPosition(position);
      Intent i = new Intent(this, RaceInputActivity.class);
      startActivity(i);

    });
  }

  private class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
        List<String> objects) {
      super(context, textViewResourceId, objects);
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
    }

    @Override
    public long getItemId(int position) {
      String item = getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }

  }



}
