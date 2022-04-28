package com.example;

import io.micronaut.core.annotation.NonNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EmployeeRepository {

    @NonNull
    Publisher<Employee> list();

    Mono<Boolean> save(@NonNull @NotNull @Valid Employee employee);
}
