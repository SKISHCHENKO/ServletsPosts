package Net.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

@Configuration
public class WebConfig  implements WebMvcConfigurer {
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        final var bean = new RequestMappingHandlerAdapter();
        // MappingJackson2HttpMessageConverter
        bean.getMessageConverters().add(new GsonHttpMessageConverter());
        return bean;
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Очищаем список и добавляем только Gson-конвертер
        converters.clear();
        converters.add(new GsonHttpMessageConverter());
    }
}