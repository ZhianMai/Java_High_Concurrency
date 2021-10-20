package nio.channel_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIOCopy {
  public static final String DEFAULT_SOURCE_FILE = "./resources/nio_data.txt";
  public static final String DEFAULT_DESTINATION_FILE = "./resources/nio_data_copied.txt";
  public static final int DEFAULT_BUFFER_SIZE = 1024;

  public void nioCopySourceFile(String inputFilePath, String outputFilePath) throws IOException {
    File srcFile = new File(inputFilePath);
    File destFile = new File(outputFilePath);
    FileInputStream inStream = null;
    FileOutputStream outStream = null;
    FileChannel inChanel = null;
    FileChannel outChannel = null;

    try {
      if (!srcFile.exists()) {
        System.out.println("Source file not exist.");
        return;
      }

      if (!destFile.exists()) {
        destFile.createNewFile();
      }

      // Start file copy
      inStream = new FileInputStream(srcFile);
      // Append writing
      outStream = new FileOutputStream(destFile, true);
      // Overwriting file
      // outStream = new FileOutputStream(destFile, false);
      inChanel = inStream.getChannel();
      outChannel = outStream.getChannel();

      ByteBuffer byteBuff = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);

      while (inChanel.read(byteBuff) != -1) { // Read one line to buffer
        byteBuff.flip(); // Buffer switch to read mode.

        // Write data
        while (outChannel.write(byteBuff) != 0) {

        }

        byteBuff.clear(); // reset
      }
    } catch (Exception e ) {

    } finally {
      inStream.close();
      outStream.close();
      inChanel.close();
      outChannel.close();
    }
  }

  public static void main(String[] args) {
    FileNIOCopy fileNIOCopy = new FileNIOCopy();
    String srcFile = DEFAULT_SOURCE_FILE;
    String destFile = DEFAULT_DESTINATION_FILE;

    try {
      fileNIOCopy.nioCopySourceFile(srcFile, destFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
