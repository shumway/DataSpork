package dataspork.interp;

import dataspork.tree.DataTreeNode;
import dataspork.gui.InterpreterGUI;
import dataspork.gui.ScalarGroupGUI;

public class ScalarGroup implements Interpreter {

  protected DataTreeNode node;
  protected ScalarGroupGUI gui;

  public ScalarGroup(DataTreeNode node) {
    this.node = node;
  }

  public InterpreterGUI getGUI() {
    if (gui==null) {gui=new ScalarGroupGUI(this);}
    else {gui.show();}
    return gui;
  }

}
