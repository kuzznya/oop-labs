package itmo.oop.lab3.util;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DefaultDateTimeProvider implements DateTimeProvider {

    private final List<Consumer<ZonedDateTime>> hooks = new ArrayList<>();

    @SuppressWarnings("BusyWait")
    public DefaultDateTimeProvider() {
        Thread dateWatcher = new Thread(() -> {
            while (true) {
                ZonedDateTime previous = currentDateTime();

                while (previous.getDayOfWeek() == currentDateTime().getDayOfWeek()) {
                    try {
                        Thread.sleep(3_600_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (hooks) {
                    for (var hook : hooks)
                        hook.accept(currentDateTime());
                }
            }
        });
    }

    @Override
    public ZonedDateTime currentDateTime() {
        return ZonedDateTime.now();
    }

    @Override
    public void addDateChangeHook(Consumer<ZonedDateTime> hook) {
        synchronized (hooks) {
            hooks.add(hook);
        }
    }
}
