package chat_bot.chat_bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;


@Configuration
@Slf4j
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
            log.info("Port is 0, Assign a dynamic port...");
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
                log.info("Port : " + port + "is already in use",e);
            }
        }
        throw new IllegalStateException("No available ports ");
    }


}
