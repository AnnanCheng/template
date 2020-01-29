package com.annan.backend.api.response;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinateUpdateDTO {
    private String message;
    private String postCode;
    private Double latitude;
    private Double longitude;
}
