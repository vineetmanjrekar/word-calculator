# word-calculator

This is a Spring Boot based REST App that calculates the number of words in a file. 

## How to run

Please download the repository code.
Then run " ./gradlew clean build distZip" from the parent directory.
Once built, please go to /build/distributions. Then unzip the word-calculator-boot.zip.
Please go into the unzipped folder and under bin run "./word-calculator" to get the app up and running at port 8080.


## How to use

The App has exposed several endpoints to get results.

1. /word-calculator/word/count/{fileId}   [GET] ---> To get word counts in file with Id {fieldId}
2. /word-calculator/process/word/count/{fileId} [POST] --> To start processing the file with Id {fieldId} to start counting the words.
