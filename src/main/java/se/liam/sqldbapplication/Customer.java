package se.liam.sqldbapplication;

import java.time.LocalDate;

public record Customer(
        long id,
        String address,
        String birthDate,
        String city,
        String firstName,
        String lastName,
        String postalCode
) {}