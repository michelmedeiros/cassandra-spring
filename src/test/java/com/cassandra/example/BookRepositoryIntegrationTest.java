package com.cassandra.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import com.cassandra.example.config.SpringCassandraConfig;
import com.cassandra.example.repository.PersonRepository;
import com.cassandra.example.repository.entity.Person;
import com.cassandra.example.repository.entity.PersonKey;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCassandraConfig.class)
public class BookRepositoryIntegrationTest {
    private static final Log LOGGER = LogFactory.getLog(BookRepositoryIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS test_cassandra WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE test_cassandra;";

    public static final String DATA_TABLE_NAME = "person_by_first_name";

    public static final String KEYSPACE = "test_cassandra";

    @Autowired
    private PersonRepository repository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    //

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Person.class, new HashMap<String, Object>());
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
        PersonKey key = new PersonKey("Michel", LocalDateTime.now(), UUID.randomUUID());
        Person person = new Person(key, "Medeiros", 6000D);
        repository.save(person);
        final Iterable<Person> persons = repository.findByKeyFirstName("Michel");
        assertEquals(person.getKey(), persons.iterator().next().getKey());
    }

//    @Test
//    public void whenUpdatingBooks_thenAvailableOnRetrieval() {
//        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        repository.save(ImmutableSet.of(javaBook));
//        final Iterable<Book> books = repository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
//        javaBook.setTitle("Head First Java Second Edition");
//        repository.save(ImmutableSet.of(javaBook));
//        final Iterable<Book> updateBooks = repository.findByTitleAndPublisher("Head First Java Second Edition", "O'Reilly Media");
//        assertEquals(javaBook.getTitle(), updateBooks.iterator().next().getTitle());
//    }
//
//    @Test(expected = java.util.NoSuchElementException.class)
//    public void whenDeletingExistingBooks_thenNotAvailableOnRetrieval() {
//        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        repository.save(ImmutableSet.of(javaBook));
//        repository.delete(javaBook);
//        final Iterable<Book> books = repository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
//        assertNotEquals(javaBook.getId(), books.iterator().next().getId());
//    }
//
//    @Test
//    public void whenSavingBooks_thenAllShouldAvailableOnRetrieval() {
//        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        final Book dPatternBook = new Book(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        repository.save(ImmutableSet.of(javaBook));
//        repository.save(ImmutableSet.of(dPatternBook));
//        final Iterable<Book> books = repository.findAll();
//        int bookCount = 0;
//        for (final Book book : books) {
//            bookCount++;
//        }
//        assertEquals(bookCount, 2);
//    }

    @After
    public void dropTable() {
//        adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

}
