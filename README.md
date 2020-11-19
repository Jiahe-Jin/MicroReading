# MicroReading

**Introduction**

This application works for the Urine Stone Risk Detector to record, analyze pictures being uploaded, and finally gives out the statistic results for the data read from token photoes. 

**Author** ***Jiahe Jin***

**Update Date** ***2020.11.12***

**Current Status**

The framework has been built for our designed application which functions to upload, store, and analyze the image token in the photobox. Because we are going to compare three color measured regions (Nessler’s Reagent and Arsenazo III with their control baseline), the application must have the method to compare colors. Now, besides the grayscale converting method, the color comparison method has been implemented by using pixels of Red, Green, and Blue to calculate the metric difference in the color space. It is also called the Euclidean distance [3] that is the standard means of determining color difference between two pictures. To facilitate image storage, the app is serializable, so that images are not necessary to be stored in the folder with some image-specific tags like .jpeg, .png, .tiff, and some else. All of the images will be stored in one hidden cache file with special data format which can only be read by the app.  Other methods, especially like terminal-related operations, will be continuously implemented in the app. 
