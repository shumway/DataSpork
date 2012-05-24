package dataspork.tree;
import dataspork.util.Err;

/** Simple implementation of DataSource for tensors that are
  * already stored as packed 1D arrays.
  */
public class ConstDataSource implements DataSource {

  /** The packed array data. */
  private Object data;
  /** The unpacked dimensions of the stored array. */
  private int[] dims;
  /** The numerical type of the stored array.
    * Either DataSource.DOUBLE or DataSource.INT.
    */
  private int type;

  /** Constructor.
    */
  public ConstDataSource(Object data, int dims[], int type) {
    //Check the consistancy of the arguments.
    if (type==DataSource.DOUBLE) {
      int size=1;
      for(int i=0; i<dims.length; i++) size*=dims[i];
      if ( ((double[])data).length != size )throw new Err(Err.DIM_MISMATCH);
    } else if (type==DataSource.INT) {
      int size=1;
      for(int i=0; i<dims.length; i++) size*=dims[i];
      if ( ((double[])data).length != size )throw new Err(Err.DIM_MISMATCH);
    } else {
      throw new Err(Err.TYPE_UNKNOWN);
    }
    //OK
    this.data = data;
    this.dims = dims;
    this.type = type;
  }

  /** Return the data array as an object. */
  public Object getData() {
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
