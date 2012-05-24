package dataspork.reader;
import dataspork.tree.*;
import dataspork.interp.*;
import java.io.File;
import java.util.Enumeration;
import dataspork.interp.ScalarDataset;
import java.net.URL;

public class TestDataReader implements DataReader {
  public TestDataReader() {};

  public DataTreeNode read(URL url) {
    DataTreeNode root = new DataTreeNode("Test Data");
    //VMC
    DataTreeNode vmc = new DataTreeNode("vmc");
    root.insert(vmc,0);
    double en[] = {0.1,0.3,0.2};
    int dim[] = {3};
    DataTreeNode energy = new DataTreeNode("energy",
                              new ConstDataSource(en,dim,DataSource.DOUBLE));
    energy.addInterpreter(new InterpreterClass(ScalarDataset.class,energy));
    vmc.insert(energy,0);
    double p[] = {5.0,3.1,4.5};
    DataTreeNode pressure = new DataTreeNode("pressure",
                                new ConstDataSource(p,dim,DataSource.DOUBLE));
    pressure.addInterpreter(new InterpreterClass(ScalarDataset.class,pressure));
    vmc.insert(pressure,1);
    //DMC
    DataTreeNode dmc = new DataTreeNode("dmc");
    en = new double [] {1.0,1.1,0.5};
    energy = new DataTreeNode("energy",
                              new ConstDataSource(en,dim,DataSource.DOUBLE));
    energy.addInterpreter(new InterpreterClass(ScalarDataset.class,energy));
    dmc.insert(energy,0);
    p = new double [] {3.0,2.1,3.5};
    pressure = new DataTreeNode("pressure",
                                new ConstDataSource(p,dim,DataSource.DOUBLE));
    pressure.addInterpreter(new InterpreterClass(ScalarDataset.class,pressure));
    dmc.insert(pressure,1);
    root.insert(dmc,1);
    return root;
  }

  public static void main(String argv[]) {
    System.out.println("TestDataReader");
    DataReader reader = new TestDataReader();
    DataTreeNode root = reader.read(null);
    TreeWriter.printTree(root,"");
  }
}
