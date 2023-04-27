package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeProviderTest1 {
    
    @Test
    public void singletonClientUsePrototype() throws Exception{
        // given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        //when
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int logic1 = clientBean1.logic();
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int logic2 = clientBean2.logic();
        //then
        assertThat(logic1).isEqualTo(1);
        assertThat(logic2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        @Autowired
        ApplicationContext ac;
        // ApplicationContext 주입 시 스프링 컨테이너에 종속적인 코드가 됨
        // 단위 테스트가 어려움

        public int logic() {
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            // DI가 아닌 DL(Dependency Lookup : 의존관계 탐색, 직접 필요한 의존관계를 찾는 것)
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
