package rw.schoolpass.config;
import org.springframework.context.annotation.Configuration; import org.springframework.web.servlet.config.annotation.*; import org.springframework.beans.factory.annotation.Value; import java.nio.file.*;
@Configuration public class StaticResourceConfig implements WebMvcConfigurer { @Value("${schoolpass.upload-dir:./data/uploads}") String uploadDir; @Override public void addResourceHandlers(ResourceHandlerRegistry registry){ registry.addResourceHandler("/uploads/**").addResourceLocations(Paths.get(uploadDir).toAbsolutePath().toUri().toString()); } }
