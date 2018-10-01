package in.avimarine.orccertificatesimporter;

import static org.junit.Assert.*;

import android.util.Log;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 29/09/2018.
 */
public class ORCCertsImporterTest {

  @Test
  public void getCertsByCountry() {
  }

  @Test
  public void getCert() {
    ORCCertsImporter oci = new ORCCertsImporter();
    oci.getCert("Lou of Ixopo", "", "", new CertsResponse() {
      @Override
      public void response(List<ORCCertObj> ret) {
        Assert.fail();
        Assert.assertEquals(1,ret.size());
      }
    });
  }

  @Test
  public void getCountries() {
  }

  @Test
  public void getCertsByCountry1() {
  }
}