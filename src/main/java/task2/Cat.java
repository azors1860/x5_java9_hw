package task2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Log
@Scope("prototype")
@NoArgsConstructor
@Setter
@Getter
public class Cat implements Animal {
    private boolean isSick = true;

    @PostConstruct
    public void say() {
        log.info("meow");
    }

    @Override
    public void healing() {
        isSick = false;
    }
}