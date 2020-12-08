package itmo.oop.lab3.util;

import java.time.ZonedDateTime;

public class DefaultDateTimeProvider implements DateTimeProvider {
    @Override
    public ZonedDateTime currentDateTime() {
        return ZonedDateTime.now();
    }
}
