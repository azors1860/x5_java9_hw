package task2;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("task2/");

        MedicalWorker medicalWorker = context.getBean(MedicalWorker.class);
        List<Animal> cats = Arrays.asList(
                context.getBean(Animal.class),
                context.getBean(Animal.class),
                context.getBean(Animal.class),
                context.getBean(Animal.class),
                context.getBean(Animal.class)
        );

        VeterinarClinic clinic = context.getBean(VeterinaryClinicService.class);
        clinic.treatAllAnimal(cats);
        medicalWorker.closeClinic();
    }
}
