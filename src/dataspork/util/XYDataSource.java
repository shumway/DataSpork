package dataspork.util;

public interface XYDataSource {
  public double[] getXData();
  public double[] getYData();
  public double getXMin();
  public double getYMin();
  public double getXMax();
  public double getYMax();
}
