#!/bin/bash

CQL="DROP keyspace test_cassandra;
CREATE KEYSPACE test_cassandra WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'} AND durable_writes = true;
CREATE TABLE test_cassandra.people_by_first_name(
  first_name TEXT,
  date_of_birth TIMESTAMP,
  person_id UUID,
  last_name TEXT,
  salary DOUBLE,
  PRIMARY KEY (first_name, date_of_birth, person_id)
) WITH CLUSTERING ORDER BY (date_of_birth ASC, person_id DESC);"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"