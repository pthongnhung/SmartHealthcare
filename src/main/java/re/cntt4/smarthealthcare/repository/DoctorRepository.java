package re.cntt4.smarthealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.smarthealthcare.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findBySpecialty_SpecialtyId(Integer specialtyId);
    Optional<Doctor> findByUser_Profile_Email(String email);
}
