package com.annan.backend.service;

import com.annan.backend.api.exception.CoordinateNotFoundException;
import com.annan.backend.api.response.CoordinateUpdateDTO;
import com.annan.backend.api.response.DistanceDTO;
import com.annan.backend.api.request.CoordinateRequestDTO;
import com.annan.backend.persistence.dao.CoordinateDao;
import com.annan.backend.persistence.entity.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoordinateService {

    private final String SUCCESS_MSG = "coordinates are successfully updated";
    private final String FAILED_MSG = "an error occurred";
    private final String POSTCODE_NOT_FOUND = "One or more post code not found.";

    private final CoordinateDao dao;
    private final DistanceService distanceService;

    @Autowired
    public CoordinateService(final CoordinateDao dao, final DistanceService distanceService) {
        this.dao = dao;
        this.distanceService = distanceService;
    }

    public DistanceDTO getDistance(String postCode1, String postCode2) throws CoordinateNotFoundException {
        Optional<Coordinate> location1Opt = dao.findByPostCode(postCode1);
        Optional<Coordinate> location2Opt = dao.findByPostCode(postCode2);

        if (!(location1Opt.isPresent() && location2Opt.isPresent())) {
            throw new CoordinateNotFoundException(POSTCODE_NOT_FOUND);
        }

        Coordinate location1 = location1Opt.get();
        Coordinate location2 = location2Opt.get();

        Double distance = distanceService.calculateDistance(
                location1.getLatitude(), location1.getLongitude(),
                location2.getLatitude(), location2.getLongitude());

        return DistanceDTO.builder()
                .location_1(location1)
                .location_2(location2)
                .distance(distance)
                .unit("km")
                .build();
    }

    @Transactional
    public CoordinateUpdateDTO updateCoordinate(CoordinateRequestDTO requestDTO) throws CoordinateNotFoundException {
        Optional<Coordinate> existingCoordinateOpt = dao.findByPostCode(requestDTO.getPostCode());

        if (!existingCoordinateOpt.isPresent()) {
            throw new CoordinateNotFoundException(POSTCODE_NOT_FOUND);
        }

        Coordinate existingCoordinate = existingCoordinateOpt.get();

        existingCoordinate.setLatitude(requestDTO.getLatitude());
        existingCoordinate.setLongitude(requestDTO.getLongitude());

        try {
            Coordinate updatedCoordinate = dao.save(existingCoordinate);
            return CoordinateUpdateDTO.builder()
                    .message(SUCCESS_MSG)
                    .postCode(updatedCoordinate.getPostCode())
                    .latitude(updatedCoordinate.getLatitude())
                    .longitude(updatedCoordinate.getLongitude())
                    .build();
        } catch (DataAccessException e) {
            return CoordinateUpdateDTO.builder().message(FAILED_MSG).build();
        }
    }
}
