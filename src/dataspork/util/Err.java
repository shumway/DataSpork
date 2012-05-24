package dataspork.util;

public class Err extends Error{

  public static final int DIM_MISMATCH = 0;
  public static final int TYPE_UNKNOWN = 1;

  public static final String[] NAMES = {
         "Dimensions don't agree",
         "Data type unknown"
  };

  public  Err(int i) {
    super(NAMES[i]);
  }
}
