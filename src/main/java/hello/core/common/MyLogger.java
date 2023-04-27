package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;
    private String time;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        time = LocalDateTime.now().toString();
        System.out.println("[" + time + "] [" + uuid + "] [" + requestURL +"] " + message);
    }

    @PostConstruct
    public void init() {
        time = LocalDateTime.now().toString();
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + time + "] [" + uuid + "] request scope bean created: " + this);
    }

    @PreDestroy
    public void close() {
        time = LocalDateTime.now().toString();
        System.out.println("[" + time + "] [" + uuid + "] request scope bean close: " + this);
    }
}
