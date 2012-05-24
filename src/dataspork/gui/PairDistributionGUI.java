package dataspork.gui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import dataspork.interp.PairDistribution;
import dataspork.view.*;
import dataspork.plot.*;
import dataspork.stat.*;

public class PairDistributionGUI extends CommonFrame 
                                 implements InterpreterGUI {
  protected PairDistribution pairDistribution;

  protected PairDistributionGraph graph;
  protected PlotPanel graphPanel;

  public PairDistributionGUI(PairDistribution pairDistribution) {
    this.pairDistribution=pairDistribution;

    setTitle(pairDistribution.getName());

    graph = new PairDistributionGraph(pairDistribution);
    graphPanel = new PlotPanel(graph.getPlot());
    graphPanel.setPreferredSize(new Dimension(500,300));
    graphPanel.setBackground(Color.white);
    graphPanel.setBorder(
           BorderFactory.createTitledBorder( 
               BorderFactory.createMatteBorder(10,50,30,10,Color.white),
               "Pair Correlation Function g(r)",
               TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    contentPane.add(graphPanel,BorderLayout.CENTER);

    pack();
    show();
  }

}
