package se.liam.sqldbapplication;

import java.time.LocalDate;
import java.util.List;

public record OrderDetail(
        long id,
        String orderDate,
        long customerId,
        long employeeId,
        String customerName,
        List<OrderLineDetail>orderLines
) {
    public OrderDetail(long id, String orderDate, long customerId, long employeeId, String customerName){
        this(id, orderDate, customerId, employeeId, customerName, null);
    }
}