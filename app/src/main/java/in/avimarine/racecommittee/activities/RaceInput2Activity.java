package in.avimarine.racecommittee.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.ListAdapters.BoatGridAdapter;
import in.avimarine.racecommittee.ListAdapters.RaceListAdapter;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Race;
import java.util.ArrayList;
import java.util.Date;

public class RaceInput2Activity extends AppCompatActivity {

  private ArrayList<Boat> list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_input2);
    final GridView gridView = findViewById(R.id.grid);
    Bundle b = this.getIntent().getExtras();
    if (b != null) {
      list = b.getParcelableArrayList("BOATLIST");
    }
    BoatGridAdapter adapter = new BoatGridAdapter(this, list.toArray(new Boat[list.size()]), 1,IdType.BOAT_NAME);
    gridView.setAdapter(adapter);

    gridView.setOnItemClickListener((parent, view, position, id) -> {
      final Boat boat = (Boat) parent.getItemAtPosition(position);
      btnOnClick(1, boat);
      RelativeLayout rl = view.findViewById(R.id.gridItem);
      setItemBg(boat,rl,1);

    });
    gridView.setOnItemLongClickListener((parent, view, position, l) -> {
      final Boat boat = (Boat) parent.getItemAtPosition(position);
      btnOnLongClick(1, boat);
      RelativeLayout rl = view.findViewById(R.id.gridItem);
      setItemBg(boat,rl,1);
      return true;
    });
  }


  private void btnOnLongClick(int tab, Boat o) {
    list.remove(o);
    if (tab == 1) {
      o.setCheckIn(null);
    } else if (tab == 2) {
      o.setOCS(null);
    } else {
      o.setFinish(null);
    }
    list.add(o);
  }

  private void btnOnClick(int tab, Boat o) {
    list.remove(o);
    if (tab == 1) {
      if (o.getCheckIn() == null) {
        o.setCheckIn(new Date());
      }
    } else if (tab == 2) {
      if (o.getOCS() == null) {
        o.setOCS(new Date());
      }
    } else {
      if (o.getFinish() == null) {
        o.setFinish(new Date());
      }
    }
    list.add(o);
  }

  private  void setItemBg(Boat o, View rl, int tab) {
    if (tab == 1) {
      if (o.getCheckIn() != null) {
        rl.setBackgroundColor(Color.CYAN);
      } else {
        rl.setBackgroundColor(Color.GRAY);
      }
    } else if (tab == 2) {
      if (o.getOCS() != null) {
        rl.setBackgroundColor(Color.CYAN);
      } else {
        rl.setBackgroundColor(Color.GRAY);
      }
    } else {
      if (o.getFinish() != null) {
        rl.setBackgroundColor(Color.CYAN);
      } else {
        rl.setBackgroundColor(Color.GRAY);
      }
    }
  }
}
