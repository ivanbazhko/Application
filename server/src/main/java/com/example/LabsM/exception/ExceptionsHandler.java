//package com.example.LabsM.exception;
//import com.example.LabsM.annotation.CustomExceptionHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@RestControllerAdvice(annotations = CustomExceptionHandler.class)
//public class ExceptionsHandler {
//    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);
//    @ExceptionHandler(InternalServerException.class)
//    public ResponseEntity<Object> InternalServerException(InternalServerException exception) {
//        logger.error("INTERNAL SERVER ERROR!");
//        Response response = new Response();
//        TriangleError errorResult = new TriangleError();
//        errorResult.setMessage("Internal Server Error!");
//        errorResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
//        response.setError(errorResult);
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
