Kafka Twitter Producer (Spring Boot)
======================

Consumes messages from [Twitter](https://twitter.com/) using [Twitter4j](http://twitter4j.org/en/index.html) library and sends the tweets into [Apache Kafka](https://kafka.apache.org/) topic.

Configuration
-------------

Configure you Twitter API credentials and Kafka broker in ``resources/application.yaml``. Default configuration
assumes that you have a topic named *twitter* present in your Kafka broker.


Build
-------------
This project is built using [Gradle](https://gradle.org/). If you do not have gradle installed on your system, just run 
``gradlew build``. This command will in-place download Gradle and will build the project. Resulting _jar_ 
file will be located under ``build/libs`` directory. 

Execution
-------------
``java -jar kafka-twitter-producer-0.0.1.jar``

Disclaimer
-------------
This code is not intended for production usage, it was written as a quick intro to Apache Kafka 
during [ZOOM International](https://www.zoomint.com/) Hackathon (Autumn 2017).
 
