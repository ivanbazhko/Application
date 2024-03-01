//package com.example.LabsM.controller;
//
//import com.example.LabsM.entity.*;
//import com.example.LabsM.exception.InternalServerException;
//import com.example.LabsM.memory.InMemoryStorage;
//import com.example.LabsM.service.*;
//import com.example.LabsM.validator.TriangleValidator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.doReturn;
//
//public class BulkControllerTest {
//    private TriangleService service = mock(TriangleService.class);
//    private TriangleValidator validator = mock(TriangleValidator.class);
//    private InMemoryStorage storage = mock(InMemoryStorage.class);
//    private CountService countService = mock(CountService.class);
//    private UnSyncCountService unSyncCountService = mock(UnSyncCountService.class);
//    private StatsService statsService = mock(StatsService.class);
//    private DataBaseService dbService = mock(DataBaseService.class);
//    @InjectMocks
//    private TriangleBulkController triangleController = new TriangleBulkController(service, validator, storage,
//            countService, unSyncCountService, statsService, dbService);
//    @Test
//    public void testCalculations() {
//        List<TriangleParams> rawParams = new ArrayList<>();
//        List<Response> listResp = new ArrayList<>();
//        List<Response> listResp1 = new ArrayList<>();
//        Triangle triangle1 = new Triangle(19, 5, 21);
//        Triangle triangle2 = new Triangle(7, 8, 9);
//        String num1 = "19", num2 = "5", num3 = "21", num4 = "7", num5 = "8", num6 = "9", num7 = "abc", num8 = "9", num9 = "5";
//
//        listResp.add(new Response(triangle1, new TriangleError()));
//        listResp.add(new Response(triangle2, new TriangleError()));
//        listResp.add(new Response(null, new TriangleError("Wrong", "Status")));
//        listResp1.add(new Response(new Triangle(19, 5, 21, 45, 50),
//                new TriangleError()));
//        listResp1.add(new Response(new Triangle(7, 8, 9, 26, 30),
//                new TriangleError()));
//        listResp1.add(new Response(null,
//                new TriangleError("Wrong", "Status")));
//        rawParams.add(new TriangleParams(num1, num2, num3));
//        rawParams.add(new TriangleParams(num4, num5, num6));
//        rawParams.add(new TriangleParams(num7, num8, num9));
//
//        when(validator.validateMultipleTriangles(rawParams)).thenReturn(listResp);
//        when(service.calculateList(listResp)).thenReturn(listResp1);
//        doReturn(null).when(statsService).getAllStats(listResp1);
//        doNothing().when(storage).addData((List<Response>) any());
//        doNothing().when(dbService).saveAllTriangles(listResp1);
//
//        ResponseEntity<Object> response = triangleController.countAllTriangles(rawParams);
//        ListResponse newResponse = ((ListResponse)response.getBody());
//
//        assertNotNull(newResponse);
//        assertEquals(3, newResponse.getResult().size());
//        assertEquals(26, newResponse.getResult().get(1).getTriangle().getPerimeter());
//        assertEquals(19, newResponse.getResult().get(0).getTriangle().getSide1());
//        assertEquals("Wrong", newResponse.getResult().get(2).getError().getMessage());
//        verify(storage).addData(listResp1);
//        verify(statsService).getAllStats(listResp1);
//        verify(dbService).saveAllTriangles(listResp1);
//    }
//    @Test
//    public void testGetDb() {
//        List<Triangle> response = new ArrayList<>();
//        response.add(new Triangle(1, 2, 3, 4, 5));
//        when(dbService.getAllTriangles()).thenReturn(response);
//
//        ResponseEntity<Object> result = triangleController.getDb();
//        List<Triangle> newResponse = ((List<Triangle>)result.getBody());
//        assertNotNull(newResponse);
//        assertEquals(1, newResponse.size());
//        assertEquals(3, newResponse.get(0).getSide3());
//    }
//    @Test
//    public void testEnCacheDb() {
//        List<Triangle> response = new ArrayList<>();
//        response.add(new Triangle(1, 2, 3, 4, 5));
//        when(dbService.getAllTriangles()).thenReturn(response);
//        doNothing().when(storage).addData3(any());
//
//        ResponseEntity<Object> result = triangleController.enCacheDb();
//        List<Triangle> newResponse = ((List<Triangle>)result.getBody());
//        assertNotNull(newResponse);
//        assertEquals(1, newResponse.size());
//        assertEquals(3, newResponse.get(0).getSide3());
//        verify(storage).addData3(response);
//    }
//}
