package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import ch.heigvd.res.lab01.impl.Utils;
/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 * youndzo francine
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private boolean firstLine = true;
  private int numberOfLine = 0;
  private char previousCharacter = '\0';
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
   public void write(String str, int off, int len) throws IOException {
    
    String MyString = "";
    String nextLine[] = Utils.getNextLine(str.substring(off, off+len));

    if (numberOfLine == 0)
      MyString += (++numberOfLine) + "\t";

    while (!nextLine[0].isEmpty()) {
      MyString += nextLine[0] + (++numberOfLine) + "\t";
      nextLine = Utils.getNextLine(nextLine[1]);
    }

    MyString += nextLine[1];
    super.write(MyString, 0, MyString.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException { 

    if (numberOfLine == 0) {
      numberOfLine = 1;
      super.write('1');
      super.write('\t');
    }

     if (c == '\n') {
     
        if (previousCharacter != '\r') {
          super.write('\n');
          numberOfLine++;
          String s = Integer.toString(numberOfLine);
          for (int i = 0; i < s.length(); ++i)
            super.write(s.charAt(i));
          super.write('\t');
        }
        else {
           numberOfLine++;
          super.write('\n');
         
          String s = Integer.toString(numberOfLine);
          for (int i = 0; i < s.length(); ++i)
            super.write(s.charAt(i));
          super.write('\t');
        }
     }
     else{
        if (previousCharacter == '\r') {
          numberOfLine++;
          String s = Integer.toString(numberOfLine);
          for (int i = 0; i < s.length(); ++i)
            super.write(s.charAt(i));
          super.write('\t');
        }
        super.write(c);
    }
    previousCharacter = (char)c;
  }

}