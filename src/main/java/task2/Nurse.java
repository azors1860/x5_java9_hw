package task2;

import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Data
@Log
@PropertySource("classpath:clinic.properties")
public class Nurse implements MedicalWorker {

    private String name;
    private ConfigurableApplicationContext context;

    public Nurse(@Value("${nurse.name}") String name, ConfigurableApplicationContext context) {
        this.name = name;
        this.context = context;
    }

    private void init(){
        openClinic();
    }

    @Override
    @PostConstruct
    //Lifecycle method annotation requires a no-arg method
    public void openClinic() {
        log.info("Клиника открыта");
        VeterinarClinic clinic = context.getBean(VeterinaryClinicService.class);
        clinic.openClinic();
    }


    @Override
    @PreDestroy
    public void closeClinic() {
        context.close();
        log.info("Клиника закрыта");
    }

    @Override
    public void healAnimal(Animal animal) {
        animal.healing();
    }
}

