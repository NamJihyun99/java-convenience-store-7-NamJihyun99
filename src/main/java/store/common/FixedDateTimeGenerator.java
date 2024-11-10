package store.common;

import java.time.LocalDateTime;

public class FixedDateTimeGenerator implements DateTimeGenerator {

    @Override
    public LocalDateTime generate() {
        return LocalDateTime.of(2024, 11, 10, 15, 30);
    }
}
