package de.ck.jexxatutorial.timeservice.infrastructure.drivenadapter.console;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import de.ck.jexxatutorial.timeservice.domainservice.ITimePublisher;
import io.jexxa.utils.JexxaLogger;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

@SuppressWarnings("unused")
public class ConsolePublisher implements ITimePublisher
{
    private static final Logger LOGGER = JexxaLogger.getLogger(ConsolePublisher.class);

    @Override
    public void publish(final LocalTime localTime)
    {
        Validate.notNull(localTime);

        var logMessage = localTime.format(DateTimeFormatter.ISO_TIME);

        LOGGER.info(logMessage);
    }
}
