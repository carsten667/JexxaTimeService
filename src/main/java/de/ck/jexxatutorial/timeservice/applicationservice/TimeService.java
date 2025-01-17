package de.ck.jexxatutorial.timeservice.applicationservice;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import de.ck.jexxatutorial.timeservice.domainservice.ITimePublisher;
import io.jexxa.utils.JexxaLogger;

public class TimeService
{
    private final ITimePublisher timePublisher;

    /**
     * Note: Jexxa supports only implicit constructor injection. Therefore, we must
     * declare all required interfaces in the constructor.
     *
     * @param timePublisher required outbound port for this application service
     */
    public TimeService(final ITimePublisher timePublisher)
    {
        this.timePublisher = timePublisher;
    }

    public LocalTime getTime()
    {
        return LocalTime.now();
    }

    public void publishTime()
    {
        timePublisher.publish(getTime());
    }

    public void timePublished(final LocalTime localTime)
    {
        var logMessage = localTime.format(DateTimeFormatter.ISO_TIME);
        JexxaLogger.getLogger(TimeService.class).info("New Time was published time {} ", logMessage);
    }
}

