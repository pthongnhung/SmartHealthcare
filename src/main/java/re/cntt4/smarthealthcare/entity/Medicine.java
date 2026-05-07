package re.cntt4.smarthealthcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "medicines")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Integer medicineId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Double price;

    // 1-N
    @OneToMany(mappedBy = "medicine")
    private List<PrescriptionDetail> details;
}