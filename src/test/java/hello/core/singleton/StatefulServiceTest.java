package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    @Test
    @DisplayName("Stateful service error")
    void statefulServiceSingleton() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        //when
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        int userAPrice = statefulService1.order("userA", 10000);
        int userBPrice = statefulService2.order("userB", 20000);

        //then
        assertThat(userAPrice).isEqualTo(10000);
        assertThat(userBPrice).isEqualTo(20000);

    }


    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}
