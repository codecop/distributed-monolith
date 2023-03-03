# Distributed Monolith Service 'shared H2'

This is an [H2 Database Server](http://h2database.com/).

## Running it locally

You can run it with the exec maven plugin like this:

```
cd shared-h2
../mvnw compile exec:java
```

Or you can run it from your IDE.

H2 is listening on `tcp://localhost:9095`. 
You can then access its web console UI here: http://localhost:8085/
