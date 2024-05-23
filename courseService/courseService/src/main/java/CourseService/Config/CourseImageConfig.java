package CourseService.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CourseImageConfig {

    @Bean
    public Cloudinary getCloudinary(){

        Map config = new HashMap();

        config.put("cloud_name", "dedh4e4jc");
        config.put("api_key","578249346185147");
        config.put("api_secret","dkGO1LC5nj2YNZcambkZflK8WYA");
        config.put("secure",true);

        return new Cloudinary(config);
    }
}
