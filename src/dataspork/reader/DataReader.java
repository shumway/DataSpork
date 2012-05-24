package dataspork.reader;

import dataspork.tree.DataTreeNode;
import java.net.URL;

public interface DataReader {
  
  public DataTreeNode read(URL url);

}
