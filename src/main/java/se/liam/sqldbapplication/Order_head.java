package se.liam.sqldbapplication;

import java.time.LocalDate;

public record Order_head(
        long id,
        String orderDate,
        long customerId,
        long employeeId
) {

    public Order_head(String orderDate, long customerId, long employeeId){
        this(0L, orderDate, customerId, employeeId);
    }

}