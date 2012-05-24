package dataspork.text;
import dataspork.*;
import dataspork.tree.*;

public class TreeText {

  protected DataTreeNode rootNode;
  protected DataTreeNode currentNode;
  protected Parser parser;
  
  public TreeText(DataTreeNode rootNode, Parser parser) {
    this.rootNode=rootNode;
    currentNode=rootNode;
    this.parser=parser;
  }

  public void parse() {
    ParsedLine line = null;
    while ( (line=parser.readNextLine()) != null) {
      line.printToStream(System.out);
    }
  }
}
