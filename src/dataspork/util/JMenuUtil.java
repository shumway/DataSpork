package dataspork.util;
import javax.swing.*;
import java.awt.Event;
import java.awt.event.ActionListener;

public class JMenuUtil {

  public static void addMenu(JMenuBar menuBar, String name, String[] label,
     String[] command, int[] shortcut, ActionListener actionListener) {
    JMenu menu = new JMenu(name);
    for (int i=0; i<label.length; i++) {
      if (label[i].equals("")) {
        menu.addSeparator();
      } else {
        JMenuItem mi = new JMenuItem(label[i]);
        mi.setActionCommand(command[i]);
        if (shortcut[i]!=0) {
          mi.setAccelerator(
            KeyStroke.getKeyStroke(shortcut[i],Event.CTRL_MASK));
        }
        mi.addActionListener(actionListener);
        menu.add(mi);
      }
    }
    menuBar.add(menu);
  }

}
