/**
 * autor : youndzo francine
 */
package ch.heigvd.res.lab01.impl.transformers;


import java.io.Writer;

/**
 * This class returns a writer without any decorator. When an instance of
 * this class is passed to a file system explorer, it will simply duplicate
 * the content of the input file into the output file.
 * 
 * @author Olivier Liechti
 */
public class NoOpFileTransformer extends FileTransformer {

  @Override
  public Writer decorateWithFilters(Writer writer) {
    return writer;
  }

}
