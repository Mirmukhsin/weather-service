package pdp.domains;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    private Integer id;
    @NotBlank(message = "cityName.null")
    private String name;
    @NotBlank(message = "countryName.null")
    private String country;
    @NotBlank(message = "population.null")
    private String population;
}
