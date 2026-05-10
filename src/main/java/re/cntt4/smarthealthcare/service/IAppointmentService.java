package re.cntt4.smarthealthcare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import re.cntt4.smarthealthcare.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IAppointmentService {
    Appointment bookAppointment(Integer patientId, Integer doctorId, LocalDate date, LocalTime time);
    List<Appointment> getAll();
    Appointment getById(Integer id);
    void cancel(Integer id);
    Page<Appointment> getAllAppointments(String search, Pageable pageable);

    Integer findPatientIdByEmail(String email);
    List<Appointment> getAppointmentsByPatientId(Integer patientId);

}
