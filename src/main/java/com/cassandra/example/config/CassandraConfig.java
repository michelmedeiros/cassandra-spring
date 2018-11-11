package com.cassandra.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

//@Configuration
//@PropertySource(value = { "application.properties" })
//@EnableCassandraRepositories(basePackages = { "com.cassandra.example.repository" })
//@EntityScan("com.cassandra.example.repository.entity")
public class CassandraConfig {//extends AbstractCassandraConfiguration {

//    @Value("${cassandra.keyspace}")
//    private String keySpace;
//
//    @Value("${cassandra.contactpoints}")
//    private String contactpoints;
//
//    @Value("${cassandra.port}")
//    private String port;
//
//    @Bean
//    public CassandraClusterFactoryBean cluster() {
//
//        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
//        cluster.setContactPoints(contactpoints);
//        return cluster;
//    }
//
//    @Bean
//    public CassandraMappingContext mappingContext() throws ClassNotFoundException {
//        BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
//        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), keySpace));
//        mappingContext.setInitialEntitySet(
//                CassandraEntityClassScanner.scan(new String[] { "com.cassandra.example.repository.entity" }));
//        return mappingContext;
//    }
//
//    @Bean
//    public CassandraConverter converter() throws ClassNotFoundException {
//        return new MappingCassandraConverter(mappingContext());
//    }
//
//    @Bean
//    public CassandraSessionFactoryBean session() {
//        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
//        session.setCluster(cluster().getObject());
//        session.setKeyspaceName(keySpace);
//        try {
//            session.setConverter(converter());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        session.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
//        return session;
//    }
//
//    @Override
//    protected String getKeyspaceName() {
//        return keySpace;
//    }
}