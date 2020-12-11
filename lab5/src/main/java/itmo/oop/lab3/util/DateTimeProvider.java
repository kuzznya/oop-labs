package itmo.oop.lab3.util;

import java.time.ZonedDateTime;
import java.util.function.Consumer;

public interface DateTimeProvider {
    ZonedDateTime currentDateTime();
    void addDateChangeHook(Consumer<ZonedDateTime> hook);
    void stop();
}
