package re.cntt4.smarthealthcare.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import re.cntt4.smarthealthcare.constant.AppointmentStatus;
import re.cntt4.smarthealthcare.entity.Appointment;
import re.cntt4.smarthealthcare.entity.Doctor;
import re.cntt4.smarthealthcare.entity.Patient;
import re.cntt4.smarthealthcare.repository.AppointmentRepository;
import re.cntt4.smarthealthcare.repository.DoctorRepository;
import re.cntt4.smarthealthcare.repository.PatientRepository;
import re.cntt4.smarthealthcare.service.IAppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public Appointment bookAppointment(Integer patientId, Integer doctorId, LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Không thể đặt lịch trong quá khứ");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bác sĩ"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân"));

        if (appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(doctor, date, time)) {
            throw new IllegalArgumentException("Bác sĩ đã có lịch hẹn trong khung giờ này");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setSpecialty(doctor.getSpecialty());
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch hẹn"));
    }

    @Override
    public void cancel(Integer id) {
        Appointment appointment = getById(id);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public Page<Appointment> getAllAppointments(String search, Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    @Override
    public Integer findPatientIdByEmail(String email) {
        Patient patient = patientRepository.findByUser_Profile_Email(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân với email: " + email));
        return patient.getPatientId();
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Integer patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }


}
