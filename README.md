
# equals() and contains() methods for char sequences ignoring difference between Greek-Latin characters
Not something try-hard. Useful for simple searches in applications. For example the string `θα πάω για καφέ` contains the string `kafe`. 
Letters with Greek [diacritics](https://en.wikipedia.org/wiki/Greek_diacritics) are considered equal to the Latin corresponding letters. 
With other words all these are equal: `υ`, `ύ`, `ϋ`, `ΰ`, `u`. [Dipthongs][1] are also taken in consideration. 
The string `θα φάω ψάρι` is equals to `tha faw psari`. 
The ignorance happens in both directions, meaning `η πέtra` contains `peτρα`. Take a look to `Tester.java` to see other cases.

The algorithm uses the [naive][2] approach of string searching. In terms of performance, the algorithm is not close to JDK's [String#contains][3] method. 
JDK's version uses the [String#indexOf][4] method which is [intrinsic][5]. 
That said, it is recommended to use the ordinary `s1.contains(s2)` (or `equals`) before doing `GreeklishUtils.contains(s1, s2)` since `s1.contains(s2)` runs super fast.

If you find a bug or something wrong with it, let me know.


  [1]: https://en.wikipedia.org/wiki/Diphthong
  [2]: https://en.wikipedia.org/wiki/String-searching_algorithm
  [3]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#contains-java.lang.CharSequence-
  [4]: http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/lang/String.java#l1740
  [5]: https://en.wikipedia.org/wiki/Intrinsic_function
