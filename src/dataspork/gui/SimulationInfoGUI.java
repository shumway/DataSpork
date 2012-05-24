package dataspork.gui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import dataspork.interp.SimulationInfo;
import dataspork.view.*;
import dataspork.plot.*;
import dataspork.stat.*;

public class SimulationInfoGUI extends JFrame implements InterpreterGUI {
  protected SimulationInfo dataset;

  protected JTextArea textArea;

  public SimulationInfoGUI(SimulationInfo dataset) {
    this.dataset=dataset;

    setTitle("SimulationInfo Window");
    Container contentPane = getContentPane();
    contentPane.setBackground(Color.white);

    contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));

    String infoString="     LABEL  ex003\n   UNITS  H      a0\n" +
        "   BOXSIZE  .3083631260E+02  .3083631260E+02  .3083631260E+02\n"+
        "     ENORM  .7000000000E+01\n    BETA  .5000000000E+01\n";
    textArea = new JTextArea(infoString,25,80);
    textArea.setFont(new Font("Monospaced",Font.PLAIN,12));
    contentPane.add(textArea);

    pack();
    show();
  }
}
