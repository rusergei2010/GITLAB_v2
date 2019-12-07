package prepare;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Problem: "How to signal server app without REST api"
 * Use Mission Control to connect to JMX API
 *
 * Add VM params to the launch params:
 * -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false  -Djava.rmi.server.hostname=localhost
 *
 * Then use Mission Control to connect via port
 */
public class AppSimpleJMXAgent {

    private MBeanServer mbs = null;

    public AppSimpleJMXAgent() {

        if (System.getProperty("com.sun.management.jmxremote") == null) {
            System.out.println("JMX remote is disabled");
        } else {
            String portString = System.getProperty("com.sun.management.jmxremote.port");
            if (portString != null) {
                System.out.println("JMX running on port "
                        + Integer.parseInt(portString));
            }
        }

        mbs = ManagementFactory.getPlatformMBeanServer();

        //
        Hello helloBean = new Hello();
        ObjectName helloName = null;

        try {
            //
            helloName = new ObjectName("AppSimpleJMXAgentSomeNewObject:name=hellothere");
            mbs.registerMBean(helloBean, helloName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    private static void waitForEnterPressed() {
        try {
            System.out.println("Press <enter> to continue...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) {
        AppSimpleJMXAgent agent = new AppSimpleJMXAgent();
        System.out.println("AppSimpleJMXAgent is running...");
        AppSimpleJMXAgent.waitForEnterPressed();
    }

    public interface HelloMBean {

        public void setMessage(String message);

        public String getMessage();

        public void sayHello();
    }

    public class Hello implements HelloMBean {

        private String message = null;

        public Hello() {
            message = "Hello there";
        }

        public Hello(String message) {
            this.message = message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void sayHello() {
            System.out.println(message);
        }
    }
}