package com.cassandra.example;

import com.cassandra.example.repository.PersonRepository;
import com.cassandra.example.repository.entity.Person;
import com.cassandra.example.repository.entity.PersonKey;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

	private static final Log LOGGER = LogFactory.getLog(PersonRepositoryTest.class);

	public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS test_cassandra WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

	public static final String KEYSPACE_ACTIVATE_QUERY = "USE test_cassandra;";

	public static final String DATA_TABLE_NAME = "person_by_first_name";

	public static final String KEYSPACE = "test_cassandra";


	@Autowired
	private PersonRepository personRepository;

	@BeforeClass
	public static void startCassandraEmbedded()
			throws InterruptedException, TTransportException, ConfigurationException, IOException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
		LOGGER.info("Server Started at 127.0.0.1:9142... ");
		final Session session = cluster.connect();
		session.execute(KEYSPACE_CREATION_QUERY);
		session.execute(KEYSPACE_ACTIVATE_QUERY);
		LOGGER.info("KeySpace created and activated.");
		Thread.sleep(5000);
	}

	@Test
	public void test() {
		PersonKey key = new PersonKey("Michel", LocalDateTime.now(), UUID.randomUUID());
		Person person = new Person(key, "Medeiros", 6000D);
		personRepository.deleteAll();
		personRepository.save(person);
		final Iterable<Person> persons = personRepository.findByKeyFirstName("Michel");
		assertEquals(person.getKey(), persons.iterator().next().getKey());
	}
}