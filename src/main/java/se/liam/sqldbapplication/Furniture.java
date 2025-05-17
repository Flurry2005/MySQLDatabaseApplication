package se.liam.sqldbapplication;

import java.time.LocalDate;

public record Furniture(
        long id,
        String color,
        String comment,
        String name,
        double price,
        LocalDate purchaseDate,
        int shelfNbr,
        double weight
) {}