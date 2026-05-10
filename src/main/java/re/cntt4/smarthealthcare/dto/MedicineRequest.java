package re.cntt4.smarthealthcare.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class MedicineRequest {
    private Integer medicineId;

    @NotBlank(message = "Tên thuốc không được để trống")
    @Size(max = 100, message = "Tên thuốc không vượt quá 100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả không vượt quá 255 ký tự")
    private String description;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho phải >= 0")
    private Integer stockQuantity;

    @NotBlank(message = "Đơn vị tính không được để trống")
    private String unit;

    @NotNull(message = "Giá thuốc không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá thuốc phải > 0")
    private Double price;
}