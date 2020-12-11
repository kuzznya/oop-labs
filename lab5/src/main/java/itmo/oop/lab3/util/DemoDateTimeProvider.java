package itmo.oop.lab3.util;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DemoDateTimeProvider implements DateTimeProvider {

    private ZonedDateTime current = ZonedDateTime.now();

    private final List<Consumer<ZonedDateTime>> hooks = new ArrayList<>();

    public void nextDay() {
        current = current.plusDays(1);
        hooks.forEach(hook -> hook.accept(current));
    }

    public void nextMonth() {
        ZonedDateTime previous = current;
        while (previous.getMonthValue() == current.getMonthValue()) {
            previous = current;
            nextDay();
        }
    }

    @Override
    public ZonedDateTime currentDateTime() {
        return current;
    }

    @Override
    public void addDateChangeHook(Consumer<ZonedDateTime> hook) {
        synchronized (hooks) {
            hooks.add(hook);
        }
    }

    @Override
    public void stop() {

    }
}
