package dataspork;
import dataspork.tree.DataTreeNode;
import dataspork.reader.*;
import dataspork.gui.MainGUI;
import java.io.*;
import java.net.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class Main {

  /**The data. */
  protected DataTreeNode dataTree;
  public ReaderIndex ri;
  public String[] fileList;
  public String[] readerList;
  public int[] groupList;

  public Main(String[] files) { 
    // read parameters from .dataspork file
    String s = System.getProperty("user.home", "not specified");
    System.out.println("Reading .dataspork file from directory: "+s);  

    // ReaderIndex gives configuration information for the data readers
    ri = new DefaultReaderIndex();
    String readerIndexName = "DefaultReaderIndex";
    /* HERE'S WHERE WE'LL PUT CHOOSABLE READERINDEX, FROM .DATASPORK
    try {
      readerIndexName = "UserReaderIndex";  // get from .dataspork
      ri = (ReaderIndex)Class.forName(readerIndexName).newInstance();
    } 
    catch (Exception e) {
      ri = new DefaultReaderIndex();
    }
    */

    // print out summary of ReaderIndex information
    System.out.println("Using "+readerIndexName+" ("+ri.getNReaders()+" readers, "+ri.getNFilters()+" file filters)");
    String[] rKeyList = ri.getReaderKeyList();
    for (int i=0;i<rKeyList.length;i++) {
      String rName = ri.getReaderName(rKeyList[i]);
      String rClass = ri.getReaderClass(rKeyList[i]);
      String fKey = ri.getReaderFilterKey(rKeyList[i]);
      String fClass = ri.getFilterClass(fKey);
      System.out.println("   "+rName+" ("+rClass+"),");
      System.out.println("      linked to filter "+fClass);
    }

    MainGUI m = new MainGUI(this);

    // read .xml file, if specified
    for (int i=0;i<files.length;i++) {
      File f = new File(files[i]);
      if (!f.exists()) f = new File(files[i]+".xml");
      if (f.exists()) {
        System.out.println("Opening file "+f.toString()+"...");
        URL url = null;
        try {url = f.toURL();}
        catch (Exception e) {}
        try {
          XmlDocHandler handler = new XmlDocHandler(url);
          SAXParserFactory factory = SAXParserFactory.newInstance();
          SAXParser parser = factory.newSAXParser();
          parser.parse(url.toString(),handler);
          m.fileList = handler.getFileList();
          m.readerList = handler.getReaderList();
          m.groupList = handler.getGroupList();
          m.AddXmlItems();
        }
        catch (Throwable t) { t.printStackTrace(); }
      }
    }

  };

  public void quit() {
    System.exit(0);
  }

  public static void main(String[] args) {
    System.out.println("DataSpork 1.1 - Data Analysis Program\n");
    Main m = new Main(args);
  }
}
