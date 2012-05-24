package dataspork.interp;

import dataspork.tree.DataTreeNode;
import java.lang.reflect.Constructor;

public class InterpreterClass { 

  protected Class c;
  protected String name;
  protected DataTreeNode node;
  protected Interpreter interpreter;

  public InterpreterClass(Class c, DataTreeNode node) {
    this.c = c;
    name = c.getName();
    int i = name.lastIndexOf('.');
    name = name.substring(++i);
    this.node = node;
  }

  public String toString() {
    return name;
  }

  /** Returns the instance of the interpreter for this object.
    */
  public Interpreter getInstance() {
    if (interpreter==null) {
      try {
        Constructor constructor =
          c.getConstructor(new Class[] {DataTreeNode.class});
        interpreter=(Interpreter)constructor.newInstance(new Object[] {node});
      }
      catch (Exception e) {} 
    }
    return interpreter;
  }
}
