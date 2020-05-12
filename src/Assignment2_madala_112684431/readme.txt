SalaryStdDev.java:
--------------------------------
Command Used:
    javac SalaryStdDev.java
    java -cp ../../../Downloads/db2jcc-db2jcc4.jar:. SalaryStdDev SAMPLE CSE532.EMPLOYEE DB2INST1 GD1OJfLGG64HV2dtwK
Command Explanation:
    java -cp <--PathToJar--> SalartStdDev <--Database--> <--TableName--> <--User--> <--Password-->
Result:
    23594.349287558864



stddev.sql:
---------------------------------
Command Used:
    db2 -td@ -f stddev.sql
Command Explanation:
    db2 -td@ -f <sql file>
Result:
    DB20000I  The SQL command completed successfully.

    DB20000I  The SQL command completed successfully.


      Value of output parameters
      --------------------------
      Parameter Name  : STANDARDDEVIATION
      Parameter Value : +2.35943453776751E+004

      Return Status = 0