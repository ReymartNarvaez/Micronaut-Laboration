package com.example;

import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.micronaut.http.HttpStatus.CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@MicronautTest
@TestInstance(PER_CLASS)
class EmployeeControllerTest implements TestPropertyProvider {

    @Inject
    EmployeeClient employeeClient;

    @Test
    void employeesEndpointInteractsWithMongo() {

        List<Employee> employees = employeeClient.findAll();
        assertTrue(employees.isEmpty());

        HttpStatus status = employeeClient.save(new Employee("Mike"));
        assertEquals(CREATED, status);

        employees = employeeClient.findAll();
        assertFalse(employees.isEmpty());
        assertEquals("Mike", employees.get(0).getName());
        assertNull(employees.get(0).getEmail());

        status = employeeClient.save(new Employee("Kalle", "kalle@email.test"));
        assertEquals(CREATED, status);

        employees = employeeClient.findAll();
        assertTrue(employees.stream().anyMatch(f -> "kalle@email.test".equals(f.getEmail())));
    }

    @AfterAll
    static void cleanup() {
        MongoDbUtils.closeMongoDb();
    }

    @Override
    public Map<String, String> getProperties() {
        MongoDbUtils.startMongoDb();
        return Collections.singletonMap("mongodb.uri", MongoDbUtils.getMongoDbUri());
    }

}