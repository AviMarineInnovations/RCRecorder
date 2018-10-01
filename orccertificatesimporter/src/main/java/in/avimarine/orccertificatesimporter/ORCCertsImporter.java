package in.avimarine.orccertificatesimporter;

import android.support.annotation.Nullable;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 29/09/2018.
 */
public class ORCCertsImporter {

  private static final String TAG = "ORCCertsImporter";

  static public void getCertsByCountry(CountryObj c, final CertsResponse cr){
    getCertsByCountry(c.getId(),cr);
  }
  static public void getCert (String name, String countryId, String sailNo, final CertsResponse cr){
    if (cr==null){
      Log.e(TAG, "Delegate is null");
      return;
    }

    RetrieveStreamTask rst = new RetrieveStreamTask();
    rst.delegate = new AsyncResponse() {
      @Override
      public void processFinish(String output) {
        String json = output;
        List<ORCCertObj> ret = new ArrayList<>();
        if (json!=null){
          try {
            JSONObject root = new JSONObject(json);
            JSONArray certsJson = root.getJSONArray("rms");

            for (int i=0; i<certsJson.length(); i++) {
              JSONObject jo = certsJson.getJSONObject(i);
              ORCCertObj cert = convertORCJsonToORCCertObj(jo);
              ret.add(cert);
            }

          } catch (Exception e) {
            Log.d(TAG,"Error parsing certificates",e);
          }
          cr.response(ret);
        }
      }
    };
    rst.execute("http://data.orc.org/public/WPub.dll?action=DownBoatRMS&YachtName="+name+"&SailNo="+sailNo+"&CountryId="+countryId+"&ext=json");
  }

  static public void getCountries(final CountriesResponse cr) {
    if (cr==null){
      Log.e(TAG, "Delegate is null");
      return;
    }
    RetrieveStreamTask rst = new RetrieveStreamTask();
    rst.delegate = new AsyncResponse() {
      @Override
      public void processFinish(String output) {
        String xml = output;
        Log.d(TAG, xml);
        List<CountryObj> ret = new ArrayList<>();
        if (xml != null) {
          try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("ROW");
            for (int i = 0; i < nList.getLength(); i++) {
              CountryObj co = new CountryObj();
              Node node = nList.item(i);
              if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;
                co.setId(getValue("CountryId", element2));
                co.setName(getValue("CountryName", element2));
                Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS").parse(getValue("LastUpdate",element2));
                co.setLastUpdate(date1);
                co.setCertificatesCount(Integer.valueOf(getValue("CertCount", element2)));
                co.setClubCertCount(Integer.valueOf(getValue("ClubCert", element2)));
              }
              ret.add(co);
            }
          cr.response(ret);

          } catch (Exception e) {
            Log.e(TAG, "Error parsing countries XML", e);
          }
        }
      }
    };
    rst.execute("http://data.orc.org/public/WPub.dll");
  }

  static public void getCertsByCountry(String countryCode, final CertsResponse cr) {
    if (cr==null){
      Log.e(TAG, "Delegate is null");
      return;
    }
    else if(GeneralUtils.isNullOrEmpty(countryCode)){
      Log.e(TAG, "Country code is null or empty");
      return;
    }
    RetrieveStreamTask rst = new RetrieveStreamTask();
    rst.delegate = new AsyncResponse() {
      @Override
      public void processFinish(String output) {
        String json = output;
        List<ORCCertObj> ret = new ArrayList<>();
        if (json!=null){
          try {
            JSONObject root = new JSONObject(json);
            JSONArray certsJson = root.getJSONArray("rms");

            for (int i=0; i<certsJson.length(); i++) {
              JSONObject jo = certsJson.getJSONObject(i);
              ORCCertObj cert = convertORCJsonToORCCertObj(jo);
              ret.add(cert);
            }
            cr.response(ret);
          } catch (Exception e) {
            Log.e(TAG,"Error parsing countries XML",e);
          }
        }
      }
    };
    rst.execute("http://data.orc.org/public/WPub.dll?action=DownRMS&CountryId="+countryCode+"&ext=json");
  }


  private static String getValue(String tag, Element element) {
    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
    Node node = nodeList.item(0);
    return node.getNodeValue();
  }
  @Nullable
  static private ORCCertObj convertORCJsonToORCCertObj(JSONObject jo) {
    if (jo == null)
      return null;
    ORCCertObj ret = new ORCCertObj();
    try{
      ret.setNatAuth(jo.getString("NatAuth"));
      ret.setCertNo(jo.getString("CertNo"));
      ret.setRefNo(jo.getString("RefNo"));
      ret.setSailNo(jo.getString("SailNo"));
      ret.setYachtsName(jo.getString("YachtName"));
      ret.setYachtsClass(jo.getString("Class"));
      ret.setCertType(ORCCertificateType.fromString(jo.getString("C_Type")));
      ret.setDivision(jo.getString("Division"));
      Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'").parse(jo.getString("IssueDate"));
      ret.setIssueDate(date1);
      ret.setAgeYear(jo.getInt("Age_Year"));
      ret.setLOA(jo.getDouble("LOA"));
      ret.setCDL(jo.getDouble("CDL"));
      ret.setTMFInshore(jo.getDouble("TMF_Inshore"));
      ret.setTMFOffshore(jo.getDouble("TMF_Offshore"));
      ret.setILCWA(jo.getDouble("ILCWA"));
      ret.setOSN(jo.getDouble("OSN"));
      ret.setTNInshoreLow(jo.getDouble("TN_Inshore_Low"));
      ret.setTNInshoreMedium(jo.getDouble("TN_Inshore_Medium"));
      ret.setTNInshoreHigh(jo.getDouble("TN_Inshore_High"));
      ret.setTNOffshoreLow(jo.getDouble("TN_Offshore_Low"));
      ret.setTNOffshoreMedium(jo.getDouble("TN_Offshore_Medium"));
      ret.setTNOffshoreHigh(jo.getDouble("TN_Offshore_High"));
      ret.setTNDInshoreLow(jo.getDouble("TND_Inshore_Low"));
      ret.setTNDInshoreMedium(jo.getDouble("TND_Inshore_Medium"));
      ret.setTNDInshoreHigh(jo.getDouble("TND_Inshore_High"));
      ret.setTNDOffshoreLow(jo.getDouble("TND_Offshore_Low"));
      ret.setTNDOffshoreMedium(jo.getDouble("TND_Offshore_Medium"));
      ret.setTNDOffshoreHigh(jo.getDouble("TND_Offshore_High"));


    }catch (Exception e){
      Log.e(TAG,"Error converting json to cert",e);
      return null;
    }

    return ret;
  }





}
