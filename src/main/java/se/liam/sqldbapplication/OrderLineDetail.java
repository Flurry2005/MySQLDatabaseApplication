package se.liam.sqldbapplication;

public record OrderLineDetail(
        long id,
        int quantity,
        long furnitureId,
        long orderId,
        String furnitureName
) {
    public OrderLineDetail(long id, int quantity, long furnitureId, String furnitureName){
        this(id, quantity, furnitureId, 0L,furnitureName);
    }
}