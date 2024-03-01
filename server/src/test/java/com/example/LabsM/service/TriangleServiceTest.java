//package com.example.LabsM.service;
//
//import com.example.LabsM.memory.InMemoryStorage;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.http.HttpStatus;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//
//public class TriangleServiceTest {
//    private CountService countService = mock(CountService.class);
//    private InMemoryStorage inMemoryStorage = mock(InMemoryStorage.class);
//    @InjectMocks
//    private TriangleService service = new TriangleService(countService, inMemoryStorage);
//    @Test
//    public void testWithSides() {
//        double num1 = 12, num2 = 5, num3 = 13;
//        Triangle result = service.calculate(num1, num2, num3);
//        assertNotNull(result);
//        assertEquals(30, result.getPerimeter());
//        assertEquals(30, result.getArea());
//    }
//    @Test
//    public void testWithTriangle() {
//        double num1 = 12, num2 = 5, num3 = 13;
//        Triangle triangle = new Triangle(4, 3, 5);
//        Triangle result = service.calculate(triangle);
//        assertNotNull(result);
//        assertEquals(12, result.getPerimeter());
//        assertEquals(6, result.getArea());
//    }
//    @Test
//    public void testWithList() {
//        doNothing().when(countService).increment();
//        List<Response> data = new ArrayList<>();
//        data.add(new Response(new Triangle(4, 3, 5), new TriangleError("", "")));
//        data.add(new Response(new Triangle(5, 3, 5), new TriangleError("", "")));
//        data.add(new Response(new Triangle(4, 3, 5), new TriangleError("Something Wrong",
//                HttpStatus.BAD_REQUEST.name())));
//        data.add(new Response(new Triangle(6, 3, 5), new TriangleError("", "")));
//        data.add(new Response(new Triangle(7, 3, 5), new TriangleError("", "")));
//        List<Response> result = service.calculateList(data);
//        assertNotNull(result);
//        assertEquals(5, result.size());
//        assertEquals(6, result.get(0).getTriangle().getArea());
//    }
//}
