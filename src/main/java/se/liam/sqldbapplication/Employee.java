package se.liam.sqldbapplication;

public record Employee(
        long id,
        String address,
        String city,
        String firstName,
        String lastName,
        String postalCode
) {}