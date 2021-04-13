BackEndDeveloper README for Project Three (CS400 @ UW Madison)
========================================================

Name of BackEndDeveloper: Elliott Weinshenker
@wisc.edu Email of BackEndDeveloper: eweinshenker@wisc.edu
Group: KB
Team: Red

Files Written by Me:
--------------------
 
 Backend.java
 The data access layer for the application Golden Eagle Path. Creates a weighted directed 
 graph where the vertices represent US National Parks. This class contains methods for 
 accessing the overall cost weighted directed edges along paths and storing these paths in 
 an abstract structure for future reference. 
 
 BackendInterface.java
 
 This ADT represents the backend structure for storing and returning objects from the graph
 of vertices representing United States National Parks. 
 
 BackendTests.java
 Contains the unit test for Backend.java

Additional Contributions:
-------------------------
 
 Assisted in rewriting code for ParkDataReader after designing a more efficient 
 solution for merging contents of two files. Initially, the merging of two files was performed
 in isolation by the ParkDataReader class by combining a list of lists containing meta-data
 about the graph structure and a second file containing the data to be stored in each node.
 I developed an approach that stored the initial graph structure in a HashMap and subsequently 
 updated the contents of the HashMap with the information from the second file, improving the 
 complexity from O(N) to O(1). 

Signature:
----------
<Type out your full name here to certify that all of the files written by you
 that are listed above are the product of your individual development efforts
 for this programming assignment.  List below your name, any exceptions, for 
 example: work reused from a previous semester, code examples taken from any 
 website or book, or anything that was not explicitly authored by you for
 the purpose of completing this assigned CS400 project.>
 
 Elliott Weinshenker
