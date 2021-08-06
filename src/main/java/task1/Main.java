package task1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("task1/");
        context.refresh();
        ConsoleAccess console = context.getBean(ConsoleAccess.class);
        console.consoleAppUse();
    }
}

