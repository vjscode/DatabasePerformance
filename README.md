# DatabasePerformance

## Why does this exist?
Whenever you are in a position to choose a DB for your next Android app, you would always hope there is someone who has done a comparitive analysis of all the popular databases in the market. This tool aims to fullfill exactly that.

As a first step, I'm starting with Realm and SnappyDB(LevelDB) and the good old sqlite. Hope to see more including couchbase lite.

- Database inserts were in the form of 1000 rows, each with 100 element list in them. Check out model folder to see details of the data that are inserted.
- Database query time includes the time to deserialize and create POJOs

Operation | Database | Type | Time taken |
----------|----------|------|------------|
Bulk inserts | Realm | NoSQL | 10.23s |
Bulk inserts | SnappyDB | NoSQL | 16.13s |
Bulk inserts | Sqlite | SQL | 6.13s |
Query | Realm | NoSQL | 100.3ms |
Query | SnappyDB | NoSQL | 20.3s |
Query | Sqlite | SQL | 3.14s |
