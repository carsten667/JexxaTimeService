package de.ck.jexxatutorial.timeservice;

import de.ck.jexxatutorial.timeservice.applicationservice.TimeService;
import de.ck.jexxatutorial.timeservice.infrastructure.drivingadapter.messaging.PublishTimeListener;
import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivingadapter.jmx.JMXAdapter;
import io.jexxa.infrastructure.drivingadapter.messaging.JMSAdapter;
import io.jexxa.infrastructure.drivingadapter.rest.RESTfulRPCAdapter;

public final class TimeServiceApplication
{
    //Declare the packages that should be used by Jexxa
    private static final String JMS_DRIVEN_ADAPTER = TimeServiceApplication.class.getPackageName() + ".infrastructure.drivenadapter.messaging";
    private static final String CONSOLE_DRIVEN_ADAPTER = TimeServiceApplication.class.getPackageName() + ".infrastructure.drivenadapter.console";
    private static final String OUTBOUND_PORTS = TimeServiceApplication.class.getPackageName() + ".domainservice";

    public static void main(String[] args)
    {
        JexxaMain jexxaMain = new JexxaMain("TimeService");

        jexxaMain
                //Define which outbound ports should be managed by Jexxa
                .addToApplicationCore(OUTBOUND_PORTS)

                //Define the driving adapter that should which implementation of the outbound port should be used by Jexxa.
                //Note: We must only register a single driven adapter for the outbound port
                .addToInfrastructure(getDrivenAdapter(args));

        // If JMS is enabled bind 'JMSAdapter' to our application
        // Note: Jexxa's JMSAdapter is a so called specific driving adapter which cannot be directly connected directly
        // to an inbound port because we cannot apply any convention. In this case bind Jexxa's specific driving adapter
        // 'JMSAdapter' to an application specific DrivingAdapter which is `PublishTimeListener`
        if (isJMSEnabled(args))
        {
            jexxaMain.bind(JMSAdapter.class).to(PublishTimeListener.class);
        }


        //The rest of main is similar to tutorial HelloJexxa
        jexxaMain
                // Bind RESTfulRPCAdapter and JMXAdapter to TimeService class so that we can invoke its method
                .bind(RESTfulRPCAdapter.class).to(TimeService.class)
                .bind(JMXAdapter.class).to(TimeService.class)

                .bind(JMXAdapter.class).to(jexxaMain.getBoundedContext())
                .bind(RESTfulRPCAdapter.class).to(jexxaMain.getBoundedContext())

                .start()

                .waitForShutdown()

                .stop();
    }

    private static String getDrivenAdapter(String[] args)
    {
        if (isJMSEnabled(args))
        {
            return JMS_DRIVEN_ADAPTER;
        }

        return CONSOLE_DRIVEN_ADAPTER;
    }

    private static boolean isJMSEnabled(String[] args)
    {
        return args.length == 1 && "j".equals(args[0]);
    }


}
