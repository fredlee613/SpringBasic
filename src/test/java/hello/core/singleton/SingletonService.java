package hello.core.singleton;

public class SingletonService {

    // static으로 설정 시 class level로 올라가서 하나만 생성됨.
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
