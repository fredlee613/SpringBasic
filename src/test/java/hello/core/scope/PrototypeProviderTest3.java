package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeProviderTest3 {
    
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
        private Provider<PrototypeBean> provider;
        // 뱔도의 라이브러리 필요
        // 자바 표준이므로 스프링이 아닌 라이브러리에서도 사용 가능

        public int logic() {
            PrototypeBean prototypeBean = provider.get();
            // 해당 매서드(.get()) 호출 시 스프링 컨테이너를 통하여 해당 빈을 반환 (DL)
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
