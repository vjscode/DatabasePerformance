# DatabasePerformance

## Why does this exist?
Whenever you are in a position to choose a DB for your next Android app, you would always hope there is someone who has done a comparitive analysis of all the popular databses in the market. This tool aims to fullfill exactly that.

As a first step, I'm starting with Realm and SnappyDB(LevelDB). Hope to see more including couchbase lite and the good old sqlite

Operation | Database | Type | Time taken |
----------|----------|------|------------|
Bulk inserts (1000 rows, each with 100 element list) | Realm | NoSQL | 10.23s |
Bulk inserts (1000 rows, each with 100 element list) | SnappyDB | NoSQL | 16.13s |
