package in.avimarine.racecommittee.fragments;


import android.support.v4.app.Fragment;
import in.avimarine.racecommittee.objects.Boat;
import java.util.ArrayList;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
public abstract class TabFragement extends Fragment {
  public abstract TabFragement newInstance(int sectionNumber, ArrayList<Boat> list);
}
