package dataspork.util;
import java.io.*;

/** Extends StreamTokenizer so that it recognizes doubles in the
  * Scientific format. 
  *
  * The approach is to read everything as words (no numbers) and then
  * have nextToken check if word is a double, using the standard function
  * java.lang.Double.valueOf().  To do this we must replace the parseNumbers 
  * settings with a boolean flag, doParseNumbers, and make sure numbers
  * are read as words.
  */
public class SciStreamTokenizer extends StreamTokenizer {

  private boolean doParseNumbers=false;

  public SciStreamTokenizer(Reader is){
    super(is);
    resetSyntax();  //Needed to clear the default parseNumbers() setting.
    //Reset the defaults, but call my parseNumbers.
    wordChars('a', 'z');
    wordChars('A', 'Z');
    wordChars(128 + 32, 255);
    whitespaceChars(0, ' ');
    commentChar('/');
    quoteChar('"');
    quoteChar('\'');
    parseNumbers();
  }

  public void parseNumbers() {
    //Disable StreamTokenizer.parseNumbers().
    //Set flag, used in nextToken().
    doParseNumbers=true;
    //Set characters in numbers to read as words.
    wordChars('0','9');
    wordChars('-','.');
    wordChars('+','+');
  }

  public void resetSyntax(){
    super.resetSyntax();
    doParseNumbers=false;  //Set my flag.
  }

  public int nextToken() throws IOException {
    int tt = super.nextToken();
    //Check for double precision number.
    if (doParseNumbers && tt==TT_WORD) {
      try {
        nval = Double.valueOf(sval).doubleValue();
        tt = TT_NUMBER;
      }
      catch (NumberFormatException e) { }
    }
    return tt;
  }

}
