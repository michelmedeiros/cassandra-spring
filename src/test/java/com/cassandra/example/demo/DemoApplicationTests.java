package com.cassandra.example.demo;

import com.cassandra.example.demo.repository.PersonRepository;
import com.cassandra.example.demo.repository.entity.Person;
import com.cassandra.example.demo.repository.entity.PersonKey;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private PersonRepository repository;

	@BeforeClass
	public static void startCassandraEmbedded() throws InterruptedException, IOException, TTransportException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		Cluster cluster = Cluster.builder()
				.addContactPoints("127.0.0.1").withPort(9142).build();
		Session session = cluster.connect();
	}

	@AfterClass
	public static void stopCassandraEmbedded() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}

	@Test
	public void contextLoads() {
		PersonKey key = new PersonKey("Michel", LocalDateTime.now(), UUID.randomUUID());
		Person person = new Person(key, "Medeiros", 6000D);
		repository.save(person);
	}

}
