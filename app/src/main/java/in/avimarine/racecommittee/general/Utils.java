package in.avimarine.racecommittee.general;

import android.content.Context;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
