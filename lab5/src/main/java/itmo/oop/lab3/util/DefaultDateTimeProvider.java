package itmo.oop.lab3.util;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class DefaultDateTimeProvider implements DateTimeProvider {

    private final List<Consumer<ZonedDateTime>> hooks = new ArrayList<>();

    public DefaultDateTimeProvider() {
        Timer t = new Timer();
        t.schedule(new DateCheckerTask(), 3_600_000, 3_600_000);
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

    private class DateCheckerTask extends TimerTask {

        private ZonedDateTime previous = currentDateTime();

        @Override
        public void run() {
            if (previous.getDayOfWeek() == currentDateTime().getDayOfWeek())
                return;

            synchronized (hooks) {
                for (var hook : hooks)
                    hook.accept(currentDateTime());
            }

            previous = currentDateTime();
        }
    }
}
