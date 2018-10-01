package in.avimarine.orccertificatesimporter;

import android.os.AsyncTask;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 29/09/2018.
 */
class RetrieveStreamTask extends AsyncTask<String, Void, String> {
  private static final String TAG = "RetrieveStreamTask";
  public AsyncResponse delegate = null;
  protected String doInBackground(String... urls) {
    try {
      URL url = new URL(urls[0]);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.connect();
      InputStream inputStream = urlConnection.getInputStream();
      String ret = readFullyAsString(inputStream,"UTF-8");
      urlConnection.disconnect();
      return ret;
    } catch (IOException e) {
      Log.d(TAG,"Error getting file",e);
    }
    return null;
  }

  public String readFullyAsString(InputStream inputStream, String encoding)
      throws IOException {
    return readFully(inputStream).toString(encoding);
  }

  public byte[] readFullyAsBytes(InputStream inputStream)
      throws IOException {
    return readFully(inputStream).toByteArray();
  }

  private ByteArrayOutputStream readFully(InputStream inputStream)
      throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length = 0;
    while ((length = inputStream.read(buffer)) != -1) {
      baos.write(buffer, 0, length);
    }
    return baos;
  }
  protected void onPostExecute(String data) {
    if (delegate!=null) {
      delegate.processFinish(data);
    }
  }
}
