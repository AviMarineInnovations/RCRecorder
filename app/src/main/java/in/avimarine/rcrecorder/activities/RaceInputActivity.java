package in.avimarine.rcrecorder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import in.avimarine.rcrecorder.IdType;
import in.avimarine.rcrecorder.R;
//import in.avimarine.rcrecorder.fragments.LargeFleetRaceInputFragment;
import in.avimarine.rcrecorder.fragments.LargeFleetRaceInputFragment;
import in.avimarine.rcrecorder.fragments.RaceInputFragment;
import in.avimarine.rcrecorder.fragments.TabFragement;
import in.avimarine.rcrecorder.objects.Boat;
import java.util.ArrayList;
import java.util.List;

public class RaceInputActivity extends AppCompatActivity {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
   * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
   * fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link
   * android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;
  private static final String TAG = "RaceInputActivity";
  private static final String FRAGMENTCLASS = "FRAGMENT_CLASS";
  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;
//  protected static ArrayList<Boat> boats;
  protected static int radioId = R.id.identification_radio_sailno;
  protected static IdType sortBy = IdType.SAIL_NO;
  private String eventKey;
  private ArrayList<Integer> raceIds;
  private String classId;
//  protected static UpdateInitiator ui;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_input);
    Bundle b = this.getIntent().getExtras();
    eventKey = b.getString("EVENTKEY");
    raceIds = b.getIntegerArrayList("RACEIDS");
    int fragement = 1;
//    try {//TODO return when ready for big input activity
//      fragement = b.getInt(FRAGMENTCLASS);
//    }catch (Exception e) {
//      Log.d(TAG,"No FRAGMENT_CLASS value available");
//    }
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Winter 18 Race1"); //TODO: Get race name from the select race activity
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    if (fragement==0) {
      mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
          LargeFleetRaceInputFragment.class);
      Log.d(TAG,"The fragment class is LargeFleetRaceInputFragment");
    }
    else {
      mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
          RaceInputFragment.class);
      Log.d(TAG,"The fragment class is RaceInputFragment");
    }

    // Set up the ViewPager with the sections adapter.
    mViewPager = findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);
    TabLayout tabLayout = findViewById(R.id.tabs);
    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_race_input, menu);
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
    } else if (id == R.id.action_sortby_sail) {
      sortBy = IdType.SAIL_NO;
      sendTabAndSortBroadcast(sortBy);
      return true;
    } else if (id == R.id.action_sortby_bow) {
      sortBy = IdType.BOW_NO;
      sendTabAndSortBroadcast(sortBy);
      return true;
    } else if (id == R.id.action_sortby_name) {
      sortBy = IdType.BOAT_NAME;
      sendTabAndSortBroadcast(sortBy);
      return true;
    } else if (id == R.id.action_fleet_size) {
      Intent mIntent = getIntent();
      Bundle b = mIntent.getExtras();
      if (b==null)
        b=new Bundle();
      if (mSectionsPagerAdapter.fragementClass == LargeFleetRaceInputFragment.class) {
        b.putInt(FRAGMENTCLASS, 1);
      }
      else {
        b.putInt(FRAGMENTCLASS, 0);
      }
      b.putString("EVENTKEY",eventKey);
      b.putIntegerArrayList("RACEID",raceIds);
      b.putString("CLASSID",classId);
      mIntent.putExtras(b);
      finish();
      startActivity(mIntent);
      this.recreate();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private IdType getIdType(int radioId) {
    if (radioId == R.id.identification_radio_sailno) {
      return IdType.SAIL_NO;
    } else if (radioId == R.id.identification_radio_bowno) {
      return IdType.BOW_NO;
    } else {
      return IdType.BOAT_NAME;
    }
  }

  private void sendTabAndSortBroadcast(IdType sortBy) {
    Intent intent = new Intent("TABANDSORT");
    intent.putExtra("SORTBY",sortBy.toString());
    sendBroadcast(intent);
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public class SectionsPagerAdapter<T extends TabFragement> extends FragmentPagerAdapter {
    Class<T> fragementClass;

    public SectionsPagerAdapter(FragmentManager fm, Class<T> fc) {
      super(fm);
      fragementClass = fc;
    }

    @Override
    public Fragment getItem(int position) {
      // getItem is called to instantiate the fragment for the given page.
      try {
        return fragementClass.newInstance().newInstance(position + 1,eventKey,raceIds);
      } catch (InstantiationException | IllegalAccessException e) {
        Log.e(TAG,"Instantiation error",e);
      }
      return null;
    }
    @Override
    public int getCount() {
      // Show 3 total pages.
      return 3;
    }
  }

//  public interface UpdateListener {
//    void update();
//  }
//
//  class UpdateInitiator {
//    private List<UpdateListener> listeners = new ArrayList<>();
//
//    void addListener(UpdateListener toAdd) {
//      listeners.add(toAdd);
//    }
//
//    void declareUpdate() {
//      // Notify everybody that may be interested.
//      for (UpdateListener ul : listeners)
//        ul.update();
//    }
//  }
}
