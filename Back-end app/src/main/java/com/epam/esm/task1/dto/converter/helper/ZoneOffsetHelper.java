package com.epam.esm.task1.dto.converter.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Helper-class for converting between LocalDateTime and OffsetDateTime.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class ZoneOffsetHelper {
    @Value("${app.timeOffset}")
    private String applicationTimeOffset;

    /**
     * Converts LocalDateTime into OffsetDateTime using the application time offset.
     *
     * @param localDateTime an instance of the LocalDateTime class
     * @return an instance of the OffsetDateTime class
     */
    public OffsetDateTime getOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        ZoneOffset applicationZoneOffset = ZoneOffset.of(applicationTimeOffset);

        return localDateTime.atOffset(applicationZoneOffset);
    }

    /**
     * Converts OffsetDateTime into LocalDateTime using the ZoneOffset's of application and of the given offsetDateTime.
     *
     * @param offsetDateTime an instance of the OffsetDateTime class
     * @return an instance of the LocalDateTime class
     */
    public LocalDateTime getLocalDateTime(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }

        ZoneOffset applicationZoneOffset = ZoneOffset.of(applicationTimeOffset);
        ZoneOffset dtoZoneOffset = offsetDateTime.getOffset();

        long offsetDifferenceInSeconds = applicationZoneOffset.getTotalSeconds() - dtoZoneOffset.getTotalSeconds();

        return offsetDateTime.plusSeconds(offsetDifferenceInSeconds).toLocalDateTime();
    }
}
