# akka.upper-caser.java

- To start - execute Application.main() in IDE (or console).
       
Then start typing words and when done - press [Enter]


[![Output will look like this:][1]][1]


  [1]: https://s16.postimg.org/kcafmg61x/Capture.jpg


- To stop - type "**exit**" on the console

**N.B.** Be careful when giving more than 5 words to uppercase to

 `com.excelsiorsoft.akka.uppercaser.routed.java.Application.java`, 

Since its router only has 5 threads and each next execution hangs its own thread (via spin-locking) it will hang the whole system (100% CPU utilization) after the 5th request is sent.  Painful!
