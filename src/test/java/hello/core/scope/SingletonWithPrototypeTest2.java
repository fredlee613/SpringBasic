package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest2 {
    
    @Test
    public void singletonClientUsePrototype() throws Exception{
        // given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);
        //when
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        int logic1 = singletonBean1.logic();
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        int logic2 = singletonBean2.logic();
        //then
        assertThat(logic1).isEqualTo(1);
        assertThat(logic2).isEqualTo(2);

    }

    @Scope("singleton")
    static class SingletonBean {
        private final PrototypeBean prototypeBean;

        @Autowired
        public SingletonBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
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
