package dataspork.util;

import javax.swing.event.EventListenerList;
import java.util.EventListener;


/** Model for storing a range low, high, with constraints.
  * Require that high - low is greater than or equal the window size width.
  * Also constrains with an min and max.
  */
public class DoubleWindowModel {
  
  protected double low, high;
  protected double min, max;
  protected double width;
  protected EventListenerList listenerList;

  public DoubleWindowModel(double min, double max, double width) {
    low=min;
    high=max;
    this.min=min;
    this.max=max;
    this.width=width;
    listenerList = new EventListenerList();
  }

  /** Return the lower limit of the current range. */
  public double getLow() {
    return low;
  }

  /** Return the upper limit of the current range. */
  public double getHigh() {
    return high;
  }

  /** Attempt to change the lower value. */
  public void setLow(double lo) {
    if      (lo<min)        {lo=min;}
    else if (lo>high-width) {lo=high-width;}
    if (lo!=low) {
      low=lo;
      fireListeners();
    }
  }

  /** Attempt to change the upper value. */
  public void setHigh(double hi) {
    if      (hi>max)       {hi=max;}
    else if (hi<low+width) {hi=low+width;}
    if (hi!=high) {
      high=hi;
      fireListeners();
    }
  }

  public void addListener(DataListener e) {
    listenerList.add(DataListener.class,e);
  }

  protected void fireListeners() {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      ((DataListener)listeners[i+1]).dataChanged(this);
    }
  }
}
