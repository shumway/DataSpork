package dataspork.gui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import dataspork.interp.ScalarGroup;
import dataspork.view.*;
import dataspork.plot.*;
import dataspork.stat.*;

public class ScalarGroupGUI extends JFrame implements InterpreterGUI {
  protected ScalarGroup dataset;

  protected JTextArea textArea;

  public ScalarGroupGUI(ScalarGroup dataset) {
    this.dataset=dataset;

    setTitle("ScalarGroup Window");
    Container contentPane = getContentPane();
    contentPane.setBackground(Color.white);

    contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));

    String infoString="Not implemented yet";
    textArea = new JTextArea(infoString,25,80);
    textArea.setFont(new Font("Monospaced",Font.PLAIN,12));
    contentPane.add(textArea);

    pack();
    show();
  }
}
