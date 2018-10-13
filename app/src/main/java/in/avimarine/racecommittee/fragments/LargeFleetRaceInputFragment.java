package in.avimarine.racecommittee.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.dao.BoatViewModel;
import in.avimarine.racecommittee.listadapters.CountryGridAdapter;
import in.avimarine.racecommittee.listadapters.NumbersGridAdapter;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Action;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Country;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class LargeFleetRaceInputFragment extends TabFragement{

  private List<Boat> list;
  private HashSet<Country> countryList;
  private String selectedCountry = "";
  private String selectedSailNo = "";
  private TextView sailNoTv;
  private TextView countryCodeTv;
  private int sectionNumber;
  private static final String ARG_SECTION_NUMBER = "section_number";
  private BoatViewModel mBoatViewModel;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View ret = inflater.inflate(R.layout.fragment_large_fleet_race_input, container, false);


    return ret;
  }
  @Override
  public LargeFleetRaceInputFragment newInstance(int sectionNumber, ArrayList<Boat> boats) {
    LargeFleetRaceInputFragment fragment = new LargeFleetRaceInputFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    View v = getView();
    final GridView gridView = v.findViewById(R.id.grid);
    sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
    sailNoTv = v.findViewById(R.id.sail_no_tv);
    countryCodeTv = v.findViewById(R.id.country_code_tv);
    countryList = getCountries(list);
    CountryGridAdapter adapter = new CountryGridAdapter(getActivity(), countryList.toArray(new Country[0]), sectionNumber,IdType.SAIL_NO);
    gridView.setAdapter(adapter);

    gridView.setOnItemClickListener((parent, view, position, id) -> {
      final Country c = (Country) parent.getItemAtPosition(position);
      countryBtnOnClick(sectionNumber, c,v.findViewById(R.id.country_code_tv));
      RelativeLayout rl = view.findViewById(R.id.gridItem);
    });
    setNumberPadLayout(v.findViewById(R.id.numbers_grid),sailNoTv);
    Button setBtn = v.findViewById(R.id.set_btn);
    setBtn.setOnClickListener(setBtnClick());
    mBoatViewModel = ViewModelProviders.of(this).get(BoatViewModel.class);
    mBoatViewModel.getAllBoats().observe(this, boats -> {
      adapter.setCountries(getCountries(boats));
      list = boats;
      adapter.notifyDataSetChanged();
    });
  }

  private OnClickListener setBtnClick() {
    return view -> {
        String sailNo = selectedCountry+selectedSailNo;
        Boat b = getBoat(list, sailNo);
        if (b!=null) {
          btnOnClick(sectionNumber,b);
//          b.setFinish(new Date());
          clearTvs();
          return;
        }
      Toast.makeText(getActivity(),"Could not find boat",Toast.LENGTH_SHORT).show();
    };
  }

  private void btnOnClick(int tab, Boat o) {
    mBoatViewModel.delete(o);
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
    mBoatViewModel.insert(o);
  }

  private void clearTvs() {
    selectedCountry = "";
    selectedSailNo = "";
    countryCodeTv.setText(selectedCountry);
    sailNoTv.setText(selectedSailNo);
  }

  private Boat getBoat(List<Boat> list, String sailNo) {
    String clearSailNo = sailNo.replace("-", "").replace(" ","");
    for (Boat b: list){
      String checkedSailNo = b.getSailNo().replace("-", "").replace(" ","");
      if (checkedSailNo.equals(clearSailNo)){
        return b;
      }
    }
    return null;
  }

  private HashSet<Country> getCountries(List<Boat> list) {
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
  private void countryBtnOnClick(int tab, Country c, TextView tv) {
    String countryCode = c.code;
    selectedCountry = countryCode;
    tv.setText(countryCode + '-');
  }

  private void setNumberPadLayout(GridView gv, TextView tv){
    ArrayList<Action> actions = createActionList();
    NumbersGridAdapter adapter = new NumbersGridAdapter(getActivity(), actions.toArray(new Action[actions.size()]), sectionNumber);
    gv.setAdapter(adapter);

    gv.setOnItemClickListener((parent, view, position, id) -> {
      final Action a = (Action) parent.getItemAtPosition(position);
      numberBtnOnClick(sectionNumber, a.number,tv);
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
