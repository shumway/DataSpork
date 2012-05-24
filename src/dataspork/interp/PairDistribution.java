package dataspork.interp;
import dataspork.tree.DataTreeNode;
import dataspork.gui.InterpreterGUI;
import dataspork.gui.PairDistributionGUI;
import dataspork.util.ConstXYDataSource;

public class PairDistribution extends ConstXYDataSource implements Interpreter {

  protected DataTreeNode node;
  protected InterpreterGUI gui;

  public PairDistribution(DataTreeNode node) {
    super(node.getSibling("r-grid")!=null?
          (double[])node.getSibling("r-grid").getDataSource().getData():null,
          (double[])node.getDataSource().getData());
    this.node = node;
  }

  public InterpreterGUI getGUI() {
    if (gui==null) {gui=new PairDistributionGUI(this);}
    else {gui.show();}
    return gui;
  }

  public String getName() {
    return node.toString();
  }
}
