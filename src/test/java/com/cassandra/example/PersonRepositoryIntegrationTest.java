package com.cassandra.example;

import com.cassandra.example.config.CassandraConfig;
import com.cassandra.example.repository.PersonRepository;
import com.cassandra.example.repository.entity.Person;
import com.cassandra.example.repository.entity.PersonKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PersonRepositoryIntegrationTest {
    private static final Log LOGGER = LogFactory.getLog(PersonRepositoryIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS test_cassandra WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE test_cassandra;";

    public static final String DATA_TABLE_NAME = "person_by_first_name";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

//    @BeforeClass
//    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
//        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
//        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
//        LOGGER.info("Server Started at 127.0.0.1:9142... ");
//        final Session session = cluster.connect();
//        session.execute(KEYSPACE_CREATION_QUERY);
//        session.execute(KEYSPACE_ACTIVATE_QUERY);
//        LOGGER.info("KeySpace created and activated.");
//        Thread.sleep(5000);
//    }

//    @Before
//    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
//        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Person.class, new HashMap<String, Object>());
//    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
        PersonKey key = new PersonKey("Michel", LocalDateTime.now(), UUID.randomUUID());
        Person person = new Person(key, "Medeiros", 6000D);
        personRepository.deleteAll();
        personRepository.save(person);
        final Iterable<Person> persons = personRepository.findByKeyFirstName("Michel");
        assertEquals(person.getKey(), persons.iterator().next().getKey());
    }
//
//    @Test
//    public void whenUpdatingBooks_thenAvailableOnRetrieval() {
//        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        personRepository.save(ImmutableSet.of(javaBook));
//        final Iterable<Book> books = personRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
//        javaBook.setTitle("Head First Java Second Edition");
//        personRepository.save(ImmutableSet.of(javaBook));
//        final Iterable<Book> updateBooks = personRepository.findByTitleAndPublisher("Head First Java Second Edition", "O'Reilly Media");
//        assertEquals(javaBook.getTitle(), updateBooks.iterator().next().getTitle());
//    }

//    @Test(expected = java.util.NoSuchElementException.class)
//    public void whenDeletingExistingBooks_thenNotAvailableOnRetrieval() {
//        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        personRepository.save(ImmutableSet.of(javaBook));
//        personRepository.delete(javaBook);
//        final Iterable<Book> books = personRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
//        assertNotEquals(javaBook.getId(), books.iterator().next().getId());
//    }

//    @Test
//    public void whenSavingBooks_thenAllShouldAvailableOnRetrieval() {
//        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        final Book dPatternBook = new Book(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
//        personRepository.save(ImmutableSet.of(javaBook));
//        personRepository.save(ImmutableSet.of(dPatternBook));
//        final Iterable<Book> books = personRepository.findAll();
//        int bookCount = 0;
//        for (final Book book : books) {
//            bookCount++;
//        }
//        assertEquals(bookCount, 2);
//    }

//    @After
//    public void dropTable() {
//        adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
//    }
//
//    @AfterClass
//    public static void stopCassandraEmbedded() {
//        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
//    }

}