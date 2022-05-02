package com.example;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.http.HttpStatus.CONFLICT;
import static io.micronaut.http.HttpStatus.CREATED;

@Controller("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeService;

    public EmployeeController(EmployeeRepository employeeService) {
        this.employeeService = employeeService;
    }

    @Get
    Publisher<Employee> list() {
        return employeeService.list();
    }

    @Post
    Mono<HttpStatus> save(@NonNull @NotNull @Valid Employee employee) {
        return employeeService.save(employee)
                .map(added -> added ? CREATED : CONFLICT);
    }
}
