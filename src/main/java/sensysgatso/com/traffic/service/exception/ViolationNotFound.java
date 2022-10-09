package sensysgatso.com.traffic.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ViolationNotFound extends RuntimeException {

    public ViolationNotFound(String message) {
        super(message);
    }
}
