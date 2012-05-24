package dataspork.gui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import dataspork.interp.StructureFactor;
import dataspork.view.*;
import dataspork.plot.*;
import dataspork.stat.*;

public class StructureFactorGUI extends JFrame implements InterpreterGUI {
  protected StructureFactor structureFactor;

  protected StructureFactorGraph graph;
  protected PlotPanel graphPanel;

  public StructureFactorGUI(StructureFactor structureFactor) {
    this.structureFactor=structureFactor;

    setTitle(structureFactor.getName());
    Container contentPane = getContentPane();
    contentPane.setBackground(Color.white);

    graph = new StructureFactorGraph(structureFactor);
    graphPanel = new PlotPanel(graph.getPlot());
    graphPanel.setPreferredSize(new Dimension(500,300));
    graphPanel.setBackground(Color.white);
    graphPanel.setBorder(new EmptyBorder(10,50,30,10));
    graphPanel.setBorder(new TitledBorder( new EmptyBorder(10,50,30,10),
               "Structure Factor s(k)",
               TitledBorder.CENTER,TitledBorder.BELOW_TOP));
    contentPane.add(graphPanel,BorderLayout.CENTER);

    pack();
    show();
  }

}
