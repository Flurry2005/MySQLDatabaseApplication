package se.liam.sqldbapplication;

import java.time.LocalDate;

public record OrderDetail(
        long id,
        String orderDate,
        long customerId,
        long employeeId,
        String customerName
) {}