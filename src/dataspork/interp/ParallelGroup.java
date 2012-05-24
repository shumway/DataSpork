package dataspork.interp;

import dataspork.tree.*;
import dataspork.gui.InterpreterGUI;
import dataspork.gui.ParallelGroupGUI;
import java.util.Enumeration;

public class ParallelGroup implements Interpreter {

  protected DataTreeNode node;
  protected ParallelGroupGUI gui;
  protected DataTreeNode averageNode;

  public ParallelGroup(DataTreeNode node) {
    this.node = node;
  }

  public InterpreterGUI getGUI() {
    if (gui==null) {gui=new ParallelGroupGUI(this);}
    else {gui.show();}
    return gui;
  }

  public DataTreeNode getAverageNode() {
    if (averageNode==null) {
      averageNode = new DataTreeNode(node.toString()+" (averaged)");
      Enumeration processes = node.children();
      while(processes.hasMoreElements()) {
        DataTreeNode process = (DataTreeNode)processes.nextElement();
        if (!process.hasInterpreter("ScalarGroup")) continue;
        Enumeration scalars =  process.children();
        while(scalars.hasMoreElements()) {
          DataTreeNode scalar = (DataTreeNode)scalars.nextElement();
          if (scalar.getDataSource()==null) break;
          String scalarName = scalar.toString();
          DataTreeNode avgScalar =
                    (DataTreeNode)averageNode.getChild(scalarName);
          if (avgScalar==null) {
            avgScalar = new DataTreeNode(scalarName);
            avgScalar.addInterpreter(
                         new InterpreterClass(ScalarDataset.class,avgScalar));
            avgScalar.setDataSource(new AverageDataSource());
            averageNode.add(avgScalar);
          }
          ((AverageDataSource)avgScalar.getDataSource()).addDataNode(scalar);
        }
      }
    }
    return averageNode;
  }

}
