package de.ck.jexxatutorial.timeservice.domainservice;

import java.time.LocalTime;

public interface ITimePublisher
{
    void publish(final LocalTime localTime);
}
