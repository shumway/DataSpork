package dataspork.stat;
import dataspork.util.DataListener;
import dataspork.util.XYDataSource;

public class Autocorr implements DataListener, XYDataSource {

  protected ScalarSeries series;
  protected double[] auto;
  protected double corrTime;
  protected double min, max;

  public Autocorr(ScalarSeries series) {
    this.series=series;
    calcAutocorr();
    series.addListener(this);
  }

  protected void calcAutocorr() {
    min=max=1;
    int imin=(int)series.getWindow().getLow();
    int imax=(int)series.getWindow().getHigh();
    double[] data=series.getData();
    double mean=series.getMean();
    double var=series.getVarience();
    int n=imax-imin+1;
    int cutoff = n;
    if (auto==null || auto.length<cutoff) auto = new double[cutoff];
    for (int j=0; j<cutoff; j++) {
      auto[j] = 0d;
      for (int i=imin; i+j<=imax; i++) {
        auto[j]=auto[j]+(data[i]-mean)*(data[i+j]-mean);
      }
      auto[j]=auto[j]/(var*(n-j));
      if ( auto[j] < Math.sqrt( 2d/(double)(n-j) ) ) {
        cutoff = Math.min(cutoff,5*j);
      }
      if (auto[j]>max) max=auto[j];
      if (auto[j]<min) min=auto[j];
    }
    //Now copy into smaller array, if necessary.
    if (auto.length!=cutoff) {
      double temp[] = auto;
      auto = new double[cutoff];
      for (int j=0; j<cutoff; j++) auto[j]=temp[j];
    }
    //Calculate the autocorrelation time.
    if (auto.length==0) {
      corrTime=1d;
    } else {
      corrTime=auto[0];
      for (int j=1; j*5<2*cutoff; j++) corrTime += 2d*auto[j]; 
    }
    series.setAutocor(corrTime);
    series.dataChanged(this);
  }

  public void dataChanged(Object o) {calcAutocorr();}

  public double[] getXData() {return null;}

  public double[] getYData() {return auto;}

  public double getXMin() {return 0;}
  public double getXMax() {return auto.length-1;}
  public double getYMin() {return min;}
  public double getYMax() {return max;}

  public ScalarSeries getDataset() {return series;}
}
