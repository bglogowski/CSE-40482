#!/bin/bash

echo "Running with no locking..."
echo "Results with 1 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 1
echo "Results with 4 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 4
echo "Results with 8 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 8
echo "Results with 16 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 16
echo "Results with 19 threads (the next highest prime)"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 19
echo
echo "Running with reentrant locks..."
echo "Results with 1 thread"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 1 --ReentrantLock
echo "Results with 4 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 4 --ReentrantLock
echo "Results with 8 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 8 --ReentrantLock
echo "Results with 16 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 16 --ReentrantLock
echo "Results with 19 threads (the next highest prime)"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 19 --ReentrantLock
echo
echo "Running with atomic long..."
echo "Results with 1 thread"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 1 --AtomicLong
echo "Results with 4 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 4 --AtomicLong
echo "Results with 8 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 8 --AtomicLong
echo "Results with 16 threads"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 16 --AtomicLong
echo "Results with 19 threads (the next highest prime)"
java -cp commons-cli-1.4.jar:. Lesson5Concurrent -n 19 --AtomicLong
