package dataspork.gui;

import dataspork.Main;
//import dataspork.interp.InterpreterClass;
//import dataspork.interp.Interpreter;
import dataspork.interp.*;
import dataspork.util.JMenuUtil;
import dataspork.reader.*;
import dataspork.tree.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Enumeration;
import java.io.*;
import java.net.URL;
import org.xml.sax.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainGUI extends CommonFrame implements ActionListener {

  protected Main main;
  protected DataTreeGUI tree;
  protected JScrollPane treeScroller;
  protected JFileChooser fChooser;
  protected JTabbedPane tabbedPane;
  protected JComboBox cbox;
  protected ReaderIndex ri;
  protected String[] readerNames;
  protected String[] filterClasses;
  protected String[] rKeyList;
  protected String[] fKeyList;
  public String[] fileList;
  public String[] readerList;
  public int[] groupList;

  protected String[] storedGroupFiles = null;
  protected String[] storedGroupReaders;
  protected int[] storedGroupTabs;
  protected String[] storedSingleFiles = null;
  protected String[] storedSingleReaders;
  protected int[] storedSingleTabs;
  protected int storedNGroups = 0;

  protected javax.swing.filechooser.FileFilter[] fFilter;
  protected javax.swing.filechooser.FileFilter xmlFilter = new XmlFilter();

  public MainGUI(Main main) {
    super();
    this.main=main;
    this.ri = main.ri;

    setTitle("DataSpork 1.0");

    // set up tabbed pane
    tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

    // Otherwise, add one empty tab as a space holder
    DataTreeNode top = new DataTreeNode("no data loaded");
    tree = new DataTreeGUI(top);
    treeScroller = new JScrollPane(tree);
    treeScroller.setPreferredSize(new Dimension(200,200));
    treeScroller.setBorder(CommonFrame.BORDER);
    // make the first tab
    tabbedPane.addTab("empty",treeScroller);

    contentPane.add(tabbedPane,BorderLayout.CENTER);

    // set up new FileChooser
    fChooser = new JFileChooser(".");
    fChooser.setPreferredSize(new Dimension(500,400));
    fChooser.setMultiSelectionEnabled(true);

    // Add XML file filter
    fChooser.addChoosableFileFilter(xmlFilter);

    // Get readers and filters from ReaderIndex
    readerNames = new String[ri.getNReaders()];
    filterClasses = new String[ri.getNFilters()];
    rKeyList = ri.getReaderKeyList();
    fKeyList = ri.getFilterKeyList();
    fFilter = new javax.swing.filechooser.FileFilter[ri.getNFilters()];

    // get list of file filters
    for (int i=0;i<ri.getNFilters();i++) {
      filterClasses[i] = ri.getFilterClass(fKeyList[i]);
      try {
        fFilter[i] = (javax.swing.filechooser.FileFilter)Class.forName(filterClasses[i]).newInstance();
        fChooser.addChoosableFileFilter(fFilter[i]);
      } catch (Exception e) {
        System.out.println("exception in MainGUI, adding filters: "+e);
      }
    }

    // get list of data reader names
    for (int i=0;i<ri.getNReaders();i++) {
      readerNames[i] = ri.getReaderName(rKeyList[i]);
    }

    // set default file filter
    fChooser.setFileFilter(fFilter[0]);

    // set up list of data readers
    cbox = new JComboBox(readerNames);
    cbox.setEditable(false);
    cbox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String rName = (String)cb.getSelectedItem();
        String rKey = ri.getReaderNameKey(rName);
        String fKey = ri.getReaderFilterKey(rKey);
        for (int j=0;j<ri.getNFilters();j++) {
          if (fKey.equals(fKeyList[j])) {
            try {
              fChooser.setFileFilter(fFilter[j]);
              fChooser.updateUI();
            } catch (Exception e2) { 
              System.out.println("Exception in auto filter select:"+e2);
            }
          }
        }
      }
    });

    // add the reader list to the JFileChooser
    JPanel jp = new JPanel();
    jp.add(cbox);
    fChooser.setAccessory(jp);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
                                        "quit"));       
      }
    });

    // Set up File menu
    JMenuUtil.addMenu( menuBar, "File",
             new String[] {"Open Dataset(s)","Open XML","Save XML","","Quit"},
             new String[] {"open","openxml","savexml","","quit"},
             new int[]    {KeyEvent.VK_F,KeyEvent.VK_O,KeyEvent.VK_S,0,KeyEvent.VK_Q},
             this
    );

    // Set up Dataset menu
    JMenuUtil.addMenu( menuBar, "Datasets",
        new String[] {"Delete Tab", "Delete All Tabs"},
        new String[] {"close","closeall"},
        new int[]    {KeyEvent.VK_T,0},
        this
    );

    // Now display the window.
    pack();
    show();
  }

  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals("quit")) {
      Runnable disposeFrame = new Runnable() {
        public void run() {
          setVisible(false);
          dispose();
          main.quit();
        }
      };
      SwingUtilities.invokeLater(disposeFrame);
    }

    if (command.equals("open")) {
      int returnVal = fChooser.showOpenDialog(this.tabbedPane);	
      if (returnVal != JFileChooser.APPROVE_OPTION) return;

      /// GET FILES ///

      final File[] files = fChooser.getSelectedFiles();

      // Read in data
      AddData ad = new AddData(files,(String)cbox.getSelectedItem(),ri);
      DataTreeNode[] node = ad.getNodes();
      String[] tabName = ad.getTabNames();

      // Store file info, to allow XML file saving (call _before_ adding tabs)
      StoreFileInfo(files,(String)cbox.getSelectedItem(),ad.getNTabs(),this.tabbedPane.getTabCount());
        
      /// ADD TO GUI ///

      for (int j=0;j<ad.getNTabs();j++) {
        DataTreeGUI tree = new DataTreeGUI(node[j]);
        JScrollPane treeScroller = new JScrollPane(tree);
        treeScroller.setPreferredSize(new Dimension(400,410));
        treeScroller.setBorder(CommonFrame.BORDER);
        newTab(tabName[j],treeScroller);
      }
    }

    if (command.equals("openxml")) {

      fChooser.setFileFilter(xmlFilter);
      int returnVal = fChooser.showOpenDialog(this.tabbedPane);	
      if (returnVal != JFileChooser.APPROVE_OPTION) return;

      // when it is implemented, replace this line with:
      final File[] files = fChooser.getSelectedFiles();
      //final File[] files = getSelectedFiles(fChooser);

      for (int i=0;i<files.length;i++) {
        URL url = null;
        try {url = files[i].toURL();}
        catch (Exception e) {}
        try {
          XmlDocHandler handler = new XmlDocHandler(url);
          SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
          //Parser parser = new Parser();
          //parser.setDocumentHandler(handler);
          //parser.setErrorHandler(handler);
          //InputSource input = new InputSource(url.toString());
          parser.parse(url.toString(),handler);
          fileList = handler.getFileList();
          readerList = handler.getReaderList();
          groupList = handler.getGroupList();
        }
        catch (Throwable t) { t.printStackTrace(); }
        AddXmlItems();
      }


    }
    if (command.equals("savexml")) {
      fChooser.setFileFilter(xmlFilter);
      fChooser.setMultiSelectionEnabled(false);
      int returnVal = fChooser.showSaveDialog(this.tabbedPane);	
      fChooser.setMultiSelectionEnabled(true);
      if (returnVal != JFileChooser.APPROVE_OPTION) return;

      final File xmlFile = fChooser.getSelectedFile();
      try {
        PrintWriter out = new PrintWriter(new FileWriter(xmlFile));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE dataSpork>");
        out.println("<dataSpork>");

        // add the groups first
        int[] tempTabList = new int[storedNGroups];
        int tempCnt = 0;
        int currentTab = -2;
        if (storedGroupFiles != null) {
          for (int i=0;i<storedGroupFiles.length;i++) {
            if (storedGroupTabs[i] != currentTab && storedGroupTabs[i] > -3) { 
              currentTab = storedGroupTabs[i];
              tempTabList[tempCnt] = currentTab;
              tempCnt++;
            }
          }

          for (int i=0;i<tempCnt;i++) {
            out.println("<simGroup>");
            for (int j=0;j<storedGroupFiles.length;j++) {
              if (storedGroupTabs[j] == tempTabList[i]) { 
                out.println("<simRef ref=\""+storedGroupFiles[j]+"\" reader=\""+storedGroupReaders[j]+"\"/>");
              }
            }
            out.println("</simGroup>");
          }
        }
        // now add the single files
        if (storedSingleFiles != null) {
          for (int i=0;i<storedSingleFiles.length;i++) {
            if (storedSingleTabs[i] > -3) {
              out.println("<simRef ref=\""+storedSingleFiles[i]+"\" reader=\""+storedSingleReaders[i]+"\"/>");
            }
          }
        }
        out.println("</dataSpork>");
        out.close();      
      } catch (Exception e) {
        System.out.println("Exception in MainGUI, savexml: " + e);
      }
    }

    if (command.equals("close")) {
      if (tabbedPane.getTabCount() > 0) {
        int currTab = tabbedPane.getSelectedIndex();
        tabbedPane.removeTabAt(currTab);

        // update the storedxxx arrays, to allow xml saving

        for (int i=0;i<storedGroupFiles.length;i++) {
          if (storedGroupTabs[i] == currTab) storedGroupTabs[i] = -100;
          else if (storedGroupTabs[i] > currTab) storedGroupTabs[i]--;
        }
        for (int i=0;i<storedSingleFiles.length;i++) {
          if (storedSingleTabs[i] == currTab) storedSingleTabs[i] = -100;
          else if (storedSingleTabs[i] > currTab) storedSingleTabs[i]--;
        }
      }
    }
    if (command.equals("closeall")) {
      int tabCount = tabbedPane.getTabCount();
      for (int i=tabCount-1;i>=0;i--) tabbedPane.removeTabAt(i);
      storedGroupFiles = null;
      storedGroupReaders = null;
      storedGroupTabs = null;
      storedSingleFiles = null;
      storedSingleReaders = null;
      storedSingleTabs = null;
      storedNGroups = 0;
    }

  }

  public void AddXmlItems() {

    String currentReader = null;

    // find the number of groups
    int nGroups = 0;
    for (int i=0; i<groupList.length; i++) {
      nGroups = groupList[i]>nGroups ? groupList[i] : nGroups;
    }

    // first add all the groups
    for (int i=1; i<=nGroups; i++) {
      int gcnt = 0;
      File[] groupFiles = new File[fileList.length];
      for (int j=0; j<fileList.length; j++) {
        if (i==groupList[j]) {
          currentReader = readerList[j];
          groupFiles[gcnt] = new File(fileList[j]);
          gcnt++;
        }
      }
      File[] tempArray = new File[gcnt];
      for (int j=0;j<gcnt;j++) tempArray[j] = groupFiles[j];

      AddData ad = new AddData(tempArray,currentReader,ri);
      DataTreeNode[] node = ad.getNodes();
      String[] tabName = ad.getTabNames();

      // Store file info, to allow XML file saving (call _before_ adding tabs)
      StoreFileInfo(tempArray,currentReader,ad.getNTabs(),this.tabbedPane.getTabCount());

      for (int j=0;j<ad.getNTabs();j++) {
        DataTreeGUI tree = new DataTreeGUI(node[j]);
        JScrollPane treeScroller = new JScrollPane(tree);
        treeScroller.setPreferredSize(new Dimension(400,410));
        treeScroller.setBorder(CommonFrame.BORDER);
        this.newTab(tabName[j],treeScroller);
      }
    }

    // now add all the single files
    for (int j=0; j<fileList.length; j++) {
      if (groupList[j]==0) {
        File[] singleFile = new File[1];
        singleFile[0] = new File(fileList[j]);
        AddData ad = new AddData(singleFile,readerList[j],ri);
        DataTreeNode[] node = ad.getNodes();
        String[] tabName = ad.getTabNames();

      // Store file info, to allow XML file saving (call _before_ adding tabs)
        StoreFileInfo(singleFile,readerList[j],ad.getNTabs(),this.tabbedPane.getTabCount());

        DataTreeGUI tree = new DataTreeGUI(node[0]);
        JScrollPane treeScroller = new JScrollPane(tree);
        treeScroller.setPreferredSize(new Dimension(400,410));
        treeScroller.setBorder(CommonFrame.BORDER);
        this.newTab(tabName[0],treeScroller);
      }
    }        
  }

  public void newTab(String tabName, JComponent comp) {
    tabbedPane.addTab(tabName,comp);
    int etab = tabbedPane.indexOfTab("empty");
    if (etab > -1) tabbedPane.removeTabAt(etab);
  }

  public void StoreFileInfo(File[] files, String ReaderName, int NTabs, int TabCount) {

    int etab = tabbedPane.indexOfTab("empty");
    if (etab > -1) TabCount = 0;

    if (files.length>1 && NTabs == 1) {    // this is a group
      int start;
      if (storedGroupFiles != null) {
        start = storedGroupFiles.length;
      }
      else { start = 0; }

      String[] tempFiles = new String[start+files.length];
      String[] tempReaders = new String[start+files.length];
      int[] tempTabs = new int[start+files.length];
      for (int i=0;i<start;i++) {
        tempFiles[i] = storedGroupFiles[i];
        tempReaders[i] = storedGroupReaders[i];
        tempTabs[i] = storedGroupTabs[i];
      }
      for (int i=start;i<start+files.length;i++) {
        tempFiles[i] = files[i-start].getName();
        tempReaders[i] = ReaderName;
        tempTabs[i] = TabCount;
      }
      storedGroupFiles = tempFiles;
      storedGroupReaders = tempReaders;
      storedGroupTabs = tempTabs;
      storedNGroups++;
    }
    else {
      int start;
      if (storedSingleFiles != null) {
        start = storedSingleFiles.length;
      }
      else { start = 0; }

      String[] tempFiles = new String[start+files.length];
      String[] tempReaders = new String[start+files.length];
      int[] tempTabs = new int[start+files.length];
      for (int i=0;i<start;i++) {
        tempFiles[i] = storedSingleFiles[i];
        tempReaders[i] = storedSingleReaders[i];
        tempTabs[i] = storedSingleTabs[i];
      }
      for (int i=start;i<start+files.length;i++) {
        tempFiles[i] = files[i-start].getName();
        tempReaders[i] = ReaderName;
        tempTabs[i] = TabCount+i-start;
      }
      storedSingleFiles = tempFiles;
      storedSingleReaders = tempReaders;
      storedSingleTabs = tempTabs;      
    }
  }

/*  public static File[] getSelectedFiles(JFileChooser chooser) {
    // Although JFileChooser won't give us this information,
    // we need it...
    Container c1 = (Container)chooser.getComponent(3);
    JList list = null;
    while (c1 != null) {
      Container c = (Container)c1.getComponent(0);
      if (c instanceof JList) {
        list = (JList)c;
        break;
      }
      c1 = c;
    }
    Object[] entries = list.getSelectedValues();
    File[] files = new File[entries.length];
    for (int k=0; k<entries.length; k++) {
      if (entries[k] instanceof File)
        files[k] = (File)entries[k];
    }
    return files;
  } */
}
