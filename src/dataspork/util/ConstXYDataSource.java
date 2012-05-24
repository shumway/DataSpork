package dataspork.util;

public class ConstXYDataSource implements XYDataSource{
  protected double[] x;
  protected double[] y;
  protected double xmin,ymin,xmax,ymax;

  public ConstXYDataSource(double[] y) {
    this.y=y;
    xmin=0; xmax=y.length-1;
    ymin=ymax=y[0];
    for (int i=0; i<y.length; ++i) {
      if (y[i]<ymin) ymin=y[i];
      if (y[i]>ymax) ymax=y[i];
    }
  }

  public ConstXYDataSource(double[] x, double[] y) {
    this.x=x;
    this.y=y;
    if (x==null) {
      x=new double[y.length];
      for (int i=0; i<x.length; ++i) x[i]=i;
    }
    xmin=xmax=y[0];
    ymin=ymax=y[0];
    for (int i=0; i<y.length; ++i) {
      if (x[i]<xmin) xmin=x[i];
      if (y[i]<ymin) ymin=y[i];
      if (x[i]>xmax) xmax=x[i];
      if (y[i]>ymax) ymax=y[i];
    }
  }

  public double[] getXData() {return x;}
  public double[] getYData() {return y;}
  public double getXMin() {return xmin;}
  public double getYMin() {return ymin;}
  public double getXMax() {return xmax;}
  public double getYMax() {return ymax;}
}
