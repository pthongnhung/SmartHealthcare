package re.cntt4.smarthealthcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.cntt4.smarthealthcare.constant.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "doctor_id", "appointment_date", "appointment_time"
        }))
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;


    // 1-1
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;
}