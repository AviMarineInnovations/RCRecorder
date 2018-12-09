package in.avimarine.orcscorerxmlparser;

import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import java.io.File;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class OrcscSerializer {
  public static String serialize(OrcscFile fileObject) throws Exception {
    // Maybe you have to correct this or use another / no Locale
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    RegistryMatcher m = new RegistryMatcher();
    m.bind(Date.class, new DateFormatTransformer(format));
    Serializer serializer = new Persister(m);

    StringWriter sw  = new StringWriter();
    serializer.write(fileObject, sw);
    return sw.toString();
  }
  public static void serialize(OrcscFile fileObject, File f) throws Exception {
    // Maybe you have to correct this or use another / no Locale
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    RegistryMatcher m = new RegistryMatcher();
    m.bind(Date.class, new DateFormatTransformer(format));
    Serializer serializer = new Persister(m);
    serializer.write(fileObject, f);
  }
}
