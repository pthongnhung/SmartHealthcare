package re.cntt4.smarthealthcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")   // khóa chính
    private Integer doctorId;

    // 1-1
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // khóa ngoại
    private User user;

    // N-1
    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false) // khóa ngoại
    private Specialty specialty;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "experience_years")
    private Integer experienceYears;

    // 1-N
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}