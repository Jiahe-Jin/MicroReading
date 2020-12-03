run: compile
	java -cp .:jars/* Launch

compile: CompNode.class DataAnalysis.class DataStorage.class Image.class ImageProcessor.class ImageTable.class LinearRgression.class Launch.class

clean:
	$(RM) *.class
	$(RM) *~
	$(RM) *#
CompNode.class: CompNode.java
	javac -cp .:jars/* CompNode.java
DataAnalysis.class: DataAnalysis.java
	javac -cp .:jars/* DataAnalysis.java
DataStorage.class: DataStorage.java
	javac -cp .:jars/* DataStorage.java
Image.class: Image.java
	javac -cp .:jars/* Image.java	
ImageProcessor.class: ImageProcessor.java
	javac -cp .:jars/* ImageProcessor.java
ImageTable.class: ImageTable.java
	javac -cp .:jars/* ImageTable.java
LinearRgression.class: LinearRegression.java
	javac -cp .:jars/* LinearRegression.java
Launch.class: Launch.java
	javac -cp .:jars/* Launch.java

