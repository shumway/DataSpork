package dataspork.interp;

import dataspork.tree.DataTreeNode;
import dataspork.gui.InterpreterGUI;
import dataspork.gui.SimulationInfoGUI;

public class SimulationInfo implements Interpreter {

  protected DataTreeNode node;
  protected SimulationInfoGUI gui;

  public SimulationInfo(DataTreeNode node) {
    this.node = node;
  }

  public InterpreterGUI getGUI() {
    if (gui==null) {gui=new SimulationInfoGUI(this);}
    else {gui.show();}
    return gui;
  }

}
