package dataspork.reader;
import dataspork.tree.*;
import java.io.*;

public class AddData {

  protected String[] tabName;
  protected DataTreeNode[] node;
  protected int nTabs;

  public AddData(File[] files, String readerName, ReaderIndex ri) {

    nTabs = 0;
    DataTreeNode[] subnode = new DataTreeNode[files.length];
    node = new DataTreeNode[files.length];
    tabName = new String[files.length];

    node[0] = new DataTreeNode(files[0].getName()+" group");

    // get rKeyList and readerNames from ReaderIndex
    String[] readerNames = new String[ri.getNReaders()];
    String[] rKeyList = ri.getReaderKeyList();

    // get list of data reader names
    for (int i=0;i<ri.getNReaders();i++) {
      readerNames[i] = ri.getReaderName(rKeyList[i]);
    }


    // get the reader class name from ReaderIndex
    String readerClass = null;
    boolean groupable = false;
    for (int i=0;i<ri.getNReaders();i++) {
      if (readerNames[i].equals(readerName)) {
        readerClass = ri.getReaderClass(rKeyList[i]);
        groupable = ri.isReaderGroupable(rKeyList[i]);
      }
    }          
    if (readerClass == null) {
      node[0] = new DataTreeNode("Can't access reader");
      tabName[0] = "error";
      nTabs = 1;
    }
    else {      
      try {        
        // instantiate a reader of that name, and read data from it
        DataReader reader = (DataReader)Class.forName(readerClass).newInstance();
        if (groupable) {
          nTabs = 1;
          for (int j=0;j<files.length;j++) {
            subnode[j] = reader.read(files[j].toURL());
            node[0].add(subnode[j]);
          }
          tabName[0] = files[0].getName()+" (grp)";
          AverageNode avgnode = new AverageNode(node[0]);
          node[0] = avgnode.getAverage();
          for (int j=0;j<files.length;j++) {
            node[0].add(subnode[j]);
          }
        }
        else {
          for (int j=0;j<files.length;j++) {
            nTabs++;
            node[j] = reader.read(files[j].toURL());
            tabName[j] = files[j].getName();
          }
        }
        
      } catch (Exception e) { 
        System.out.println("Exception in AddData: "+e);
      } 
    }

  }
  public int getNTabs() {
    return nTabs;
  }

  public DataTreeNode[] getNodes() {
    return node;
  }

  public String[] getTabNames() {
    return tabName;
  }

}
