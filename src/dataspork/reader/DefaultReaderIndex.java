package dataspork.reader;
import java.util.*;
// import dataspork.reader.*;

/*
Purpose:  To allow a user to add/remove the file filters and data
readers used by DataSpork.  

Usage: Copy this file to a user directory (e.g. ~/myreaders), and add
that directory to your CLASSPATH.  (It must be -->BEFORE<-- the
DataSpork directories in the CLASSPATH!)  Name this file whatever you
want, but be sure to change the class name and constructor name below
("DefaultReaderIndex") to match the filename.  Put that filename in
your .dataspork file as the primary ReaderIndex to use.  

You also have to remove the line "package dataspork.reader;" above,
and uncomment the line "import dataspork.reader.*;".

Syntax:  

Add a key to the readerKeyList that corresponds to the reader you're adding.

readerClass points to the reader class, in the CLASSPATH.

  Example:  you wrote HTMLReader.java, and put it in ~/myreaders/HTML
    Your CLASSPATH = ~/myreaders:(DataSpork directory): (etc) 
  --> you would write
    readerClass.put(key,"HTML.HTMLReader")

readerName is the string you want displayed on the FileChooser GUI,
on the reader list combo box.

readerFilterKey tells the GUI to automatically switch to this
FileFilter whenever a user chooses this reader.

readerGroupable tells DataSpork whether or not to average multiple
file selections together as parallel clones.

*/


public class DefaultReaderIndex implements ReaderIndex {
  protected Hashtable readerClass = new Hashtable();
  protected Hashtable readerName = new Hashtable();
  protected Hashtable readerFilterKey = new Hashtable();
  protected Hashtable readerGroupable = new Hashtable();

  protected Hashtable filterName = new Hashtable();
  protected Hashtable filterClass = new Hashtable();

  // Here's where you add readers to DataSpork

  protected String[] readerKeyList = {"asciidata", "asciiclone"};
  protected String[] filterKeyList = {"dat"};

  protected int nReaders = readerKeyList.length;
  protected int nFilters = filterKeyList.length;

  public DefaultReaderIndex() {

    String key;

    // AsciiDataReader
    key = "asciidata";
    readerClass.put(key,"dataspork.reader.AsciiDataReader");
    readerName.put(key,"AsciiDataReader");
    readerFilterKey.put(key,"dat");
    readerGroupable.put(key,"false");

    // AsciiCloneReader
    key = "asciiclone";
    readerClass.put(key,"dataspork.reader.AsciiDataReader");
    readerName.put(key,"AsciiCloneReader");
    readerFilterKey.put(key,"dat");
    readerGroupable.put(key,"true");

    // .dat filter
    key = "dat";
    filterClass.put(key,"dataspork.reader.AsciiFilter");
    filterName.put(key,"Text data files (*.dat)");
  }

  public String getReaderClass(String key) {
    return (String)readerClass.get(key);
  }
  public String getReaderName(String key) {
    return (String)readerName.get(key);
  }
  public boolean isReaderGroupable(String key) {
    String groupable = (String)readerGroupable.get(key);
    if (groupable.equals("true")) return true;
    return false;
  }
  public String getReaderFilterKey(String key) {
    return (String)readerFilterKey.get(key);
  }
  public String getFilterClass(String key) {
    return (String)filterClass.get(key);
  }
  public String getFilterName(String key) {
    return (String)filterName.get(key);
  }

  public String getReaderClassKey(String name) {
    for (int i=0;i<nReaders;i++) {
      if (name.equals((String)readerClass.get(readerKeyList[i]))) {
        return readerKeyList[i]; 
      }
    }
    return null;
  }
  public String getReaderNameKey(String name) {
    for (int i=0;i<nReaders;i++) {
      if (name.equals((String)readerName.get(readerKeyList[i]))) {
        return readerKeyList[i];
      }
    }
    return null;
  }

  public String[] getReaderKeyList() {
    return readerKeyList;
  }

  public String[] getFilterKeyList() {
    return filterKeyList;
  }

  public int getNReaders() {
    return nReaders;
  }

  public int getNFilters() {
    return nFilters;
  }

}
