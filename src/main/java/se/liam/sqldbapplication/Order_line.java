package se.liam.sqldbapplication;

public record Order_line(
        long id,
        long furnitureId,
        long orderId,
        int quantity
) {
    public Order_line(long furnitureId, int quantity) {
        this(0L, furnitureId,0L, quantity);
    }
}
