package com.example;

import jakarta.inject.Singleton;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.core.annotation.NonNull;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Singleton
public class MongoDbEmployeeRepository implements EmployeeRepository {

    private final MongoDbConfiguration mongoDbConfiguration;
    private final MongoClient mongoClient;

    public MongoDbEmployeeRepository(MongoDbConfiguration mongoDbConfiguration, MongoClient mongoClient) {
        this.mongoDbConfiguration = mongoDbConfiguration;
        this.mongoClient = mongoClient;
    }

    @NonNull
    private MongoCollection<Employee> getCollection() {
        return mongoClient.getDatabase(mongoDbConfiguration.getName())
                .getCollection(mongoDbConfiguration.getCollection(), Employee.class);
    }

    @Override
    public Mono<Boolean> save(@NonNull @NotNull @Valid Employee employee) {
        return Mono.from(getCollection().insertOne(employee))
                .map(insertOneResult -> true)
                .onErrorReturn(false);
    }

    @Override
    @NonNull
    public Publisher<Employee> list() {
        return getCollection().find();
    }


}
