package se.liam.sqldbapplication;

public record Order_line(
        long id,
        long furnitureId,
        long orderId,
        int quantity
) {}