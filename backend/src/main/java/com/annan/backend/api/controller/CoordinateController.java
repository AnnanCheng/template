package com.annan.backend.api.controller;

import com.annan.backend.api.exception.CoordinateNotFoundException;
import com.annan.backend.api.response.CoordinateUpdateDTO;
import com.annan.backend.api.response.DistanceDTO;
import com.annan.backend.api.request.CoordinateRequestDTO;
import com.annan.backend.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CoordinateController {

    private final CoordinateService coordinateService;

    @Autowired
    public CoordinateController(final CoordinateService coordinateService) {
        this.coordinateService = coordinateService;
    }

    @GetMapping(value = "/distance")
    public DistanceDTO getDistance(@RequestParam("postCode_1") String postCode_1,
                                   @RequestParam("postCode_2") String postCode_2) throws CoordinateNotFoundException {
        return coordinateService.getDistance(postCode_1, postCode_2);
    }

    @PostMapping(value = "/update")
    public CoordinateUpdateDTO updateCoordinate(@Valid @RequestBody CoordinateRequestDTO requestDTO) throws CoordinateNotFoundException {
        return coordinateService.updateCoordinate(requestDTO);
    }
}
