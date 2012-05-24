package dataspork.view;
import java.awt.*;
import java.awt.geom.*;
import dataspork.plot.*;
import dataspork.interp.PairDistribution;

/**
  */
public class PairDistributionGraph {

  protected PairDistribution pairDistribution;
  protected CoordRange range;
  protected Plot plot;
  protected PlotBox plotBox;
  protected XYPlot autocorPlot;
  protected Axis xAxis;
  protected Axis yAxis;

  /**
    */
  public PairDistributionGraph(PairDistribution pairDistribution) {
    this.pairDistribution=pairDistribution;
    range = new CoordRange( new AutoXRange(pairDistribution), 
                            new AutoYRange(pairDistribution));
    //
    //Lay out the plot
    //
    plot = new Plot();
    plotBox = new PlotBox(range, new CoordRange(0,0,1,1));
    plot.addElement(plotBox);
    plotBox.addElement( autocorPlot = new XYPlot(pairDistribution) );
    autocorPlot.setPaint(Color.red);
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
