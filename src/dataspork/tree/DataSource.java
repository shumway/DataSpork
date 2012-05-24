package dataspork.tree;

/** Interface for classes that can supply tensor data.
  */
public interface DataSource {
  /** Class variable to indicate a tensor of type double. */
  public static final int DOUBLE=0;
  /** Class variable to indicate a tensor of type int. */
  public static final int INT=1;
  /** Class variable for string representation of type names. */
  public static final String[] TYPENAME={"double","int"};
 
  /** Return the tensor data as an object.
    * The information from getType may be used to cast this back to a
    * packed 1D array, and the information from getDims may be used
    * to interpret the 1D array as a multidimensional tensor.
    */
  public Object getData();

  /** Return the dimensions of the tensor. */
  public int[] getDims();

  /** Return the type of the tensor values.
    * Return value is either DataSource.DOUBLE or DataSource.INT.
    */
  public int getType();
}
