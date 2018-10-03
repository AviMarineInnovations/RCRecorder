package in.avimarine.racecommittee.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import in.avimarine.racecommittee.ListAdapters.RaceListAdapter;
import in.avimarine.racecommittee.OrcscParser;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Race;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaceSelectActivity extends AppCompatActivity {

  ArrayList<Boat> boats;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_select);
    Bundle b = this.getIntent().getExtras();
    String uriString = b.getString("URI");

    Uri uri = Uri.parse(uriString);
    String orcscString = null;
    try {
      orcscString = readTextFromUri(uri);
    } catch (IOException e) {
      e.printStackTrace();
    }
    ArrayList<Race> list = OrcscParser.getRaces(orcscString);
    boats = OrcscParser.getBoats(orcscString);
    final ListView listview = findViewById(R.id.listview);

    RaceListAdapter adapter = new RaceListAdapter(this, list.toArray(new Race[list.size()]));
    listview.setAdapter(adapter);

    listview.setOnItemClickListener((parent, view, position, id) -> {
      final Race item = (Race) parent.getItemAtPosition(position);
      Bundle newB = new Bundle();
      newB.putParcelableArrayList("BOATLIST", boats);
      Intent i = new Intent(this, RaceInputActivity.class);
      i.putExtras(newB);
      startActivity(i);

//      view.animate().setDuration(2000).alpha(0)
//          .withEndAction(() -> {
//            list.remove(item);
//            adapter.notifyDataSetChanged();
//            view.setAlpha(1);
//          });
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

  private String readTextFromUri(Uri uri) throws IOException {
    InputStream inputStream = getContentResolver().openInputStream(uri);
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        inputStream));
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
    }
    inputStream.close();
//      parcelFileDescriptor.close();
    return stringBuilder.toString();
  }

}
