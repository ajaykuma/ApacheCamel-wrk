Concurrency & Parallelism
------------------
three different ways to run the application faster with concurrency:
■ Using parallelProcessing options on the Splitter EIP
■ Using a custom thread pool on the Splitter EIP
Note**A thread pool is a group of threads that are created to execute a number of tasks in a task queue.
■ Using staged event-driven architecture (SEDA)
The first two solutions are features that the Splitter EIP provides out of the box. 
The last solution is based on the SEDA principle, which uses queues between tasks. 

The Splitter EIP offers an option to switch on parallel processing
.split(body().tokenize("\n")).streaming().parallelProcessing()

when parallelProcessing is enabled, the Splitter EIP uses a
thread pool to process the messages concurrently. The thread pool is, by default, configured to use 10 threads,
In the console output you’ll see that the thread name is displayed, containing a unique thread number, such as Camel Thread 4 - Split. This thread
number is a sequential, unique number assigned to each thread as it’s created, in any thread pool. This means if you use a second Splitter EIP, the second splitter will most likely have numbers assigned from 11 upwards. 

Note**
In Apache Camel, the default shutdown time for a route is 300 seconds (5 minutes), which is used by the "DefaultShutdownStrategy" when gracefully shutting down a Camel context; this means Camel will wait up to 5 minutes for in-flight messages to finish processing before forcefully shutting down a route. 

-------------------
The Splitter EIP also allows you to use a custom thread pool for concurrency. You can
create a thread pool using the java.util.Executors factory:
ExecutorService threadPool = Executors.newCachedThreadPool();
The newCachedThreadPool method will create a thread pool suitable for executing
many small tasks. The pool will automatically grow and shrink on demand.
 To use this pool with the Splitter EIP, you need to configure it as shown here:
.split(body().tokenize("\n")).streaming().executorService(threadPool)

The reason is that the cached thread pool is
designed to be very aggressive and to spawn new threads on demand. It has no upper
bounds and no internal work queue, which means that when a new task is being handed
over, it will create a new thread if there are no available threads in the thread pool.
 You may also have noticed the thread name in the console output, which indicates
that many threads were created;

a high number of
newly created threads may impact applications in other areas.

For example, instead of using the cached thread pool, you could use a fixed thread
pool. You can use the same Executors factory to create such a pool:
ExecutorService threadPool = Executors.newFixedThreadPool(20);

--------------------------
Seda:
By default, a seda consumer will only use one thread. To leverage concurrency, you
use the concurrentConsumers option to increase the number of threads

When using concurrentConsumers with SEDA endpoints, the thread
pool uses a fixed size, which means that a fixed number of active threads are waiting at all times to process incoming messages. That’s why it’s best to leverage the
concurrency features provided by the EIPs, such as the parallelProcessing on
the Splitter EIP. It will leverage a thread pool that can grow and shrink on
demand, so it won’t consume as many resources as a SEDA endpoint will.

-----------------------





