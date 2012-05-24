package dataspork.gui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import dataspork.interp.ParallelGroup;
import dataspork.tree.*;
import dataspork.util.JMenuUtil;

public class ParallelGroupGUI extends CommonFrame 
    implements InterpreterGUI, ActionListener {
  protected ParallelGroup group;
  protected DataTreeNode averageNode;
  protected DataTreeGUI averageTree;
  protected JScrollPane treeScroller;

  public ParallelGroupGUI(ParallelGroup group) {
    this.group=group;

    setTitle("ParallelGroup Window");
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
                                        "close"));
      }
    });

    //Set up the menus.
    JMenuUtil.addMenu( menuBar, "File",
      new String[] {"Close"},
      new String[] {"close"},
      new int[]    {KeyEvent.VK_W},
      this
    );

    averageNode=group.getAverageNode();
    averageTree = new DataTreeGUI(averageNode);
    treeScroller = new JScrollPane(averageTree);
    treeScroller.setPreferredSize(new Dimension(400,410));
    treeScroller.setBorder(CommonFrame.BORDER);
    contentPane.add(treeScroller, BorderLayout.CENTER);

    pack();
    show();
  }

  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals("close")) {
      Runnable hideFrame = new Runnable() {
        public void run() {setVisible(false);}
      };
      SwingUtilities.invokeLater(hideFrame);
    }
  }
}
