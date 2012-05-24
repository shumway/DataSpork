package dataspork.reader;
import dataspork.tree.*;
import dataspork.interp.*;
import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import dataspork.interp.ScalarDataset;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Stack;

//public class XmlDocHandler extends HandlerBase {
public class XmlDocHandler extends DefaultHandler {
  protected URL urlContext;
  protected String[] fileNames = new String[1];
  protected String[] readerNames = new String[1];
  protected int[] group = new int[1];
  protected int fileCount;
  protected int groupFlag;
  protected boolean grouped;

  public XmlDocHandler(URL urlContext) {
    this.urlContext=urlContext;
    fileCount = 0;
    groupFlag = 0;
    grouped = false;
  };

  //public void startElement(java.lang.String name, AttributeList atts) {
  public void startElement(java.lang.String uri, java.lang.String localName, 
              java.lang.String name, Attributes atts) {

    if (name.equals("simGroup")) {
      // start another group
      groupFlag++;
      grouped = true;
    }
    else if(name.equals("simRef")) {
      if (fileCount >= fileNames.length) {
        String[] resizeFile = new String[fileCount+1];
        String[] resizeReader = new String[fileCount+1];
        int[] resizeGroup = new int[fileCount+1];
        for (int i=0;i<fileCount;i++) {
          resizeFile[i] = fileNames[i];
          resizeReader[i] = readerNames[i];
          resizeGroup[i] = group[i];
        }
        fileNames = resizeFile;
        readerNames = resizeReader;
        group = resizeGroup;
      }

      fileNames[fileCount] = (String)atts.getValue("ref");
      readerNames[fileCount] = (String)atts.getValue("reader");
      group[fileCount] = 0;
      if (grouped) group[fileCount] = groupFlag;
      fileCount++;
    }
  }

  public void endElement(java.lang.String namespaceURI,
                         java.lang.String localName,
                         java.lang.String name) throws SAXException {
    if (name.equals("simGroup")) grouped = false;
  }

  public String[] getFileList() {
    return fileNames;
  }

  public String[] getReaderList() {
    return readerNames;
  }

  public int[] getGroupList() {
    return group;
  }

  public void error(SAXParseException e) throws SAXException {
    System.out.println("Error parsing line "+e.getLineNumber()+" of "
                       +e.getSystemId());
    System.out.println(e.getMessage());
  }

  public void fatalError(SAXParseException e) throws SAXException {
    System.out.println("fatalError:"+e);
  }

  public void warning(SAXParseException e) {
    System.out.println("warning:"+e);
  }

}
