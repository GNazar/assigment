package sensysgatso.com.traffic.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EventAlreadyProcessed extends RuntimeException {

    public EventAlreadyProcessed(String message) {
        super(message);
    }
}
