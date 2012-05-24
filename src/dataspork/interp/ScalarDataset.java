package dataspork.interp;
import dataspork.tree.DataTreeNode;
import dataspork.gui.InterpreterGUI;
import dataspork.gui.ScalarDatasetGUI;
import dataspork.stat.ScalarSeries;

public class ScalarDataset extends ScalarSeries implements Interpreter {

  protected DataTreeNode node;
  protected InterpreterGUI gui;

  public ScalarDataset(DataTreeNode node) {
    super((double[])node.getDataSource().getData());
    this.node = node;
  }

  public InterpreterGUI getGUI() {
    if (gui==null) {gui=new ScalarDatasetGUI(this);}
    else {gui.show();}
    return gui;
  }

  public String getName() {
    //    return node.toString();
    return node.getLongName();
  }

}
