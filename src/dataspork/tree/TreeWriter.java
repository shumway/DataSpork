package dataspork.tree;

import java.util.Enumeration;

/** Class for providing a character representation of a tree.
  * Currently only writes to System.out.
  */
public class TreeWriter {

  /** Recursively print a DataTree to System.out.
    */
  public static void printTree(DataTreeNode node, String indent) {
    System.out.println(indent+node);
    Enumeration a = node.getAttributeLabels();
    while (a.hasMoreElements()) {
      String label=(String)a.nextElement();
      if (!label.equals(DataTreeNode.NAME)) {
        System.out.println(indent+": attribute label: "+label);
      }
    }
    Enumeration e;
    if ( (e=node.children()) != null ) {
      while (e.hasMoreElements()) {
        printTree( (DataTreeNode)e.nextElement(), indent+"  " );
      }
    } else {
      DataSource ds = node.getDataSource();
      String info = ds.TYPENAME[ds.getType()];
      int[] dims = ds.getDims();
      for (int i=0; i<dims.length; i++) {
        info +=  "[" + String.valueOf(dims[i]) + "]";
      }
      System.out.println(indent+": "+info);
    }
  }

}
