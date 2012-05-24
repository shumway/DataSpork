package dataspork.view;
import java.awt.*;
import java.awt.geom.*;
import dataspork.plot.*;
import dataspork.stat.Autocorr;
import dataspork.util.DoubleSource;

/**
  */
public class AutocorGraph {

  protected Autocorr autocorr;
  protected double[] data;
  protected CoordRange range;
  protected CoordRange histRange;
  protected Plot plot;
  protected Line zeroLine;
  protected PlotBox plotBox;
  protected XYPlot autocorPlot;
  protected Axis xAxis;
  protected Axis yAxis;

  /**
    */
  public AutocorGraph(Autocorr autocorr) {
    this.autocorr=autocorr;
    this.data = autocorr.getYData();
    //range = new CoordRange(0,-5,(double)(data.length-1),5);
    range = new CoordRange( new AutoXRange(autocorr), 
                            new AutoYRange(autocorr));
    //
    //Lay out the plot
    //
    plot = new Plot();
    plotBox = new PlotBox(range, new CoordRange(0,0,1,1));
    plot.addElement(plotBox);
    plotBox.addElement(zeroLine=new Line(DoubleSource.Const.ZERO,
                                         Line.HORIZONTAL));
    plotBox.addElement( autocorPlot = new XYPlot(autocorr) );
    autocorPlot.setPaint(Color.green);
    plotBox.addElement(xAxis = new Axis(Axis.HORIZONTAL));
    plotBox.addElement(yAxis = new Axis(Axis.VERTICAL));
    plotBox.addElement( new PlotFrame() );
  }


  /**
    */
  public Plot getPlot() {
    return plot;
  }
}
