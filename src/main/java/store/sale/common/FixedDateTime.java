package store.sale.common;

import java.time.LocalDateTime;

public class FixedDateTime implements DateTime {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(2024, 11, 10, 15, 30);
    }
}
