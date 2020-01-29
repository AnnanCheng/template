package com.annan.backend.api.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateRequestDTO {

    @NotEmpty(message = "Post Code cannot be empty.")
    @Pattern(regexp = "[1-9][0-9]{3}[a-zA-Z]{2}", message = "Invalid Post Code.")
    private String postCode;

    @NotNull(message = "latitude cannot be null.")
    @Max(value = 90, message = "Invalid latitude.")
    @Min(value = -90, message = "Invalid latitude.")
    private Double latitude;

    @NotNull(message = "longitude cannot be null.")
    @Max(value = 180, message = "Invalid longitude.")
    @Min(value = -180, message = "Invalid longitude.")
    private Double longitude;
}
