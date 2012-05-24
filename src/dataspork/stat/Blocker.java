package dataspork.stat;
import dataspork.util.DataListener;
import dataspork.util.XYDataSource;

public class Blocker implements DataListener, XYDataSource {

  protected ScalarSeries series;
  protected double[] error;
  protected double min,max;

  public Blocker(ScalarSeries series) {
    this.series=series;
    calcBlockedError();
    series.addListener(this);
  }

  protected void calcBlockedError() {
    int imin=(int)series.getWindow().getLow();
    int imax=(int)series.getWindow().getHigh();
    double[] temp1 = new double[imax-imin+1];
    for (int i=0; i<temp1.length; ++i) temp1[i]=series.data[i+imin];
    int nblock=(int)Math.floor(Math.log((double)(imax-imin+1))
                /Math.log(2d))-2;
    if (nblock<1) nblock=1;
    error = new double[nblock];
    min=max=error[0]=Math.sqrt(series.getVarience()/temp1.length);
    for (int iblock=1; iblock<nblock; ++iblock) {
      double[] temp2 = new double[temp1.length/2];
      int j=0;
      for (int i=0; i<temp2.length; ++i) {
        temp2[i]=0.5*(temp1[j++]+temp1[j++]);
      }
      temp1=temp2;
      ScalarSeries temp=new ScalarSeries(temp1);
      error[iblock]=temp.getErrorOfMean();
      if (error[iblock]>max) max=error[iblock];
      if (error[iblock]<min) min=error[iblock];
    }
  }

  public double[] getBlockedError() {
    return error;
  }

  public void dataChanged(Object o) {
    calcBlockedError();
  }

  public double[] getXData() {
    return null;
  }

  public double[] getYData() {
    return error;
  }

  public ScalarSeries getDataset() {
    return series;
  }

  public double getXMin() {return 0;}
  public double getXMax() {return error.length-1;}
  public double getYMin() {return min;}
  public double getYMax() {return max;}
}
