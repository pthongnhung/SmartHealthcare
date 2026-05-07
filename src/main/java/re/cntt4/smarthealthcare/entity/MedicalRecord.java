package re.cntt4.smarthealthcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medical_records")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Integer recordId;

    @OneToOne
    @JoinColumn(name = "appointment_id", unique = true, nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "diagnosis")
    private String diagnosis;


    // 1-1
    @OneToOne(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private Prescription prescription;
}