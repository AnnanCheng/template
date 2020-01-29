package com.annan.backend.api.response;

import com.annan.backend.persistence.entity.Coordinate;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistanceDTO {
    private Coordinate location_1;
    private Coordinate location_2;
    private Double distance;
    private String unit;
}
