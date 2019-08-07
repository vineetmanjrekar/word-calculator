# word-calculator

This is a Spring Boot based REST App that calculates the number of words in a file. 

## How to run

Please download the repository code.
Then run " ./gradlew clean build distZip" from the parent directory.
Once built, please go to /build/distributions. Then unzip the word-calculator-boot.zip.
Please go into the unzipped folder and under bin run "./word-calculator" to get the app up and running at port 8080.


## How to use

The App has exposed several API endpoints for App Usage.

1. /word-calculator/file/upload/{fileId} [POST] --> To upload the file. Use key "file" to upload file.
2. /word-calculator/word/count/{fileId}   [GET] ---> To get word counts in file with Id {fieldId}
3. /word-calculator/process/word/count/{fileId} [POST] --> To start processing the file with Id {fieldId} to start counting the words.
4. /word-calculator/word/count/min/{fileId} [GET] --> Get the word with minimum word count in the file.
5. /word-calculator/word/count/max/{fileId} [GET] --> Get the word with maximum word count in the file.
6. /word-calculator/word/count/{fileId}/{word} [GET] --> Get the word count for {word} in the file {fileId}


## Steps to Use

1. Use API 1 mentioned above to upload a document into Mongo DB. This endpoint stores the file to be uploaded in chunks of 10MB each. (As MongoDB restriction on a maximum document size is 16MB)

2. Use API 3 to process the file we just uploaded.

3. Use any remainder of the APIs listed to get results.


## Technologies Used
1. Spring Boot with MongoDB

## Architecture:

1. The entire solution is available via APIs to be used.
2. The solution uses the MapReduce feature to calculate the word count and then store them as keys in a different collection.
3. Inorder to make use of the multi run, the concept of chunk processing is followed. [In Progress]

#### Parts of The System

##### File Uploader

The API (1) uploads the file into Mongo Database in chunks of 10MB each. The maximum file size supported currently is 1.28GB.
The maximum size of a Mongo Collection allowed and result processing size allowed in MapReduce is 16MB. Hence chunk size is 10MB. This is currently hard coded but can be externalised.

##### Word Processor

The API (2) runs the MapReduce on the documents matching the FileID. Currently it works for files upto 16MB. The results are then written into a "wordCount" collection so that we can read it from that collection to get results.


##### Results API

The other APIs do read only from "wordCount" collection that gives back results based on fileID and/or word in a file.


## Limitations/ Things TO DO

1. The processing limit is 16MB for a file.
2. The ability to process large files could not be completed in time.


