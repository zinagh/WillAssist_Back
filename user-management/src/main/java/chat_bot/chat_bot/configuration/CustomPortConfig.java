package chat_bot.chat_bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;


@Configuration
public class CustomPortConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${MIN_PORT}")
    private Integer minPort;
    @Value("${Max_PORT}")
    private Integer maxPort;

    @Value("${server.port}")
    private String initialPort;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory){
        if(initialPort.equals("0")) {
            int availablePort = findAvailablePort();
            factory.setPort(availablePort);
            System.getProperties().put("server.port", availablePort);
        }
    }

    private int findAvailablePort(){
        for (int port = minPort; port <= maxPort; port++) {
            try(ServerSocket socket = new ServerSocket(port)){
                return port;
            } catch(IOException e) {
            }
        }
        throw new IllegalStateException("No available ports ");
    }


}
