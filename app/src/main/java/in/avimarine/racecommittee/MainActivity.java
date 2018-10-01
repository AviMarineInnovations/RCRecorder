package in.avimarine.racecommittee;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import in.avimarine.orccertificatesimporter.ORCCertObj;
import in.avimarine.orccertificatesimporter.ORCCertsImporter;
import in.avimarine.racecommittee.objects.Boat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 29/09/2018.
 */
public class MainActivity extends AppCompatActivity {

  private static final int READ_REQUEST_CODE = 42;
  private static final String TAG = "MainActivity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main1);

    openOrcscFile();

//    ORCCertsImporter.getCertsByCountry("ISR",ret -> {
//      ArrayList<Boat> boats = convertToBoatList(ret);
//      Bundle b = new Bundle();
//      b.putParcelableArrayList("BOATLIST",boats);
//      Intent i = new Intent(this,RaceInputActivity.class);
//      i.putExtras(b);
//      startActivity(i);
//    });
  }

  private void openOrcscFile() {
    // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
    // browser.
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

    // Filter to only show results that can be "opened", such as a
    // file (as opposed to a list of contacts or timezones)
    intent.addCategory(Intent.CATEGORY_OPENABLE);

    // Filter to show only images, using the image MIME data type.
    // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
    // To search for all documents available via installed storage providers,
    // it would be "*/*".
    intent.setType("*/*");

    startActivityForResult(intent, READ_REQUEST_CODE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      Intent resultData) {

    // The ACTION_OPEN_DOCUMENT intent was sent with the request code
    // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
    // response to some other intent, and the code below shouldn't run at all.

    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      // The document selected by the user won't be returned in the intent.
      // Instead, a URI to that document will be contained in the return intent
      // provided to this method as a parameter.
      // Pull that URI using resultData.getData().
      Uri uri = null;
      if (resultData != null) {
        uri = resultData.getData();
        Log.i(TAG, "Uri: " + uri.toString());
        try {
          String res = readTextFromUri(uri);
          parseAndStartActivity(res);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void parseAndStartActivity(String res) {
    ArrayList<Boat> boats = OrcscParser.getBoats(res);
    Bundle b = new Bundle();
    b.putParcelableArrayList("BOATLIST", boats);
    Intent i = new Intent(this, RaceInputActivity.class);
    i.putExtras(b);
    startActivity(i);
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


  @NotNull
  private ArrayList<Boat> convertToBoatList(List<ORCCertObj> l) {
    ArrayList<Boat> ret = new ArrayList<>();
    if (l == null) {
      return ret;
    }
    int c = 1;
    for (ORCCertObj o : l) {
      Boat b = new Boat();
      b.setBowNo(c++);
      b.setLOA(o.getLOA());
      b.setRefNo(o.getRefNo());
      b.setSailNo(o.getSailNo());
      b.setYachtsClass(o.getYachtsClass());
      b.setYachtsName(o.getYachtsName());
      ret.add(b);
    }
    return ret;
  }
}
