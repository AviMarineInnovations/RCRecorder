package in.avimarine.orcscorerxmlparser.Orcsc;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Text;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
class Reports {
  @ElementList(inline=true,required = false)
  List<Logo> logoList;
  @ElementList(inline=true, required = false)
  List<Report> reportList;
}
class Logo {
  @Attribute
  String name;
  @Attribute
  String filename;
  @Attribute
  String mediatype;
  @Text (required = false)
  String value;
}

class Report {
  @Attribute
  String name;
  @Attribute
  String id;
  @Attribute
  String title;
  @ElementList(inline=true)
  List<Column> list;
}

class Column {
  @Attribute
  String fieldname;
  @Attribute
  String title;
  @Attribute
  int alignment;
  @Attribute
  boolean visible;
}