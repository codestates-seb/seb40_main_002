package main.project.server.config;

import lombok.RequiredArgsConstructor;
import main.project.server.annotation.QueryStringArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${images.upload-path}")
    private String uploadPath;

    private final QueryStringArgumentResolver argumentResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String os = System.getProperty("os.name").toLowerCase();
        String resourceLocation = "";

        if (os.contains("win")) {
            resourceLocation += "file:///";

        } else if (os.contains("mac")) {
            resourceLocation += "file:///";

        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {

        } else if (os.contains("linux")) {

        } else if (os.contains("sunos")) {
        }

       resourceLocation += uploadPath; //파일을 실제 저장하는 경로

        registry
                .addResourceHandler("/images/**")
                .addResourceLocations(resourceLocation);


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }
}