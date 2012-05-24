package dataspork.view;
import java.awt.*;
import java.awt.geom.*;
import dataspork.plot.*;
import dataspork.stat.Blocker;
import dataspork.util.DoubleSource;

/**
  */
public class BlockingGraph {

  protected CoordRange range;
  protected Plot plot;
  protected PlotBox plotBox;
  protected XYPlot blockingPlot;
  protected Line errLine;
  protected Blocker blocker;
  protected Axis xAxis;
  protected Axis yAxis;
  public final DoubleSource ERROR_SOURCE = new ErrorSource();


  /**
    */
  public BlockingGraph(Blocker blocker) {
    this.blocker=blocker;
    double[] err = blocker.getBlockedError();
    range = new CoordRange( new AutoXRange(blocker), 
                            new BlockerYRange());
    //
    //Lay out the plot
    //
    plot = new Plot();
    plotBox = new PlotBox(range, new CoordRange(0,0,1,1));
    plot.addElement(plotBox);
    plotBox.addElement( blockingPlot = new XYPlot(blocker) );
    //blockingPlot.setPaint(Color.red);
    //blockingPlot.setStroke(new PlotStroke(2f,1));
    plotBox.addElement(errLine=new Line(ERROR_SOURCE,Line.HORIZONTAL));
    errLine.setPaint(Color.green);
    plotBox.addElement( new PlotFrame() );
    plotBox.addElement(xAxis = new Axis(Axis.HORIZONTAL));
    plotBox.addElement(yAxis = new Axis(Axis.VERTICAL));
  }

  /**
    */
  public Plot getPlot() {
    return plot;
  }

  public class ErrorSource implements DoubleSource {
    public double getValue(){return blocker.getDataset().getErrorOfMean();}
  }

  public class BlockerYRange implements Range {
    public double getLow() {return 0;}
    public double getHigh() {
      double amax=ERROR_SOURCE.getValue();
      double bmax=blocker.getYMax();
      return amax>bmax ? 1.1*amax : 1.1*bmax;
    }
    public double length() {
      double amax=ERROR_SOURCE.getValue();
      double bmax=blocker.getYMax();
      return amax>bmax ? 1.1*amax : 1.1*bmax;
    }
  }

}
