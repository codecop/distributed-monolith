# Distributed Monolith Service 'Kafka'

This is an [Apache Kafka](https://kafka.apache.org/) broker.

## Running it locally

You can run it with the exec maven plugin like this:

```
cd kafka
../mvnw compile exec:java
```

Or you can run it from your IDE.

### Notes on local installation using Kraft

* [Installing Apache Kafka without Zookeeper Easy Steps 101](https://hevodata.com/learn/kafka-without-zookeeper/)
* [How to easily install kafka without zookeeper - Aditya’s Blog](https://adityasridhar.com/posts/how-to-easily-install-kafka-without-zookeeper)
* [Apache Kafka Installation and Usage](https://www.sobyte.net/post/2023-02/apache-kafka/)
* [KAFKA-14273: Kafka doesn't start with KRaft on Windows #12763](https://github.com/apache/kafka/pull/12763/commits/e3ba06be23925e373eb2597cb9acb10d4993e9ba)
