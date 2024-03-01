//package com.example.LabsM.controller;
//
//import com.example.LabsM.entity.*;
//import com.example.LabsM.memory.InMemoryStorage;
//import com.example.LabsM.service.DataBaseService;
//import com.example.LabsM.service.TriangleService;
//import com.example.LabsM.validator.TriangleValidator;
//import com.example.LabsM.service.CountService;
//import com.example.LabsM.service.UnSyncCountService;
//import com.example.LabsM.exception.InternalServerException;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.Mockito.*;
//
//public class ControllerTest {
//    private TriangleService service = mock(TriangleService.class);
//    private TriangleValidator validator = mock(TriangleValidator.class);
//    private InMemoryStorage storage = mock(InMemoryStorage.class);
//    private CountService countService = mock(CountService.class);
//    private UnSyncCountService unSyncCountService = mock(UnSyncCountService.class);
//    private DataBaseService dbService = mock(DataBaseService.class);
//    @InjectMocks
//    private TriangleController triangleController = new TriangleController(service, validator, storage,
//            countService, unSyncCountService, dbService);
//    @Test
//    public void testCalculations() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, perimeter = 30, area = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, perimeter, area);
//        TriangleError error = new TriangleError();
//
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doNothing().when(storage).addData(triangle);
//        doNothing().when(dbService).saveOneTriangle(any());
//        doReturn(0).when(dbService).checkInDb(any());
//        doReturn(null).when(storage).getAllData();
//        doReturn(0).when(countService).getNumber();
//        doReturn(false).when(storage).contains(any());
//
//        ResponseEntity<Object> result = triangleController.Calculations(num1, num2, num3);
//        Triangle resTriangle = ((Response)result.getBody()).getTriangle();
//        assertNotNull(result);
//        assertEquals(30, resTriangle.getArea());
//        verify(storage).addData(triangle);
//        verify(dbService).checkInDb(triangle);
//    }
//
//    @Test
//    public void testMemoryCheck() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, perimeter = 30, area = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, perimeter, area);
//        TriangleError error = new TriangleError();
//
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doNothing().when(storage).addData(triangle);
//        doReturn(null).when(storage).getAllData();
//        doReturn(0).when(countService).getNumber();
//        doReturn(true).when(storage).contains(any());
//        when(storage.getTriangleByKey(any())).thenReturn(triangle);
//
//        ResponseEntity<Object> result = triangleController.Calculations(num1, num2, num3);
//        Triangle resTriangle = ((Response)result.getBody()).getTriangle();
//        assertNotNull(result);
//        assertEquals(30, resTriangle.getArea());
//    }
//    @Test
//    public void testException() throws InternalServerException {
//        String num1 = "19", num2 = "5", num3 = "20";
//        double num4 = 19, num5 = 5, num6 = 20;
//        Triangle triangle = new Triangle(num4, num5, num6);
//        TriangleError error = new TriangleError();
//        doNothing().when(storage).addData(triangle);
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doReturn(false).when(storage).contains(any());
//
//        doReturn(null).when(storage).getAllData();
//        doReturn(0).when(countService).getNumber();
//
//        try {
//            ResponseEntity<Object> result = triangleController.Calculations(num1, num2, num3);
//        } catch (InternalServerException exception) {
//            assertNotNull(exception.getMessage());
//        }
//    }
//    @Test
//    public void testValidation() {
//        String num1 = "19", num2 = "5", num3 = "-21";
//        double num4 = 19, num5 = 5, num6 = -21;
//        Triangle triangle = new Triangle(num4, num5, num6);
//        TriangleError error = new TriangleError("Length can't be negative or zero!",
//                HttpStatus.INTERNAL_SERVER_ERROR.name());
//
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doNothing().when(storage).addData(triangle);
//        doReturn(null).when(storage).getAllData();
//        doReturn(0).when(countService).getNumber();
//        doReturn(false).when(storage).contains(any());
//
//        ResponseEntity<Object> result = triangleController.Calculations(num1, num2, num3);
//    }
//    @Test
//    public void testCache() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, num7 = 30, num8 = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, num7, num8);
//        TriangleError error = new TriangleError();
//        List<Triangle> resTr = new ArrayList<>();
//        resTr.add(triangle);
//
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doNothing().when(storage).addData(triangle);
//        when(storage.getAllData()).thenReturn(resTr);
//        doReturn(0).when(countService).getNumber();
//        doReturn(false).when(storage).contains(any());
//
//        triangleController.Calculations(num1, num2, num3);
//        ResponseEntity<Object> result = triangleController.getData();
//        List<Triangle> resList = (List<Triangle>)result.getBody();
//        assertNotNull(result);
//        assertEquals(30, resList.get(0).getPerimeter());
//    }
//    @Test
//    public void testCounter() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, num7 = 30, num8 = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, num7, num8);
//        int testRunNumber = 5;
//
//        doReturn(null).when(service).calculate(num4, num5, num6);
//        doReturn(null).when(validator).validateInput(num1, num2, num3);
//        doNothing().when(countService).reset();
//        doNothing().when(storage).addData(triangle);
//        doReturn(null).when(storage).getAllData();
//        when(countService.getNumber()).thenReturn(testRunNumber);
//        doReturn(false).when(storage).contains(any());
//
//        AmountEntity resNumber = (AmountEntity)triangleController.getCount().getBody();
//        assertNotNull(resNumber);
//        assertEquals(testRunNumber, resNumber.getAmount());
//    }
//    @Test
//    public void testCounterReset() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, num7 = 30, num8 = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, num7, num8);
//        int testRunNumber = 5;
//
//        doReturn(null).when(service).calculate(num4, num5, num6);
//        doReturn(null).when(validator).validateInput(num1, num2, num3);
//        doNothing().when(storage).addData(triangle);
//        doReturn(null).when(storage).getAllData();
//        when(countService.getNumber()).thenReturn(testRunNumber);
//        doNothing().when(countService).reset();
//        doReturn(false).when(storage).contains(any());
//
//        AmountEntity resNumber = (AmountEntity)triangleController.resetCount().getBody();
//        assertNotNull(resNumber);
//        assertEquals(testRunNumber, resNumber.getAmount());
//    }
//    @Test
//    public void testGetById() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, perimeter = 30, area = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, perimeter, area);
//        TriangleError error = new TriangleError();
//
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doNothing().when(storage).addData(triangle);
//        doNothing().when(dbService).saveOneTriangle(any());
//        doReturn(0).when(dbService).checkInDb(any());
//        doReturn(null).when(storage).getAllData();
//        doReturn(0).when(countService).getNumber();
//        doReturn(false).when(storage).contains(any());
//        when(dbService.getLastId()).thenReturn(5);
//        when(dbService.getById(3)).thenReturn(triangle);
//
//        ResponseEntity<Object> result = triangleController.getById(3);
//        Triangle resTriangle = (Triangle)result.getBody();
//        assertNotNull(result);
//        assertEquals(30, resTriangle.getArea());
//        verify(dbService).getLastId();
//    }
//    @Test
//    public void testAsyncCallInDb() {
//        String num1 = "12", num2 = "5", num3 = "13";
//        double num4 = 12, num5 = 5, num6 = 13, perimeter = 30, area = 30;
//        Triangle triangle = new Triangle(num4, num5, num6, perimeter, area);
//        TriangleError error = new TriangleError();
//
//        when(service.calculate(num4, num5, num6)).thenReturn(triangle);
//        when(validator.validateInput(num1, num2, num3)).thenReturn(error);
//        doNothing().when(storage).addData(triangle);
//        doNothing().when(dbService).saveOneTriangle(any());
//        doReturn(2).when(dbService).checkInDb(any());
//        doReturn(null).when(storage).getAllData();
//        doReturn(0).when(countService).getNumber();
//        doReturn(false).when(storage).contains(any());
//
//        ResponseEntity<Object> result = triangleController.AsyncCalculations(num1, num2, num3);
//        IdMessageEntity resultMessage = (IdMessageEntity)result.getBody();
//        assertNotNull(result);
//        assertEquals(2, resultMessage.getId());
//    }
//}
