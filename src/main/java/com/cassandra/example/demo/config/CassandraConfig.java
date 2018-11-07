package com.cassandra.example.demo.config;

import com.datastax.driver.core.Cluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    public static final String KEYSPACE_DEFAULT = "test_cassandra";


    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keySpace;

    @Value("${cassandra.basePackages}")
    private String basePackages;



    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {basePackages};
    }

//    @Bean
//    public CassandraClusterFactoryBean cluster() {
//        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
//        cluster.setPort(9142);
//        cluster.setContactPoints("127.0.0.1");
//        cluster.setClusterName("cassandra0");
//
//        return  cluster;
//    }
//
//    @Bean
//    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
//        return new BasicCassandraMappingContext();
//    }
}
