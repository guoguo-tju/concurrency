#高并发学习笔记 concurrency_karl
---
<br>


<h2 id="并发">并发</h2>
同时拥有两个或者多个线程,如果程序在单核处理器上运行,多个线程交替地换入或者换出内存,这些线程是同时"存在"的,每个线程都处于执行过程中的某个状态,如果运行多核处理器上,此时,程序中每个线程都将分配到一个处理核上,因此,可以同时运行.


<h2 id="CPU多级缓存">CPU多级缓存</h2>

   <h3 id="为什么需要CPU缓存呢">为什么需要CPU缓存呢</h3>
   因为CPU的频率太快了,快到主存跟不上,这样在处理器时钟周期内,CPU常常需要等待主存,浪费资源.所以cache的出现,是为了缓解CPU和内存之间速度的不匹配问题(结构:CPU-->cache-->memory)
   
   <h3 id="保证缓存的一致性(MESI协议)">保证缓存的一致性(MESI协议)</h3>
   参考博客: https://blog.csdn.net/muxiqingyang/article/details/6615199
   
   <h3 id="乱序执行优化">乱序执行优化</h3>
   处理器为提高运算速度而做出的违背代码原有顺序的优化(重排序).单核的环境下处理结果与预期相同,但多核情况下,多个核来执行指令,每个核都可能被乱序,使得结果偏离预期.
   
   <h3 id="Java内存模型(Java Memory Model --JMM)">Java内存模型(Java Memory Model --JMM)</h3>
   * 它规定了一个线程如何和何时可以看到其他线程修改过的共享变量的值，以及在必须时如何同步地访问共享变量
   * 堆Heap:运行时数据区，有垃圾回收，堆的优势可以动态分配内存大小，生存期也不必事先告诉编译器，因为他是在运行时动态分配内存。缺点是由于运行时动态分配内存，所以存取速度慢一些。
   * 栈Stack:优势存取速度快，速度仅次于计算机的寄存器。栈的数据是可以共享的，但是缺点是存在栈中数据的大小与生存期必须是确定的。主要存放基本类型变量，对象据点。要求调用栈和本地变量存放在线程栈上。
   * 静态类型变量跟随类的定义存放在堆上。存放在堆上的对象可以被所持有对这个对象引用的线程访问。
   * 如果两个线程同时调用了同一个对象的同一个方法，他们都会访问这个对象的成员变量。但是这两个线程都拥有的是该对象的成员变量（局部变量）的私有拷贝。—线程封闭中的堆栈封闭
   
   <h3 id="计算机硬件架构">计算机硬件架构</h3>
   * CPU Registers(寄存器):是CPU内存的基础，CPU在寄存器上执行操作的速度远大于在主存上执行的速度。这是因为CPU访问寄存器速度远大于主存。
   * CPU Cache Memory(高速缓存):由于计算机的存储设备与处理器的运算速度之间有着几个数量级的差距，所以现代计算机系统都不得不加入一层读写速度尽可能接近处理器运算速度的高级缓存，来作为内存与处理器之间的缓冲。将运算时所使用到的数据复制到缓存中,让运算能快速的进行。当运算结束后，再从缓存同步回内存之中，这样处理器就无需等待缓慢的内存读写了。
   * RAM-Main Memory(主存/内存):当一个CPU需要读取主存的时候，他会将主存中的部分读取到CPU缓存中，甚至他可能将缓存中的部分内容读到他的内部寄存器里面，然后在寄存器中执行操作。当`CPU需要将结果回写到主存的时候，他会将内部寄存器中的值刷新到缓存中，然后在某个时间点从缓存中刷回主存。

   <h3 id="JMM与硬件架构的关系">JMM与硬件架构的关系</h3>
   * Java内存模型抽象结构：每个线程都有一个私有的本地内存，本地内存他是java内存模型的一个抽象的概念。它并不是真实存在的，它涵盖了缓存、写缓冲区、寄存器以及其他的硬件和编译器的优化。本地内存中它存储了该线程以读或写共享变量拷贝的一个副本。
   * 从更低的层次来说，主内存就是硬件的内存，是为了获取更高的运行速度，虚拟机及硬件系统可能会让工作内存优先存储于寄存器和高速缓存中，java内存模型中的线程的工作内存是CPU的寄存器和高速缓存的一个抽象的描述。而JVM的静态内存存储模型它只是对内存的一种物理划分而已。它只局限在内存，而且只局限在JVM的内存。

   <h3 id="JMM中线程与主内存中同步的八种操作">JMM中线程与主内存中同步的八种操作</h3>
        1.lock（锁定）：作用于主内存的变量，把一个变量标识变为一条线程独占状态
        2.unlock（解锁）：作用于主**内存的变量，把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定
        3.read（读取）：作用于主内存的变量，把一个变量值从主内存传输到线程的工作内存中，以便随后的load动作使用
        4.load（载入）：作用于工作内存的变量，它把read操作从主内存中得到的变量值放入工作内存的变量副本中
        5.use（使用）：作用于工作内存的变量，把工作内存中的一个变量值传递给执行引擎
        6.assign（赋值）：作用于工作内存的变量，它把一个从执行引擎接受到的值赋值给工作内存的变量
        7.store（存储）：作用于工作内存的变量，把工作内存中的一个变量的值传送到主内存中，以便随后的write的操作
        8.write（写入）：作用于主内存的变量，它把store操作从工作内存中一个变量的值传送到主内存的变量中
   <h3 id="同步规则">同步规则</h3>
   * 如果要把一个变量从主内存中赋值到工作内存，就需要按顺序得执行read和load操作，如果把变量从工作内存中同步回主内存中，就要按顺序得执行store和write操作，但java内存模型只要求上述操作必须按顺序执行，没有保证必须是连续执行
   * 不允许read和load、store和write操作之一单独出现
   * 不允许一个线程丢弃他的最近assign的操作，即变量在工作内存中改变了之后必须同步到主内存中
   * 不允许一个线程无原因地（没有发生过任何assign操作）把数据从工作内存同步到主内存中
   * 一个新的变量只能在主内存中诞生，不允许在工作内存中直接使用一个未被初始化（load或assign）的变量。即就是对一个变量实施use和store操作之前，必须先执行过了load和assign操作
   * 一个变量在同一时刻只允许一条线程对其进行lock操作，但lock操作可以同时被一条线程重复执行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才会解锁，lock和unlock必须成对出现
   * 如果一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎中使用这个变量前需要重新执行load或assign操作初始化变量的值
   * 如果一个变量事先没有被lock操作锁定，则不允许他执行unlock操作，也不允许去unlock一个被其他线程锁定的变量
   * 对一个变量执行unlock操作之前，必须先把此变量同步到主内存中（执行store和write操作）
   
   <h3 id="并发的优势与风险">并发的优势与风险</h3>
   ####优势:
   
   * 速度：同时处理多个请求，响应更快；复杂的操作可以分成多个进程同时进行。
   * 设计：程序设计在某些情况下更简单，也可以有更多选择
   * 资源利用：CPU能够在等待IO的时候做一些其他的事情
   
   ####风险:
   * 安全性：多个线程共享数据时可能会产生于期望不相符的结果
   * 活跃性：某个操作无法继续进行下去时，就会发生活跃性问题。比如死锁、饥饿问题
   * 性能：线程过多时会使得CPU频繁切换，调度时间增多；同步机制；消耗过多内存。
   
   <h3 id="并发模拟工具">并发模拟工具</h3>
   * PostMan
   * Apache Bench(AB):
   
   			ab -n 1000 -c 50 http://localhost:8080/test	 请求1000次,最多允许同时50次访问(并发数为50)
   			
   * JMeter
   
   <h3 id="并发模拟代码">并发模拟代码</h3>
    ConCurrencyExample01
   
   <h3 id="原子性">原子性</h3>
    CAS: 不断获取底层的值,直到和当前对象(工作内存中的)值相等(var2=var5)
    AtomicXXX: CAS(compareAndSwap), Unsafe.compareAndSwapInt
    底层实现:
<pre>
    //var1,传过来的count对象(AtomicInteger)
    //var2,该对象工作内存中的值
    //var4,要增加的值
    public final int getAndAddInt(Object var1, long var2, int var4) {
    //获取底层的该对象的值,主内存中的值
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
            //不断获取底层的值,直到和当前对象(工作内存中的)值相等(var2=var5).CAS,最终的目的是返回对象最新的值
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
        return var5;
    }	
    这里的compareAndSwapInt（var1, var2, var5, var5 + var4）换成 compareAndSwapInt（obj, offset, expect, update）能清楚一些，如果obj内的value和expect相等，就证明没有其他线程改变过这个变量，那么就更新它(主内存)为update,返回的值是var5给工作内存,然后工作内存又+1。
    CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
</pre>
   AtomicLong,LongAdder:(面试)
   * AtomicLong:死循环方式,如果并发高竞争非常激烈，那么失败量就会很高，性能会受到影响
   * LongAdder:jvm对long，double这些64位的变量拆成两个32位的操作,在高并发的场景，通过热点分区来提高并行度,缺点：在统计的时候如果有并发更新，可能会导致结果有些误差,要求数据精确的话不要使用
   * compareAndSet:更多用到AtomicBoolean中,保证我们要控制的这段代码只被执行一次.
   * AtomicReference:用法同AtomicInteger一样，但是可以放各种对象:AtomicReference<Integer> count = new AtomicReference<>(0);
   * AtomicIntegerFieldUpdater:原子性的去更新某一个类的实例的指定的某一个字段.newUpdater()方法的第一个参数是某类的class文件,第二个参数是指定的字段名,注意:该字段必须是volatile修饰,并且不能是static修饰.
<pre></pre>
   AtomicStampReference:CAS的ABA问题:
   * ABA问题：在CAS操作的时候，其他线程将变量的值A改成了B由改成了A，本线程使用期望值A与当前变量进行比较的时候，发现A变量没有变，于是CAS就将A值进行了交换操作，这个时候实际上A值已经被其他线程改变过，这与设计思想是不符合的
   * 解决思路：每次变量更新的时候，把变量的版本号加一，这样只要变量被某一个线程修改过，该变量版本号就会发生递增操作，从而解决了ABA变化
<pre>
    AtomicLongArray可以指定更新一个数组指定索引位置的值compareAndSet(int i, long expect, long update)
    AtomicBoolean(平时用的比较多),在并发情况下可以让一段代码只被执行一次.
    AtomicBoolean isHappened =  new AtomicBoolean(false);
    if(isHappened.compareAndSet(false,ture)){
    //在高并发情况下if里面的代码只会被执行一次
        ...
    }
</pre>
   native:
   * 使用native关键字说明这个方法是原生函数，也就是这个方法是用C/C++语言实现的，并且被编译成了DLL，由java去调用。 这些函数的实现体在DLL中，JDK的源代码中并不包含，你应该是看不到的。对于不同的平台它们也是不同的。这也是java的底层机制，实际上java就是在不同的平台上调用不同的native方法实现对操作系统的访问的。   
   * 独占锁：是一种悲观锁，synchronized就是一种独占锁，会导致其它所有需要锁的线程挂起，等待持有锁的线程释放锁。
   * 乐观锁：每次不加锁，假设没有冲突去完成某项操作，如果因为冲突失败就重试，直到成功为止。乐观锁用到的机制就是CAS，Compare and Swap。CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。

   <h3 id="原子性-锁-synchronize">原子性-锁-synchronize</h3>  
   
    
    
