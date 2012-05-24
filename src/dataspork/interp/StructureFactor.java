package dataspork.interp;
import dataspork.tree.DataTreeNode;
import dataspork.gui.InterpreterGUI;
import dataspork.gui.StructureFactorGUI;
import dataspork.util.ConstXYDataSource;

public class StructureFactor extends ConstXYDataSource implements Interpreter {

  protected DataTreeNode node;
  protected InterpreterGUI gui;

  public StructureFactor(DataTreeNode node) {
    super((double[])node.getSibling("k-grid").getDataSource().getData(),
          (double[])node.getDataSource().getData());
    this.node = node;
  }

  public InterpreterGUI getGUI() {
    if (gui==null) {gui=new StructureFactorGUI(this);}
    else {gui.show();}
    return gui;
  }

  public String getName() {
    return node.toString();
  }
}
