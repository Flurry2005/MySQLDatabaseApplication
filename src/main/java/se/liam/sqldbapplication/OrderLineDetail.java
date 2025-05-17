package se.liam.sqldbapplication;

public record OrderLineDetail(
        long id,
        long furnitureId,
        long orderId,
        int quantity,
        String furnitureName
) {}