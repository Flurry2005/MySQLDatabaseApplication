package se.liam.sqldbapplication;

import java.time.LocalDate;

public record Order_head(
        long id,
        String orderDate,
        long customerId,
        long employeeId
) {}