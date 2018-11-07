#!/bin/bash

CQL="DROP keyspace test_cassandra;
CREATE KEYSPACE test_cassandra WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'} AND durable_writes = true;
CREATE TABLE test_cassandra.table_test (search_hash text, PRIMARY KEY (search_hash));"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"