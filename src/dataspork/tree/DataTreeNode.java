package dataspork.tree;

import dataspork.interp.InterpreterClass;
import java.util.*;
import javax.swing.tree.*;

/**
 * Class for building the raw data tree.
 */
public class DataTreeNode extends DefaultMutableTreeNode {
  protected Hashtable attributes;
  protected DataSource source;
  protected Vector interpreterClasses;

  final static public String NAME = "NAME";
  final static public String UNITS = "UNITS";
  final static public String ICLASS = "ICLASS";
  final static public String LONGNAME = "LONGNAME";

  /** 
   * Constructor for a composite node. 
   */
  public DataTreeNode(String name) {
    this.source = null;
    attributes = new Hashtable();
    attributes.put(NAME,name);
    attributes.put(LONGNAME,name);
    interpreterClasses = new Vector();
  }

  /** 
   * Constructor for a leaf. 
   */
  public DataTreeNode(String name, DataSource source) {
    this.source = source;
    attributes = new Hashtable();
    attributes.put(NAME,name);
    attributes.put(LONGNAME,name);
    interpreterClasses = new Vector();
  }

  /**
   * return a DataSource object.
   *
   * Thus the tree structure is generic with specific
   * data source attached to leaf node.
   * The tree does not know how to handle particular
   * data source. The tree only serves the purpose of
   * organizing data in a hierarchical structure.
   * All the functionalities needed to handle a data
   * source is defined in DataSource interface.
   */
  public DataSource getDataSource() {
    return (source==null) ? null : source;
  }

  public void addAttribute(String key, String value) {
    attributes.put(key,value);
  }

  public String getAttribute(String key) {
    return (String)attributes.get(key);
  }

  public Enumeration getAttributeLabels() {
    return attributes.keys();
  }

  public void removeAttribute(String key) {
    attributes.remove(key);
  }

  public void setAttribute(String key, String value) {
    attributes.remove(key);
    attributes.put(key,value);
  }

  public synchronized void setName(String name) {
    attributes.remove(NAME);
    attributes.put(NAME,name);
  }

  public synchronized void setLongName(String name) {
    attributes.remove(LONGNAME);
    attributes.put(LONGNAME,name);
  }

  public String getLongName() {
    return (String)attributes.get(LONGNAME);
  }

  public void setDataSource(DataSource source) {
    this.source=source;
  }

  public String toString() {
    return (String)attributes.get(NAME);
  }

  public void addInterpreter(InterpreterClass i) {
    interpreterClasses.addElement(i);
  }

  public Enumeration getInterpreters() {
    return interpreterClasses.elements();
  }

  public boolean hasInterpreter(String name) {
    Enumeration e=interpreterClasses.elements();
    while (e.hasMoreElements()) {
      if (e.nextElement().toString().equals(name)) return true;
    }
    return false;
  }

  public DataTreeNode getSibling(String siblingName) {
    Object node=null;
    Enumeration e=getParent().children(); 
    while (e.hasMoreElements()) {
      if (siblingName.equals((node=e.nextElement()).toString())) {
        return (DataTreeNode)node;
      }
    }
    return null;
  }

  public DataTreeNode getChild(String childName) {
    Object node=null;
    Enumeration e=children(); 
    while (e.hasMoreElements()) {
      if (childName.equals((node=e.nextElement()).toString())) {
        return (DataTreeNode)node;
      }
    }
    return null;
  }

}
