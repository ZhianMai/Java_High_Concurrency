package nio.channel_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIOChannelTransferCopy {
  public static final String DEFAULT_SOURCE_FILE = "./resources/nio_data.txt";
  public static final String DEFAULT_DESTINATION_FILE = "./resources/nio_data_copied.txt";
  public static final int CHANNEL_MAX_COPY_COUNT = 1024;

  public void nioCopySourceFile(String inputFilePath, String outputFilePath) throws IOException {
    File srcFile = new File(inputFilePath);
    if (!srcFile.exists()) {
      throw new IOException("Source file not exist.");
    }

    File destFile = new File(outputFilePath);
    if (!destFile.exists()) {
      destFile.createNewFile();
    }

    FileInputStream inStream = null;
    FileOutputStream outStream = null;
    FileChannel inChannel = null;
    FileChannel outChannel = null;

    try {
      // Start file copy
      inStream = new FileInputStream(srcFile);
      // Append writing
      outStream = new FileOutputStream(destFile, true);
      // OR overwriting the file
      // outStream = new FileOutputStream(destFile, false);
      inChannel = inStream.getChannel();
      outChannel = outStream.getChannel();

      long size = inChannel.size();
      long position = 0;

      while (position < size) {
        //Copy at most XXX bytes every time
        long count = Math.min(CHANNEL_MAX_COPY_COUNT, size - position);
        // Method One: outChannel read data from inChannel
        // position += outChannel.transferFrom(inChannel, position, count);
        // Method Two: inChannel push data to outChannel
        position += inChannel.transferTo(position, count, outChannel);
      }

      outChannel.force(true); // Force to write to disk
    } catch (Exception e ) {
      throw new IOException(e.getMessage());
    } finally {
      inStream.close();
      outStream.close();
      inChannel.close();
      outChannel.close();
    }
  }

  public static void main(String[] args) {
    FileNIOChannelTransferCopy fileNIOCopy = new FileNIOChannelTransferCopy();
    String srcFile = DEFAULT_SOURCE_FILE;
    String destFile = DEFAULT_DESTINATION_FILE;

    try {
      fileNIOCopy.nioCopySourceFile(srcFile, destFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
