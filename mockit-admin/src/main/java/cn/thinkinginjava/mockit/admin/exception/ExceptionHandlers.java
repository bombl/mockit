/**
 * Mockit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Mockit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Mockit. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.thinkinginjava.mockit.admin.exception;

import cn.thinkinginjava.mockit.admin.model.dto.MockitResult;
import cn.thinkinginjava.mockit.common.exception.MockitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ControllerMethodResolver
 */
@ResponseBody
@ControllerAdvice
public class ExceptionHandlers {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(Exception.class)
    protected MockitResult<String> handleExceptionHandler(final Exception exception) {
        logger.error(exception.getMessage(), exception);
        String message;
        if (exception instanceof MockitException) {
            MockitException mockitException = (MockitException) exception;
            message = mockitException.getMessage();
        } else {
            message = "The system is busy, please try again later";
        }
        return MockitResult.fail(message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected MockitResult<String> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        logger.warn("http request method not supported", e);
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMethod());
        sb.append(
                " method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(e.getSupportedHttpMethods()).forEach(t -> sb.append(t).append(" "));
        return MockitResult.fail(sb.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected MockitResult<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        logger.warn("method argument not valid", e);
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream()
                .map(f -> f.getField().concat(": ").concat(Optional.ofNullable(f.getDefaultMessage()).orElse("")))
                .collect(Collectors.joining("| "));
        return MockitResult.fail(String.format("Request error! invalid argument [%s]", errorMsg));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected MockitResult<String> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        logger.warn("missing servlet request parameter", e);
        return MockitResult.fail(String.format("%s parameter is missing", e.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected MockitResult<String> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        logger.warn("method argument type mismatch", e);
        return MockitResult.fail(String.format("%s should be of type %s", e.getName(), Objects.requireNonNull(e.getRequiredType()).getName()));
    }
}
