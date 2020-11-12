run: compile
	java Test

compile: ImageTank.class Test.class

test:

ImageTank.class: ImageTank.java
	javac -classpath .:ij.jar ImageTank.java
Test.class: Test.java
	javac Test.java
clean:
	$(RM) *.class
	$(RM) *~