package dataspork.text;
import java.io.*;
import java.util.*;

public interface Parser {
  public ParsedLine readNextLine();
}
