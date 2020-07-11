package co.edu.cedesistemas.common;

import co.edu.cedesistemas.common.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultResponseBuilder {
    public static ResponseEntity<Status<?>> errorResponse(final String message, final Throwable t,
                                                          HttpStatus httpStatus) {
        Status<?> status = Status.builder()
                .code(1)
                .message(message)
                .details(t != null ? Stream.of(t.getStackTrace())
                        .map(StackTraceElement::toString).collect(Collectors.toList()) : null)
                .build();
        return new ResponseEntity<>(status, httpStatus);
    }

    public static <T> ResponseEntity<Status<?>> defaultResponse(final T obj, HttpStatus httpStatus) {
        int hits = 1;
        Class<?> _class = obj.getClass();
        Collection<?> result;
        if (obj instanceof Collection) {
            Collection<?> col = (Collection<?>) obj;
            if (col.iterator().hasNext()) {
                _class = col.iterator().next().getClass();
            }
            hits = col.size();
            result = col;
        } else {
            result = Collections.singleton(obj);
        }
        Status<?> status = Status.builder()
                ._hits(hits)
                ._source(result)
                ._class(_class.getName())
                .code(httpStatus.value())
                .message(httpStatus.toString())
                .build();
        return new ResponseEntity<>(status, httpStatus);
    }
}