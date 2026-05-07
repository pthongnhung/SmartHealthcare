package re.cntt4.smarthealthcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.cntt4.smarthealthcare.constant.Gender;

import java.time.LocalDate;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")   // đổi tên cột
    private Integer profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

}