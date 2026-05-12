package re.cntt4.smarthealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.smarthealthcare.constant.PrescriptionStatus;
import re.cntt4.smarthealthcare.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    Optional<Prescription> findByMedicalRecord_RecordId(Integer recordId);

}

