package hello.core.web;

import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    private final AnnotationConfigApplicationContext ac;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        System.out.println("myLogger = " + myLogger.getClass());
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);
        myLogger.log(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }

}
