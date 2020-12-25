package in.avimarine.rcrecorder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import in.avimarine.orcscorerxmlparser.Orcsc.RsltRow;
import in.avimarine.orcscorerxmlparser.OrcscDeserializer;
import in.avimarine.orcscorerxmlparser.OrcscSerializer;
import in.avimarine.rcrecorder.general.Utils;
import in.avimarine.rcrecorder.objects.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 09/12/2018.
 */
public class OrcscExporter {

    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "OrcscExporter";
    private Activity activity;
    private List<Result> results;

    public void openFileForExport(Activity a, List<Result> results) {
        activity = a;
        this.results = results;
        openOrcscFile(activity);
    }

    private void openOrcscFile(Activity a) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");
        a.startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void openFileResult(int requestCode, int resultCode,
                               Intent resultData) {
        Log.d(TAG, "In on ActivityResult");
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());
                exportToUri(uri);
            }
        }
    }

    private void exportToUri(Uri uri) {
        String orcscString = null;
        try {
            orcscString = Utils.readTextFromUri(uri, activity);
        } catch (IOException e) {
            Log.e(TAG, "Error reading from file", e);
        }
        if (orcscString == null) {
            Log.e(TAG, "Error parsing file");
        }
        OrcscFile f = null;
        try {
            f = OrcscDeserializer.deserialize(orcscString);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing file", e);
        }

        if (f != null) {
            if (f.rslt.list != null) {
                f.rslt.list.clear();
            }
            List<RsltRow> rows = convertToRsltList(results);
            if (rows == null) {
                Log.e(TAG, "Error converting results to rsltrows");
                Toast.makeText(activity, "Error getting results", Toast.LENGTH_LONG).show();
            } else {
                f.rslt.list = rows;
                String orcscFileString = null;
                try {
                    orcscFileString = OrcscSerializer.serialize(f);
                } catch (Exception e) {
                    Log.e(TAG, "Error serializing file", e);
                }
                if (orcscFileString == null) {
                    Log.e(TAG, "Error serializing file");
                    Toast.makeText(activity, "Error exporting file", Toast.LENGTH_LONG).show();
                    return;
                }
                saveStringToFile("amit1.orcsc", orcscFileString);
            }
        }
    }

    private List<RsltRow> convertToRsltList(List<Result> results) {
        if (results == null) {
            return null;
        }
        List<RsltRow> ret = new ArrayList<>();
        for (Result r : results) {
            RsltRow rr = new RsltRow();
            rr.Penalty = r.getPenalty();
            rr.Remarks = r.getRemarks();
            rr.YID = r.getYachtId();
            rr.RaceId = r.getRaceId();
            rr.FinishTime = r.getFinishTime();
            rr.Finish = r.getFinish().getValue();
            ret.add(rr);
        }
        return ret;
    }

    private void saveStringToFile(String fileName, String s) {
        if (!isExternalStorageWritable()) {
            Log.e(TAG, "Storage not available");
            Toast.makeText(activity, "Storage not available", Toast.LENGTH_LONG).show();
            return;
        }
        try (FileOutputStream outputStream = new FileOutputStream(
                new File(getPublicDocumentsStorageDir("Results"), fileName))) {
            outputStream.write(s.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            Log.e(TAG, "Error serializing file", e);
            Toast.makeText(activity, "Error exporting file", Toast.LENGTH_LONG).show();
        }
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private File getPublicDocumentsStorageDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), fileName);
        if (!file.mkdirs() && !file.exists()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

}
