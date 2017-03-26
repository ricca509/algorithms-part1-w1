# [Percolation](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)

## Dependencies

* [stdlib.jar](http://algs4.cs.princeton.edu/code/stdlib.jar)
* [algs4.jar](http://algs4.cs.princeton.edu/code/algs4.jar)

## Programming Assignment 1: Percolation


Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.

Install a Java programming environment. Install a Java programming environment on your computer by following these step-by-step instructions for your operating system [ Mac OS X · Windows · Linux ]. After following these instructions, the commands javac-algs4 and java-algs4 will classpath in both [stdlib.jar](http://algs4.cs.princeton.edu/code/stdlib.jar) and [algs4.jar](http://algs4.cs.princeton.edu/code/algs4.jar): the former contains libraries for reading data from standard input, writing data to standard output, drawing results to standard draw, generating random numbers, computing statistics, and timing programs; the latter contains all of the algorithms in the textbook.

Percolation. Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

The model. We model a percolation system using an N-by-N grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

Percolates
The problem. In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1, the system percolates. The plots below show the site vacancy probability p versus the percolation probability for 20-by-20 random grid (left) and 100-by-100 random grid (right).

Percolation threshold for 20-by-20 grid                Percolation threshold for 100-by-100 grid          
When N is sufficiently large, there is a threshold value p* such that when p < p* a random N-by-N grid almost never percolates, and when p > p*, a random N-by-N grid almost always percolates. No mathematical solution for determining the percolation threshold p* has yet been derived. Your task is to write a computer program to estimate p*.

Percolation data type. To model a percolation system, create a data type Percolation with the following API:

```java
public class Percolation {
   public Percolation(int N)              // create N-by-N grid, with all sites blocked
   public void open(int i, int j)         // open site (row i, column j) if it is not already
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   public boolean percolates()            // does the system percolate?
}
```

By convention, the indices i and j are integers between 1 and N, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if either i or j is outside this range. The constructor should take time proportional to N^2; all methods should take constant time plus a constant number of calls to the union-find methods union(), find(), connected(), and count().
Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

Initialize all sites to be blocked.
Repeat the following until the system percolates:
Choose a site (row i, column j) uniformly at random among all blocked sites.
Open the site (row i, column j).
The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.

By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let xt be the fraction of open sites in computational experiment t. The sample mean μ provides an estimate of the percolation threshold; the sample standard deviation σ measures the sharpness of the threshold.

Estimating the sample mean and variance
Assuming T is sufficiently large (say, at least 30), the following provides a 95% confidence interval for the percolation threshold:
95% confidence interval for percolation threshold
To perform a series of computational experiments, create a data type PercolationStats with the following API.

```java
public class PercolationStats {
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   public double mean()                     // sample mean of percolation threshold
   public double stddev()                   // sample standard deviation of percolation threshold
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   public static void main(String[] args)   // test client, described below
}
```

The constructor should throw a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0.
Also, include a main() method that takes two command-line arguments N and T, performs T independent computational experiments (discussed above) on an N-by-N grid, and prints out the mean, standard deviation, and the 95% confidence interval for the percolation threshold. Use standard random from our standard libraries to generate random numbers; use standard statistics to compute the sample mean and standard deviation.

```bash
% java PercolationStats 200 100
mean                    = 0.5929934999999997
stddev                  = 0.00876990421552567
95% confidence interval = 0.5912745987737567, 0.5947124012262428

% java PercolationStats 200 100
mean                    = 0.592877
stddev                  = 0.009990523717073799
95% confidence interval = 0.5909188573514536, 0.5948351426485464


% java PercolationStats 2 10000
mean                    = 0.666925
stddev                  = 0.11776536521033558
95% confidence interval = 0.6646167988418774, 0.6692332011581226

% java PercolationStats 2 100000
mean                    = 0.6669475
stddev                  = 0.11775205263262094
95% confidence interval = 0.666217665216461, 0.6676773347835391
```

Analysis of running time and memory usage (optional and not graded). Implement the Percolation data type using the quick-find algorithm [QuickFindUF.java](QuickFindUF.java) from algs4.jar.

Use the stopwatch data type from our standard library to measure the total running time of PercolationStats. How does doubling N affect the total running time? How does doubling T affect the total running time? Give a formula (using tilde notation) of the total running time on your computer (in seconds) as a single function of both N and T.
Using the 64-bit memory-cost model from lecture, give the total memory usage in bytes (using tilde notation) that an N-by-N percolation system uses. Count all memory that is used, including memory for the union-find data structure.
Now, implement the Percolation data type using the weighted quick-union algorithm WeightedQuickUnionUF.java from algs4.jar. Answer the questions in the previous paragraph.

Deliverables. Submit only Percolation.java (using the weighted quick-union algorithm as implemented in the [WeightedQuickUnionUF](http://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html) class) and PercolationStats.java. We will supply stdlib.jar and WeightedQuickUnionUF. Your submission may not call any library functions other than those in java.lang, stdlib.jar, and WeightedQuickUnionUF.


This assignment was developed by Bob Sedgewick and Kevin Wayne. 
Copyright © 2008.

## Results

ASSESSMENT SUMMARY

Compilation:  PASSED
API:          PASSED

Findbugs:     PASSED
Checkstyle:   FAILED (1 warning)

Correctness:  22/26 tests passed
Memory:       8/8 tests passed
Timing:       9/9 tests passed

Aggregate score: 90.77%
[Compilation: 5%, API: 5%, Findbugs: 0%, Checkstyle: 0%, Correctness: 60%, Memory: 10%, Timing: 20%]

ASSESSMENT DETAILS

The following files were submitted:
----------------------------------
4.1K Mar 26 00:02 Percolation.java
2.4K Mar 26 00:02 PercolationStats.java


********************************************************************************
*  COMPILING                                                                    
********************************************************************************


% javac Percolation.java
*-----------------------------------------------------------

% javac PercolationStats.java
*-----------------------------------------------------------


================================================================


Checking the APIs of your programs.
*-----------------------------------------------------------
Percolation:

PercolationStats:

================================================================


********************************************************************************
*  CHECKING STYLE AND COMMON BUG PATTERNS                                       
********************************************************************************


% findbugs *.class
*-----------------------------------------------------------

================================================================


% checkstyle *.java
*-----------------------------------------------------------
PercolationStats.java:34:15: '//' or '/*' is not followed by whitespace. [IllegalTokenText]
Checkstyle ends with 1 error.

================================================================


********************************************************************************
*  TESTING CORRECTNESS
********************************************************************************

Testing correctness of Percolation
*-----------------------------------------------------------
Running 15 total tests.

Tests 1 through 8 create a Percolation object using your code, then repeatedly
open sites by calling open(). After each call to open(), we check the return
values of isOpen(), percolates(), numberOfOpenSites(), and isFull() in that order.
Except as noted, a site is opened at most once.

Test 1: open predetermined list of sites using file inputs
  * filename = input6.txt
  * filename = input8.txt
  * filename = input8-no.txt
  * filename = input10-no.txt
  * filename = greeting57.txt
  * filename = heart25.txt
==> passed

Test 2: open random sites until just before system percolates
  * n = 3
  * n = 5
  * n = 10
  * n = 10
  * n = 20
  * n = 20
  * n = 50
  * n = 50
==> passed

Test 3: open predetermined sites for n = 1 and n = 2
  * filename = input1.txt
  * filename = input1-no.txt
  * filename = input2.txt
  * filename = input2-no.txt
==> passed

Test 4: check for backwash with predetermined sites
  * filename = input20.txt
  * filename = input10.txt
  * filename = input50.txt
  * filename = jerry47.txt
==> passed

Test 5: check for backwash with predetermined sites that have
        multiple percolating paths
  * filename = input3.txt
  * filename = input4.txt
  * filename = input7.txt
==> passed

Test 6: open predetermined sites with long percolating path
  * filename = snake13.txt
  * filename = snake101.txt
==> passed

Test 7: open every site
  * filename = input5.txt
==> passed

Test 8: open random sites until just before system percolates,
        allowing open() to be called on a site more than once
  * n = 3
  * n = 5
  * n = 10
  * n = 10
  * n = 20
  * n = 20
  * n = 50
  * n = 50
==> passed

Test 9: check that IndexOutOfBoundsException is thrown if (col, row) is out of bounds
  * n = 10, (col, row) = (0, 6)
  * n = 10, (col, row) = (12, 6)
  * n = 10, (col, row) = (11, 6)
  * n = 10, (col, row) = (6, 0)
  * n = 10, (col, row) = (6, 12)
  * n = 10, (col, row) = (6, 11)
==> passed

Test 10: check that IllegalArgumentException is thrown if n <= 0 in constructor
  * n = -10
  * n = -1
  * n = 0
==> passed

Test 11: create multiple Percolation objects at the same time
         (to make sure you didn't store data in static variables)
==> passed

Test 12: open predetermined list of sites using file inputs,
         but change the order in which methods are called
  * filename = input8.txt;  order =     isFull(),     isOpen(), percolates()
  * filename = input8.txt;  order =     isFull(), percolates(),     isOpen()
  * filename = input8.txt;  order =     isOpen(),     isFull(), percolates()
  * filename = input8.txt;  order =     isOpen(), percolates(),     isFull()
  * filename = input8.txt;  order = percolates(),     isOpen(),     isFull()
  * filename = input8.txt;  order = percolates(),     isFull(),     isOpen()
==> passed

Test 13: call all methods in random order until just before system percolates
  * n = 3
  * n = 5
  * n = 7
  * n = 10
  * n = 20
  * n = 50
==> passed

Test 14: call all methods in random order until almost all sites are open,
         but with inputs not prone to backwash
  * n = 3
  * n = 5
  * n = 7
  * n = 10
  * n = 20
  * n = 50
==> passed

Test 15: call all methods in random order until all sites are open,
         allowing open() to be called on a site more than once
         (these inputs are prone to backwash)
  * n = 3
  * n = 5
  * n = 7
  * n = 10
  * n = 20
  * n = 50
==> passed


Total: 15/15 tests passed!


================================================================
********************************************************************************
*  TESTING CORRECTNESS (substituting reference Percolation)
********************************************************************************

Testing correctness of PercolationStats
*-----------------------------------------------------------
Running 11 total tests.

Test 1: Test that PercolationStats creates trials Percolation objects, each of size n-by-n
  * n =  20, trials =  10
  * n =  50, trials =  20
  * n = 100, trials =  50
  * n =  64, trials = 150
==> passed

Test 2: Test that PercolationStats calls open() until system percolates
  * n =  20, trials =  10
  * n =  50, trials =  20
  * n = 100, trials =  50
  * n =  64, trials = 150
==> passed

Test 3: Test that PercolationStats does not call open() after system percolates
  * n =  20, trials =  10
  * n =  50, trials =  20
  * n = 100, trials =  50
  * n =  64, trials = 150
==> passed

Test 4: Test that mean() is consistent with the number of intercepted calls to open()
        on blocked sites
  * n =  20, trials =  10
    - PercolationStats mean = 2.371
    - total calls to open() / (N*N*T) = 0.5927499999999999
  * n =  50, trials =  20
    - PercolationStats mean = 14.802
    - total calls to open() / (N*N*T) = 0.5920799999999999
  * n = 100, trials =  50
    - PercolationStats mean = 59.2862
    - total calls to open() / (N*N*T) = 0.5928619999999998
  * n =  64, trials = 150
    - PercolationStats mean = 24.289466666666666
    - total calls to open() / (N*N*T) = 0.5930045572916667
==> FAILED

Test 5: Test that stddev() is consistent with the number of intercepted calls to open()
        on blocked sites
  * n =  20, trials =  10
    - student PercolationStats stddev()           = 0.2513076909996102
    - anticipated stddev   from intercepted calls = 0.06282692274990255
    - anticipated variance from intercepted calls = 0.003947222222222223
  * n =  50, trials =  20
    - student PercolationStats stddev()           = 0.6763514735619889
    - anticipated stddev   from intercepted calls = 0.02705405894247956
    - anticipated variance from intercepted calls = 7.319221052631582E-4
  * n = 100, trials =  50
    - student PercolationStats stddev()           = 1.4097858572369444
    - anticipated stddev   from intercepted calls = 0.014097858572369433
    - anticipated variance from intercepted calls = 1.9874961632653028E-4
  * n =  64, trials = 150
    - student PercolationStats stddev()           = 0.9199574666755906
    - anticipated stddev   from intercepted calls = 0.022459899088759536
    - anticipated variance from intercepted calls = 5.044470670772615E-4
==> FAILED

Test 6: Test that confidenceLo() and confidenceHigh() are consistent with mean() and stddev()
  * n =  20, trials =  10
  * n =  50, trials =  20
  * n = 100, trials =  50
  * n =  64, trials = 150
==> passed

Test 7: Check whether exception is thrown if either n or trials is out of bounds
  * n = -23, trials =  42
  * n =  23, trials =   0
  * n = -42, trials =   0
  * n =  42, trials =  -1
==> passed

Test 8: Create two PercolationStats objects at the same time and check mean()
        (to make sure you didn't store data in static variables)
  * n1 =  50, trials1 =  10, n2 =  50, trials2 =   5
    - PercolationStats object 1 mean = 14.514000000000001
    - total calls to open() / (N1*N1*T1)  = 0.58056
  * n1 =  50, trials1 =   5, n2 =  50, trials2 =  10
    - PercolationStats object 1 mean = 15.152000000000001
    - total calls to open() / (N1*N1*T1)  = 0.6060800000000001
  * n1 =  50, trials1 =  10, n2 =  25, trials2 =  10
    - PercolationStats object 1 mean = 14.735999999999999
    - total calls to open() / (N1*N1*T1)  = 0.58944
  * n1 =  25, trials1 =  10, n2 =  50, trials2 =  10
    - PercolationStats object 1 mean = 3.833
    - total calls to open() / (N1*N1*T1)  = 0.61328
  * n1 =  50, trials1 =  10, n2 =  15, trials2 = 100
    - PercolationStats object 1 mean = 14.856
    - total calls to open() / (N1*N1*T1)  = 0.5942400000000001
  * n1 =  15, trials1 = 100, n2 =  50, trials2 =  10
    - PercolationStats object 1 mean = 1.3343
    - total calls to open() / (N1*N1*T1)  = 0.5930222222222222
==> FAILED

Test 9: Check that the methods return the same value, regardless of
        the order in which they are called
  * n =  20, trials =  10
  * n =  50, trials =  20
  * n = 100, trials =  50
  * n =  64, trials = 150
==> passed

Test 10: Check for any calls to StdRandom.setSeed()
  * n = 20, trials = 10
  * n = 20, trials = 10
  * n = 40, trials = 10
  * n = 80, trials = 10
==> passed

Test 11: Check distribution of number of sites opened until percolation
  * n = 2, trials = 100000

            value  observed  expected   2*O*ln(O/E)
        -------------------------------------------
                2         0   33333.0          0.00
                3         0   66667.0          0.00
        -------------------------------------------
                          0  100000.0          0.00
    
    G-statistic = 0.00 (p-value = 0.000000, reject if p-value <= 0.0001)
    Note: a correct solution will fail this test by bad luck 1 time in 10,000.


  * n = 3, trials = 100000

            value  observed  expected   2*O*ln(O/E)
        -------------------------------------------
                3         0    3571.0          0.00
                4         0   13889.0          0.00
                5         0   29365.0          0.00
                6         0   32937.0          0.00
                7         0   20238.0          0.00
        -------------------------------------------
                          0  100000.0          0.00
    
    G-statistic = 0.00 (p-value = 0.000000, reject if p-value <= 0.0001)
    Note: a correct solution will fail this test by bad luck 1 time in 10,000.


  * n = 4, trials = 100000

            value  observed  expected   2*O*ln(O/E)
        -------------------------------------------
                4         0     220.0          0.00
                5         0    1154.0          0.00
                6         0    3497.0          0.00
                7         0    7822.0          0.00
                8         0   13850.0          0.00
                9         0   19542.0          0.00
               10         0   21522.0          0.00
               11         0   17924.0          0.00
               12         0   10733.0          0.00
               13         0    3736.0          0.00
        -------------------------------------------
                          0  100000.0          0.00
    
    G-statistic = 0.00 (p-value = 0.000000, reject if p-value <= 0.0001)
    Note: a correct solution will fail this test by bad luck 1 time in 10,000.


==> FAILED


Total: 7/11 tests passed!


================================================================
********************************************************************************
*  MEMORY (substituting reference Percolation)
********************************************************************************

Computing memory of PercolationStats
*-----------------------------------------------------------
Running 4 total tests.

Test 1a-1d: Memory usage as a function of trials for n = 100
            (max allowed: 8*trials + 128 bytes)

            trials        bytes
--------------------------------------------
=> passed       16          112         
=> passed       32          176         
=> passed       64          304         
=> passed      128          560         
==> 4/4 tests passed


Estimated student memory = 4.00 T + 48.00   (R^2 = 1.000)

Total: 4/4 tests passed!

================================================================



********************************************************************************
*  MEMORY
********************************************************************************

Computing memory of Percolation
*-----------------------------------------------------------
Running 4 total tests.

Test 1a-1d: Check that total memory <= 17 n^2 + 128 n + 1024 bytes

                 n        bytes
--------------------------------------------
=> passed       64        69920         
=> passed      256      1114400         
=> passed      512      4456736         
=> passed     1024     17826080         
==> 4/4 tests passed


Estimated student memory = 17.00 n^2 + 0.00 n + 288.00   (R^2 = 1.000)


Test 2 (bonus): Check that total memory <= 11 n^2 + 128 n + 1024 bytes
   -  failed memory test for n = 64
==> FAILED


Total: 4/4 tests passed!

================================================================



********************************************************************************
*  TIMING                                                                  
********************************************************************************

Timing Percolation
*-----------------------------------------------------------
Running 9 total tests.

Test 1a-1e: Create an n-by-n percolation system; open sites at random until
            the system percolates. Count calls to connected(), union() and
            find() in WeightedQuickUnionUF.
                                                 2 * connected()
                 n   seconds       union()              + find()        constructor
---------------------------------------------------------------------------------------------
=> passed        8     0.00          124                   164                   2         
=> passed       32     0.00         1501                  1866                   2         
=> passed      128     0.01        22515                 28834                   2         
=> passed      512     0.08       370380                474148                   2         
=> passed     1024     0.34      1457240               1864838                   2         
==> 5/5 tests passed

Running time in seconds depends on the machine on which the script runs,
and  varies each time that you submit. If one of the values in the table
violates the performance limits, the factor by which you failed the test
appears in parentheses. For example, (9.6x) in the union() column
indicates that it uses 9.6x too many calls.


Tests 2a-2d: Check whether number of calls to union(), connected(), and find()
             is a constant per call to open(), isFull(), and percolates().
             The table shows the maximum number of union(), connected(), and
             find() calls made during a single call to open(), isFull(), and
             percolates().

                 n     per open()      per isOpen()    per isFull()    per percolates() 
---------------------------------------------------------------------------------------------
=> passed       32        8               0               1               1         
=> passed      128        8               0               1               1         
=> passed      512        8               0               1               1         
=> passed     1024        8               0               1               1         
==> 4/4 tests passed

Total: 9/9 tests passed!

================================================================

