package itmo.oop.lab3.util;

import java.time.ZonedDateTime;

public interface DateTimeProvider {
    ZonedDateTime currentDateTime();
}
