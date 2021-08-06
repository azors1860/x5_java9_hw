package task2;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Service
@PropertySource("classpath:clinic.properties")
@Getter
@Log
public class VeterinaryClinicService implements VeterinarClinic {
    private final MedicalWorker medicalWorker;
    private final String clinicName;
    private boolean isOpen = false;


    public void treatAllAnimal(List<Animal> sickAnimalList) {
        for (Animal animal : sickAnimalList) {
            medicalWorker.healAnimal(animal);
        }
    }

    public VeterinaryClinicService(MedicalWorker medicalWorker, @Value("${clinic.name}") String clinicName) {
        this.medicalWorker = medicalWorker;
        this.clinicName = clinicName;
    }

    @Override
    public void openClinic() {
        isOpen = true;
    }
}
