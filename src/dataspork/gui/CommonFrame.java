package dataspork.gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CommonFrame extends JFrame {

  protected Container contentPane;
  protected JMenuBar menuBar;

  public static final Border BORDER = BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(5,5,5,5),
        BorderFactory.createBevelBorder(BevelBorder.LOWERED));

  public CommonFrame() {
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    contentPane=getContentPane();
  }
}
