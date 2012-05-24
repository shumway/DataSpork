package dataspork.stat;
import dataspork.util.DoubleWindowModel;
import dataspork.util.DataListener;
import java.util.EventListener;
import javax.swing.event.EventListenerList;

/** Class for representing time series data.  Calculates mean and varience,
  * and allows for imin and imax cutoffs.
  */
public class ScalarSeries implements DataListener{

  /** The mean. */
  protected double mean=0d;

  /** The varience. */
  protected double var=0d;

  /** The standard deviation. */
  protected double sigma=0d;

  /** The autocorrelation time. */
  protected double autocor=1d;

  /** The cutoff window for calculating statistics. */
  protected DoubleWindowModel window;

  /** The time series data */
  public double data[];

  /** Data listeners for this object */
  protected EventListenerList listenerList;
  
  /** Create a ScalarSeries object that refers to the given array.
    */
  public ScalarSeries(double[] data) {
    this.data = data;
    window = new DoubleWindowModel(0,data.length-1,1);
    listenerList = new EventListenerList();
    calcMean();
    window.addListener(this);
  }

  /** Set the autocorrelation time.  Used for calculating the error
    * of the average.
    */ 
  public void setAutocor(double time) {
    autocor=(time>1)?time:1;
  }
  
  /** Return the data.
    */
  public double[] getData() {
    return data;
  }

  /** Return the mean.
    */
  public double getMean() {
    return mean;
  }

  /** Return the error of the mean.
    */
  public double getErrorOfMean() {
    return sigma*Math.sqrt(autocor/(window.getHigh()-window.getLow()));
  }

  /** Return the varience.
    */
  public double getVarience() {
    return var;
  }

  /** Return the standard deviation.
    */
  public double getSigma() {
    return sigma;
  }

  /** Return the autocorrelation time being used for estimating
    * the error of the mean.  This value is not calculated by
    * this class, but can be set using setCorrTime.
    */
  public double getAutoCorr() {
    return autocor;
  }

  /** Return the model for the datarange.  */
  public DoubleWindowModel getWindow() {
    return window;
  }

  protected void calcMean() {
    mean = 0d;
    var = 0d;
    int imin = (int)window.getLow();
    int imax = (int)window.getHigh();
    int n=imax-imin+1;
    for (int i=imin; i<=imax; i++) {
      mean+=data[i];
      var+=data[i]*data[i];
    }
    mean/=n;
    var = var/n - mean*mean;
    sigma = Math.sqrt(var);
  }

  public void addListener(DataListener e) {
    listenerList.add(DataListener.class,e);
  }

  protected void fireListeners(Object o) {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (o!=listeners[i+1]) {
        ((DataListener)listeners[i+1]).dataChanged(this);
      }
    }
  }

  public void dataChanged(Object o) {
    calcMean();
    fireListeners(o);
  }

}
