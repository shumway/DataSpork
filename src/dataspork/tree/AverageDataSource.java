package dataspork.tree;
import dataspork.util.Err;
import java.util.*;

/**
  *
  */
public class AverageDataSource implements DataSource {

  /** The packed array data. */
  protected Object data;
  /** The unpacked dimensions of the stored array. */
  protected int[] dims;
  /** The numerical type of the stored array.
    * Either DataSource.DOUBLE or DataSource.INT.
    */
  protected int type;

  protected List sourceNodeList;

  /** Constructor.
    */
  public AverageDataSource() {
    sourceNodeList = new LinkedList();
  }

  /**
    */
  public void addDataNode(DataTreeNode node) {
    DataSource source = node.getDataSource();
    if (dims==null) {
      dims=new int[1];
      dims[0]=source.getDims()[0];
      type=source.getType();
    } else {
      int[] newDims=source.getDims();
      if (dims[0]<newDims[0]) dims[0]=newDims[0];
    }
    sourceNodeList.add(node);
  }

  /** Return the data array as an object. */
  public Object getData() {
    if (data==null) {
      if (type==DOUBLE) {
        double[] data=new double[dims[0]];
        double[] wt=new double[dims[0]];
        Iterator iNode = sourceNodeList.listIterator();
        while (iNode.hasNext()) {
          DataTreeNode node=((DataTreeNode)iNode.next());
          double[] temp = (double[])node.getDataSource().getData();
          for (int i=0; i<temp.length; ++i) {
            data[i]+=temp[i];
            wt[i]+=1;
          }
        }
        for (int i=0; i<data.length; ++i) data[i]/=wt[i];
        this.data=data;
      } else
      if (type==INT) {
        data=new int[dims[0]];
      }
    }
    return data;
  }

  /** Return the unpacked dimensions of the data array. */
  public int[] getDims() {
    return dims;
  }

  /** Return the numerical type of the data array.
    * Returns either DataSource.DOUBLE or DataSource.INT.
    */
  public int getType() {
    return type;
  }
}
