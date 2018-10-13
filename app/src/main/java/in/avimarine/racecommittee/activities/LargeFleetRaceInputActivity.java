package in.avimarine.racecommittee.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.listadapters.CountryGridAdapter;
import in.avimarine.racecommittee.listadapters.NumbersGridAdapter;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Action;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Country;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class LargeFleetRaceInputActivity extends AppCompatActivity {

  private ArrayList<Boat> list;
  private HashSet<Country> countryList;
  private String selectedCountry = "";
  private String selectedSailNo = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_large_fleet_race_input);
    final GridView gridView = findViewById(R.id.grid);
    Bundle b = this.getIntent().getExtras();
    if (b != null) {
      list = b.getParcelableArrayList("BOATLIST");
    }
    countryList = getCountries(list);
    CountryGridAdapter adapter = new CountryGridAdapter(this, countryList.toArray(new Country[0]), 1,IdType.SAIL_NO);
    gridView.setAdapter(adapter);

    gridView.setOnItemClickListener((parent, view, position, id) -> {
      final Country c = (Country) parent.getItemAtPosition(position);
      btnOnClick(1, c,findViewById(R.id.country_code_tv));
      RelativeLayout rl = view.findViewById(R.id.gridItem);
//      setItemBg(c,rl,1);
    });
    setNumberPadLayout(findViewById(R.id.numbers_grid),findViewById(R.id.sail_no_tv));
    Button setBtn = findViewById(R.id.set_btn);
    setBtn.setOnClickListener(setBtnClick());
  }

  private OnClickListener setBtnClick() {
    return view -> {
        String sailNo = selectedCountry+selectedSailNo;
        Boat b = getBoat(list, sailNo);
        if (b!=null) {
          b.setFinish(new Date());
          clearTvs();
          return;
        }
      Toast.makeText(this,"Could not find boat",Toast.LENGTH_SHORT).show();
    };
  }

  private void clearTvs() {
    TextView country = findViewById(R.id.country_code_tv);
    TextView sailNo = findViewById(R.id.sail_no_tv);
    selectedCountry = "";
    selectedSailNo = "";
    country.setText(selectedCountry);
    sailNo.setText(selectedSailNo);
  }

  private Boat getBoat(ArrayList<Boat> list, String sailNo) {
    String clearSailNo = sailNo.replace("-", "").replace(" ","");
    for (Boat b: list){
      String checkedSailNo = b.getSailNo().replace("-", "").replace(" ","");
      if (checkedSailNo.equals(clearSailNo)){
        return b;
      }
    }
    return null;
  }

  private HashSet<Country> getCountries(ArrayList<Boat> list) {
    HashSet<Country> ret = new HashSet<>();
    if (list == null)
      return ret;
    for (Boat b:list){
      ret.add(new Country(getCountryCode(b.getSailNo()),getCountryName(getCountryCode(b.getSailNo()))));
    }

    return ret;

  }

  private String getCountryName(String countryCode) {
    return ""; //TODO implement
  }

  private String getCountryCode(String sailNo) {
    return sailNo.substring(0,3);
  }
  private void btnOnClick(int tab, Country c, TextView tv) {
    String countryCode = c.code;
    selectedCountry = countryCode;
    tv.setText(countryCode + '-');
  }

  private void setNumberPadLayout(GridView gv, TextView tv){
    ArrayList<Action> actions = createActionList();
    NumbersGridAdapter adapter = new NumbersGridAdapter(this, actions.toArray(new Action[actions.size()]), 1);
    gv.setAdapter(adapter);

    gv.setOnItemClickListener((parent, view, position, id) -> {
      final Action a = (Action) parent.getItemAtPosition(position);
      numberBtnOnClick(1, a.number,tv);
    });
  }

  private void numberBtnOnClick(int i, int a, TextView tv) {
    if (a==-2){
      if(selectedSailNo.length()>0) {
        selectedSailNo = selectedSailNo.substring(0, selectedSailNo.length() - 1);
      }
    }
    if (a>=0){
      selectedSailNo += a;
    }
    tv.setText(selectedSailNo);
  }

  private ArrayList<Action> createActionList() {
    ArrayList<Action> ret = new ArrayList<>();
    ret.add(new Action("1",1));
    ret.add(new Action("2",2));
    ret.add(new Action("3",3));
    ret.add(new Action("4",4));
    ret.add(new Action("5",5));
    ret.add(new Action("6",6));
    ret.add(new Action("7",7));
    ret.add(new Action("8",8));
    ret.add(new Action("9",9));
    ret.add(new Action("",-1));
    ret.add(new Action("0",0));
    ret.add(new Action("<-",-2));
    return ret;
  }
}
