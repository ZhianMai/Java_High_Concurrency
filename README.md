# Java_High_Concurrency

(In Progress) 

This repo contains demo of Java NIO, Zookeeper, Kafka...It's designed for high concurrency distributed system.

## Contents

### Java NIO
NIO stands for non-blocking IO, which means when process requires IO, it won't be blocked if IO is unavailable.

#### Buffer Demo :link:[link](src/nio/buffer_demo/BufferDemo.java)
  
  java.nio.IntBuffer demo. Methods included are:
  - allocate(): init a buffer.
  - put(int num): write data to buffer.
  - flip(): buffer switch write mode to read mode.
  - get(): buffer read next data.
  - compact(): move all data at index [position, capacity] to [0, capacity - position], then reset old position to new one.
  - clear(): reset position to 0, and limit to capacity.
  - rewind(): reset position to 0 only.
  - mark(): record the current position value to variable mark;
  - reset(): set the current position to the value in variable mark.
  
#### File NIO Copy :link:[link](src/nio/channel_demo/FileNIOCopy.java)

Use channel to copy a file.
