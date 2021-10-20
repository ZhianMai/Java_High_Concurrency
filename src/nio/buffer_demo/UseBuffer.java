package nio.buffer_demo;

import java.nio.IntBuffer;

/**
 * Demo of Buffer creation, input data, read data, and read-write mode switch.
 **/
public class UseBuffer {
  static IntBuffer intBuffer = null;
  private static final int DEFAULT_CAPACITY = 100;

  /**
   * Buffer cannot be created by ctor. It should be created by calling Subclass.allocate().
   **/
  static void allocateBufferTest(int capacity) {
    intBuffer = IntBuffer.allocate(DEFAULT_CAPACITY);

    print("-----> Allocation Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Write data starting at position pointer. If the position pointer reaches the capacity,
   * then java.nio.BufferOverflowException is thrown.
   * */
  static void bufferPutTest(int times) {
    checkBufferNull();

    for (int i = 0; i < times; i++) {
      intBuffer.put(i);
    }

    print("-----> Write Data Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Write mode switches to read mode.
   */
  static void bufferFlipTest() {
    checkBufferNull();

    intBuffer.flip();

    print("-----> Buffer Flipped <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Read data from the buffer, and buffer should be in the read mode.
   *
   * If the buffer is still in the write mode, then the read position is same as the
   * write position, so it will read the empty value after all valid written data.
   *
   * If the read data exceeds limit, then BufferUnderflowException is thrown.
   */
  static void bufferGetTest(int count) {
    checkBufferNull();

    for (int i = 1; i <= count; i++) {
      System.out.print("Get buffer value: " + intBuffer.get() + ", ");

      if (i % 5 == 0) {
        print("");
      }
    }

    print("\n-----> Read Data Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Move all elements starting at index position to index 0, say
   */
  static void bufferCompactTest() {
    checkBufferNull();
    intBuffer.compact();

    print("\n-----> Compact Data Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Reset position to 0. Now the buffer can write data from scratch or read from the beginning.
   */
  static void bufferClearTest() {
    checkBufferNull();
    intBuffer.clear();

    print("\n-----> Clear Data Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Reset position to 0 and switch to read mode, so all data can be read again.
   */
  static void bufferRewindTest() {
    checkBufferNull();
    intBuffer.rewind();

    print("\n-----> Rewind Buffer Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  public static void main(String[] args) {
    allocateBufferTest(DEFAULT_CAPACITY);

    bufferPutTest(30);
    // java.nio.BufferOverflowException
    // bufferPutTest(intBuffer.capacity() + 1);

    bufferFlipTest();
    bufferGetTest(15);

    bufferClearTest();
    bufferGetTest(15);

    bufferRewindTest();
    bufferGetTest(15);

    bufferCompactTest();
    bufferRewindTest();
    bufferGetTest(15);
    //bufferClearTest();
  }

  private static void checkBufferNull() {
    if (intBuffer == null) {
      allocateBufferTest(DEFAULT_CAPACITY);
    }
  }

  private static void printParam(int position, int limit, int capacity) {
    print("Position: " + position);
    print("Limit: " + limit);
    print("Capacity: " + capacity);
  }

  private static void print(Object str) {
    System.out.println(str);
  }
}