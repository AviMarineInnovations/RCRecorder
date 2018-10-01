package in.avimarine.racecommittee;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import in.avimarine.orccertificatesimporter.ORCCertObj;
import in.avimarine.orccertificatesimporter.ORCCertsImporter;
import in.avimarine.racecommittee.objects.Boat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RaceInputActivity extends AppCompatActivity {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
   * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
   * fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link
   * android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;
  protected static List<Boat> boats;
  protected static int radioId = R.id.identification_radio_sailno;
  protected static IdType sortBy = IdType.SAIL_NO;
  protected static UpdateInitiator ui;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_race_input);
    ui = new UpdateInitiator();
    Bundle b = this.getIntent().getExtras();
    boats = b.getParcelableArrayList("BOATLIST");
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Winter 18 Race1");
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    } else if (id == R.id.action_sortby_sail) {
      sortBy = IdType.SAIL_NO;
      ui.declareUpdate();
      return true;
    } else if (id == R.id.action_sortby_bow) {
      sortBy = IdType.BOW_NO;
      ui.declareUpdate();
      return true;
    } else if (id == R.id.action_sortby_name) {
      sortBy = IdType.BOAT_NAME;
      ui.declareUpdate();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class RaceInputFragment extends Fragment {

    private static final String TAG = "RaceInputFragment";
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TableLayout tableLayout;
    private int sectionNumber;

    public RaceInputFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static RaceInputFragment newInstance(int sectionNumber) {
      RaceInputFragment fragment = new RaceInputFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_race_input, container, false);
      tableLayout = rootView.findViewById(R.id.boatsButtonTable);
      sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
      generateButtonTable(tableLayout, boats,
          radioId, sectionNumber, sortBy);
      addRadioButtonClickListeneres(rootView);
      ui.addListener(() -> generateButtonTable(tableLayout, boats,
          radioId, sectionNumber, sortBy));
      return rootView;
    }

    private void addRadioButtonClickListeneres(View v) {
      RadioGroup radioGroup = v.findViewById(R.id.btn_lbl_radiogroup);
      radioGroup.setOnCheckedChangeListener(
          (group, checkedId) -> {
            generateButtonTable(tableLayout, boats,
                checkedId, sectionNumber, sortBy);
            radioId = checkedId;
          }
      );
    }

    private void generateButtonTable(TableLayout tableLayout, List<Boat> l, int idType, int tab,
        IdType sortBy) {
      int count = 0;
      int maxItemsPerRow = 4;
      tableLayout.removeAllViews();
      TableRow tr = null;
      switch (sortBy) {
        case BOW_NO:
          Collections.sort(l, (boat, t1) -> boat.getBowNo() - t1.getBowNo());
          break;
        case BOAT_NAME:
          Collections.sort(l,
              (obj1, obj2) -> obj1.getYachtsName().compareToIgnoreCase(obj2.getYachtsName()));
          break;
        case SAIL_NO:
        default:
          Collections
              .sort(l, (obj1, obj2) -> obj1.getSailNo().compareToIgnoreCase(obj2.getSailNo()));
          break;
      }

      for (Boat o : l) {
        if (count % maxItemsPerRow == 0) {
          if (tr != null) {
            tableLayout.addView(tr);
          }
          tr = new TableRow(getActivity());
          tr.setGravity(Gravity.TOP);
          TableRow.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
              LayoutParams.WRAP_CONTENT,
              1.0f);
          tr.setBaselineAligned(false);
          tr.setLayoutParams(lp);
        }
        Button b = new Button(getContext());
        if (idType == R.id.identification_radio_sailno) {
          b.setText(o.getSailNo());
        } else if (idType == R.id.identification_radio_bowno) {
          b.setText(String.valueOf(o.getBowNo()));
        } else {
          b.setText(o.getYachtsName());
        }
        int h = (int) TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        TableRow.LayoutParams lp = new LayoutParams(0,
            h,
            1.0f);
        b.setLayoutParams(lp);
        if (tab == 1) {
          if (o.getCheckIn() != null) {
            b.setBackgroundColor(Color.CYAN);
          } else {
            b.setBackgroundColor(Color.GRAY);
          }
        } else if (tab == 2) {
          if (o.getOCS() != null) {
            b.setBackgroundColor(Color.CYAN);
          } else {
            b.setBackgroundColor(Color.GRAY);
          }
        } else {
          if (o.getFinish() != null) {
            b.setBackgroundColor(Color.CYAN);
          } else {
            b.setBackgroundColor(Color.GRAY);
          }
        }

        b.setOnClickListener(view -> {
          b.setBackgroundColor(Color.CYAN);
          btnOnClick(tab, o);
        });
        b.setOnLongClickListener(view -> {
          b.setBackgroundColor(Color.GRAY);
          btnOnLongClick(tab, o);
          return true;
        });

        tr.addView(b);
        count++;
      }
      tableLayout.addView(tr);
    }

    private void btnOnLongClick(int tab, Boat o) {
      boats.remove(o);
      if (tab == 1) {
        o.setCheckIn(null);
      } else if (tab == 2) {
        o.setOCS(null);
      } else {
        o.setFinish(null);
      }
      boats.add(o);
    }

    private void btnOnClick(int tab, Boat o) {
      boats.remove(o);
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
      boats.add(o);
    }
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      // getItem is called to instantiate the fragment for the given page.
      // Return a RaceInputFragment (defined as a static inner class below).
      return RaceInputFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
      // Show 3 total pages.
      return 3;
    }
  }

  public interface UpdateListener {
    void update();
  }

  class UpdateInitiator {
    private List<UpdateListener> listeners = new ArrayList<>();

    void addListener(UpdateListener toAdd) {
      listeners.add(toAdd);
    }

    void declareUpdate() {
      // Notify everybody that may be interested.
      for (UpdateListener ul : listeners)
        ul.update();
    }
  }
}
