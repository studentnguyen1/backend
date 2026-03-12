package vn.khanguyen.backend.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.khanguyen.backend.domain.res.RestResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = UserNullException.class)
    public ResponseEntity<RestResponse<Object>> handleUserNullException(UserNullException userNullException) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(userNullException.getMessage());
        res.setMessage("UserNullException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
