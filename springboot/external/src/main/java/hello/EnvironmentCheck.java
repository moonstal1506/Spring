package hello;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentCheck {

    private final Environment env;

    public EnvironmentCheck(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {

        String url = env.getProperty("url");
        String username = env.getProperty("username");
        String password = env.getProperty("password");

        //--url=devdb --username=dev_user --password=dev_pw mode=on
        //-Durl=devdb -Dusername=dev_user -Dpassword=dev_pw
        log.info("env url={}",url);
        log.info("env username={}",username);
        log.info("env password={}",password);
    }
}
