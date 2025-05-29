package org.hohoho.cheer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
//@Theme("default")
@CssImport("./themes/styles/styles.css")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        // Remove the invalid Native.cleanup() call
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Optionally log shutdown events or perform other cleanup tasks
            System.out.println("Application is shutting down...");
        }));

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // You can also use Clock.systemUTC()
    }
}