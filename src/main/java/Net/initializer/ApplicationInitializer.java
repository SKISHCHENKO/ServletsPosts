package Net.initializer;

import Net.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        final var context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.scan("Net");

        // Создаем DispatcherServlet с контекстом
        final var servlet = new DispatcherServlet(context);

        // Регистрация DispatcherServlet
        final var registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }
}