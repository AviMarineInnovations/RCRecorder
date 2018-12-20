package in.avimarine.rcrecorder.general;

import android.content.Context;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 16/10/2018.
 */
public class Utils {

  public static String readTextFromUri(Uri uri, Context c) throws IOException {
    InputStream inputStream = c.getContentResolver().openInputStream(uri);
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        inputStream));
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
    }
    inputStream.close();
    return stringBuilder.toString();
  }


  public static String toSimpleDate(Date d) {
    DateFormat dateFormat = new SimpleDateFormat("dd/mm/YYYY hh:mm:ss");
    return dateFormat.format(d);
  }

  public static int[] getIntArray(List<Integer> integers) {
    if (integers == null) {
      return new int[0];
    }
    int[] ret = new int[integers.size()];
    Iterator<Integer> iterator = integers.iterator();
    for (int i = 0; i < ret.length; i++) {
      ret[i] = iterator.next();
    }
    return ret;
  }

  public static String[] getStringArray(List<String> strings) {
    if (strings == null) {
      return new String[0];
    }
    String[] ret = new String[strings.size()];
    Iterator<String> iterator = strings.iterator();
    for (int i = 0; i < ret.length; i++) {
      ret[i] = iterator.next();
    }
    return ret;
  }
}
