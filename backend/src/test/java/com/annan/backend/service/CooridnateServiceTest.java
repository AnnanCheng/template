package com.annan.backend.service;

import com.annan.backend.api.exception.CoordinateNotFoundException;
import com.annan.backend.api.request.CoordinateRequestDTO;
import com.annan.backend.api.response.CoordinateUpdateDTO;
import com.annan.backend.api.response.DistanceDTO;
import com.annan.backend.persistence.dao.CoordinateDao;
import com.annan.backend.persistence.entity.Coordinate;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.Optional;

public class CooridnateServiceTest {

    private final CoordinateService service;
    private final DistanceService distanceService;
    private final CoordinateDao dao;

    private final Coordinate coordinate1 = new Coordinate(1L, "1234AB", 51.88, 5.6);
    private final Coordinate coordinate2 = new Coordinate(2L, "4321CD", 51.87, 5.5);
    private final CoordinateRequestDTO coordinate3 = new CoordinateRequestDTO("1234AB", 52.88, 6.6);


    public CooridnateServiceTest() {
        this.dao = Mockito.mock(CoordinateDao.class);
        this.distanceService = Mockito.mock(DistanceService.class);;
        this.service = new CoordinateService(dao, distanceService);
    }

    @Test
    public void getDistanceWithValidInput() throws Exception {
        final String validPostCode_1 = "1234AB";
        final String validPostCode_2 = "4321CD";
        Mockito.when(dao.findByPostCode(validPostCode_1)).thenReturn(Optional.of(coordinate1));
        Mockito.when(dao.findByPostCode(validPostCode_2)).thenReturn(Optional.of(coordinate2));
        Mockito.when(distanceService.calculateDistance(51.88, 5.6, 51.87, 5.5)).thenReturn(1.0);

        DistanceDTO result = service.getDistance(validPostCode_1, validPostCode_2);

        Assert.assertEquals(coordinate1, result.getLocation_1());
        Assert.assertEquals(coordinate2, result.getLocation_2());
        Assert.assertTrue(Math.abs(1.0 - result.getDistance()) < 0.0001);
        Assert.assertEquals("km", result.getUnit());
    }

    @Test (expected = CoordinateNotFoundException.class)
    public void getDistanceWithInvalidInput() throws Exception {
        final String validPostCode_1 = "1234AB";
        final String validPostCode_2 = "4321CD";

        Mockito.when(dao.findByPostCode(validPostCode_1)).thenReturn(Optional.empty());
        Mockito.when(dao.findByPostCode(validPostCode_2)).thenReturn(Optional.of(coordinate2));

        service.getDistance(validPostCode_1, validPostCode_2);
    }

    @Test
    public void updateCoordinateWithValidInput() throws Exception {
        final String validPostCode_1 = coordinate3.getPostCode();
        Mockito.when(dao.findByPostCode(validPostCode_1)).thenReturn(Optional.of(coordinate1));
        Mockito.when(dao.save(coordinate1)).thenReturn(new Coordinate(1L, coordinate1.getPostCode(), 52.88, 6.6));

        CoordinateUpdateDTO result = service.updateCoordinate(coordinate3);

        Assert.assertEquals("coordinates are successfully updated", result.getMessage());

    }

    @Test (expected = CoordinateNotFoundException.class)
    public void updateCoordinateWithInvalidInput() throws Exception {
        final String validPostCode_1 = coordinate3.getPostCode();

        Mockito.when(dao.findByPostCode(validPostCode_1)).thenReturn(Optional.empty());

        service.updateCoordinate(coordinate3);
    }

    @Test
    public void updateCoordinateWithDatabaseFailure() throws Exception {
        final String validPostCode_1 = coordinate3.getPostCode();
        Mockito.when(dao.findByPostCode(validPostCode_1)).thenReturn(Optional.of(coordinate1));
        Mockito.when(dao.save(coordinate1)).thenThrow(DataRetrievalFailureException.class);

        CoordinateUpdateDTO result = service.updateCoordinate(coordinate3);

        Assert.assertEquals("an error occurred", result.getMessage());
    }

}
