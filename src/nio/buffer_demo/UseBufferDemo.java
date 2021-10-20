package nio.buffer_demo;

import java.nio.IntBuffer;

/**
 * Demo of Buffer creation, input data, read data, and read-write mode switch.
 **/
public class UseBufferDemo {
  private IntBuffer intBuffer = null;
  private static final int DEFAULT_CAPACITY = 100;

  /**
   * Buffer cannot be created by ctor. It should be created by calling Subclass.allocate().
   **/
  private void allocateBufferTest(int capacity) {
    intBuffer = IntBuffer.allocate(DEFAULT_CAPACITY);

    print("-----> Allocation Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Write data starting at position pointer. If the position pointer reaches the capacity,
   * then java.nio.BufferOverflowException is thrown.
   * */
  private void bufferPutTest(int times) {
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
  private void bufferFlipTest() {
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
  private void bufferGetTest(int count) {
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
   * Move all elements starting at index position to index 0. Say the write limit is index 99, and
   * the current position is 15. The it will move all data from index 15 - 99 to index 0 - 84, then
   * reset position to 85.
   *
   * Compact() does not check buffer element is null or not.
   */
  private void bufferCompactTest() {
    checkBufferNull();
    intBuffer.compact();

    print("\n-----> Compact Data Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Reset position to 0. Now the buffer can write data from scratch or read from the beginning.
   */
  private void bufferClearTest() {
    checkBufferNull();
    intBuffer.clear();

    print("\n-----> Clear Data Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * Reset position to 0 and switch to read mode, so all data can be read again.
   */
  private void bufferRewindTest() {
    checkBufferNull();
    intBuffer.rewind();

    print("\n-----> Rewind Buffer Finished <-----");
    printParam(intBuffer.position(), intBuffer.limit(), intBuffer.capacity());
    print("");
  }

  /**
   * mark(): store the current position value to variable mark;
   * reset(): assign mark value to position.
   */
  private void bufferMarkResetTest(int count) {
    checkBufferNull();

    print("Begin mark-reset test\nMark position\n");
    intBuffer.mark();
    bufferGetTest(count);
    print("Reset position and read again\n");
    intBuffer.reset();
    bufferGetTest(count);
    print("End mark-reset test");
  }

  public static void main(String[] args) {
    UseBufferDemo demo = new UseBufferDemo();

    demo.allocateBufferTest(DEFAULT_CAPACITY);

    demo.bufferPutTest(30);
    // java.nio.BufferOverflowException
    // demo.bufferPutTest(intBuffer.capacity() + 1);

    demo.bufferFlipTest();
    demo.bufferGetTest(15);

    demo.bufferClearTest();
    demo.bufferGetTest(15);

    demo.bufferRewindTest();
    demo.bufferGetTest(15);

    demo.bufferRewindTest();
    demo.bufferMarkResetTest(15);
    demo.bufferCompactTest();
    //bufferClearTest();
  }

  private void checkBufferNull() {
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