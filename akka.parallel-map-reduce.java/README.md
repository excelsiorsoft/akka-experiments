# akka.parallel-map-reduce.java

####This is an implementation of a parallel map/reduce on top of Akka.


**Task1:** we want to calculate the number of map entries in the distributed hashmap storage where length of their keys equals the lengths of their values (i.e. key-A[length=4], val-A[length=4]).

- To start - execute ApplicationTask1.main() in IDE (or console).

which returns:

    result: 2

-----------------------------
    
**Task2:** we want to calculate the number of map entries in the distributed hashmap storage values of which contain letter `D`.

- To start - execute ApplicationTask2.main() in IDE (or console).

which returns:

    result: 1
       
-----------------------------

**Task3:** we want to send a request to the distributed system which will copy the existing there persisted  hashtable.

- To start - execute ApplicationTask1.main() in IDE (or console).

which returns:

    result: {key-A=A, key-D=val-D, key-C=C, key-B=val-B}