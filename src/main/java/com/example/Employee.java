package com.example;

import io.micronaut.core.annotation.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import javax.validation.constraints.NotBlank;

@Introspected
@ReflectiveAccess
public class Employee {

    @NonNull
    @NotBlank
    @BsonProperty("name")
    private final String name;

    @Nullable
    @BsonProperty("email")
    private final String email;

    public Employee(@NonNull String name) {
        this(name,null);
    }

    @Creator
    @BsonCreator
    public Employee(@BsonProperty("name") String name, @Nullable @BsonProperty("email") String email) {
        this.name = name;
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
