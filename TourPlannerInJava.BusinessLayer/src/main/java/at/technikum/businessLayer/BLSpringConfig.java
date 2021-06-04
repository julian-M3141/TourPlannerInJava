package at.technikum.businessLayer;


import at.technikum.dataAccess.DALSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "at.technikum")
@Import(DALSpringConfig.class)
public class BLSpringConfig {
}
