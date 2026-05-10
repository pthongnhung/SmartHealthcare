package re.cntt4.smarthealthcare.repository.authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.smarthealthcare.entity.Appointment;
import re.cntt4.smarthealthcare.entity.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(Doctor doctor, LocalDate date, LocalTime time);
    List<Appointment> findByPatient_PatientId(Integer patientId);

}
