Rem compile and run, expecting Java8 installed
javac _INPUT_VECTOR.java
javac _INTERSECTION_STATE.java
javac _LIGHT_STATES.java
javac _TRAFFIC_VECTOR.java
javac Car.java -Xlint:unchecked
javac Cars.java -Xlint:unchecked
javac DrawCanvas.java
javac Executive.java -Xlint:deprecation
javac Client1.java
javac TraffSym.java

java TraffSym
pause