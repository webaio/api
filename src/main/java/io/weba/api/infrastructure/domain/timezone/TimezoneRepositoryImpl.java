package io.weba.api.infrastructure.domain.timezone;

import io.weba.api.domain.timezone.TimezoneRepository;
import io.weba.api.domain.timezone.Timezones;
import io.weba.api.domain.timezone.Timezone;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;

@Repository
public class TimezoneRepositoryImpl implements TimezoneRepository {
    @Override
    public Timezones findAll() {
        Timezones timezones = new Timezones();
        String[] ids = TimeZone.getAvailableIDs();

        for (String id : ids) {
            timezones.add(new Timezone(this.formatTimezone(SimpleTimeZone.getTimeZone(id))));
        }

        return timezones;
    }

    private String formatTimezone(TimeZone timeZone) {
        Long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(timeZone.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
        minutes = Math.abs(minutes);

        if (hours > 0) {
            return String.format("(GMT+%d:%02d) %s", hours, minutes, timeZone.getID());
        }

        return String.format("(GMT%d:%02d) %s", hours, minutes, timeZone.getID());
    }
}
