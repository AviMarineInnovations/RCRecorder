package in.avimarine.rcrecorder;

import java.util.HashMap;
import java.util.Map;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 08/12/2018.
 */
public enum FinishType {
  OK(0), DNF(1), DNC(2), DNS(3), OCS(4), BFD(5), RET(6), DSQ(7), DNE(8), ZFP(10), UFD(11),
  SCP(12), RDG(13), DPI(14), TLE(15);

  private final int value;
  private static Map map = new HashMap<>();
  static {
    for (FinishType finish : FinishType.values()) {
      map.put(finish.value, finish);
    }
  }

  FinishType(int i) {
    this.value = i;
  }
  public static FinishType valueOf(int finish) {
    return (FinishType) map.get(finish);
  }

  public int getValue() {
    return value;
  }

}
