package dataspork.tree;
import java.util.Enumeration;
import dataspork.interp.*;

public class AverageNode {

  protected DataTreeNode node;
  protected DataTreeNode averageNode;

  public AverageNode(DataTreeNode node) {
    this.node = node;
  }

  public DataTreeNode getAverage() {
    String nodeName = node.toString();
    if (averageNode==null) {
      averageNode = new DataTreeNode(node.toString()+" (averaged)");
      Enumeration processes = node.children();
      while(processes.hasMoreElements()) {
        DataTreeNode process = (DataTreeNode)processes.nextElement();
        Enumeration scalars =  process.children();
        while(scalars.hasMoreElements()) {
          DataTreeNode scalar = (DataTreeNode)scalars.nextElement();
          if (scalar.getDataSource()==null) break;
          String scalarName = scalar.toString();
          DataTreeNode avgScalar =
            (DataTreeNode)averageNode.getChild(scalarName);
          if (avgScalar==null) {
            avgScalar = new DataTreeNode(scalarName);
            avgScalar.setLongName(scalarName+" ("+nodeName+")");
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
