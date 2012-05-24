package dataspork.stat;
import dataspork.util.DataListener;
import dataspork.util.XYDataSource;

public class Histogram implements DataListener, XYDataSource {

  protected ScalarSeries series;
  protected double[] x;
  protected double[] hist;
  protected double min,max,maxCount;
  protected int nbins;

  public Histogram(ScalarSeries series, int nbins) {
    this.series=series;
    this.nbins=nbins;
    x = new double[nbins];
    hist = new double[nbins];
    calcHistogram();
    series.addListener(this);
  }

  protected void calcHistogram() {
    int imin=(int)series.getWindow().getLow();
    int imax=(int)series.getWindow().getHigh();
    double[] data = series.getData();
    //First find max and min.
    min=max=data[imin]; maxCount=0;
    for (int i=imin+1; i<=imax; ++i) {
      if (data[i]<min) min=data[i];
      if (data[i]>max) max=data[i];
    }
    if (max==min) {
      max=data[imin]+1;
      min=data[imin]-1;
    }
    //Then set up bins and sample.
    double dx=(max-min)/(nbins-1);
    x[0]=min; hist[0]=0;
    for (int i=1; i<nbins; ++i) {
      x[i]=x[i-1]+dx;
      hist[i]=0;
    }
    for (int i=imin; i<=imax; ++i) {
      int ibin=(int)Math.floor((data[i]-min)/dx);
      ++hist[ibin];
      if ( hist[ibin]>maxCount) maxCount=hist[ibin];
    }
  }

  public void dataChanged(Object o) {
    calcHistogram();
  }

  public double[] getXData() {
    return x;
  }

  public double[] getYData() {
    return hist;
  }

  public ScalarSeries getDataset() {
    return series;
  }

  public double getXMin() {return min;}
  public double getXMax() {return max;}
  public double getYMin() {return 0;}
  public double getYMax() {return maxCount;}
}
