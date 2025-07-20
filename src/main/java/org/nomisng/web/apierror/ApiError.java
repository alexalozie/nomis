package org.nomisng.web.apierror;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;
    private int statusCode;

//<editor-fold defaultstate="collapsed" desc="delombok">
//</editor-fold>
/*
    private String ObjectName;
    private String Field;
    private Object RejectedValue;
    private String DefaultMessage;
*/
    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
        this.statusCode = this.status.value();
    }

    public ApiError(HttpStatus status, Set<ConstraintViolation<?>> constraintViolations) {
        this();
        this.status = status;
        this.statusCode = this.status.value();
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.statusCode = this.status.value();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.statusCode = this.status.value();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        //this.setObjectName(object);this.setField(field);this.setRejectedValue(rejectedValue);this.setDefaultMessage(message);
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        //this.setObjectName(object);this.setDefaultMessage(message);
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(cv.getRootBeanClass().getSimpleName(), ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public HttpStatus getStatus() {
        return this.status;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @SuppressWarnings("all")
    public String getMessage() {
        return this.message;
    }

    @SuppressWarnings("all")
    public String getDebugMessage() {
        return this.debugMessage;
    }

    @SuppressWarnings("all")
    public List<ApiSubError> getSubErrors() {
        return this.subErrors;
    }

    @SuppressWarnings("all")
    public int getStatusCode() {
        return this.statusCode;
    }

    @SuppressWarnings("all")
    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @SuppressWarnings("all")
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @SuppressWarnings("all")
    public void setMessage(final String message) {
        this.message = message;
    }

    @SuppressWarnings("all")
    public void setDebugMessage(final String debugMessage) {
        this.debugMessage = debugMessage;
    }

    @SuppressWarnings("all")
    public void setSubErrors(final List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    @SuppressWarnings("all")
    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ApiError)) return false;
        final ApiError other = (ApiError) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getStatusCode() != other.getStatusCode()) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$debugMessage = this.getDebugMessage();
        final Object other$debugMessage = other.getDebugMessage();
        if (this$debugMessage == null ? other$debugMessage != null : !this$debugMessage.equals(other$debugMessage)) return false;
        final Object this$subErrors = this.getSubErrors();
        final Object other$subErrors = other.getSubErrors();
        if (this$subErrors == null ? other$subErrors != null : !this$subErrors.equals(other$subErrors)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof ApiError;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getStatusCode();
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $debugMessage = this.getDebugMessage();
        result = result * PRIME + ($debugMessage == null ? 43 : $debugMessage.hashCode());
        final Object $subErrors = this.getSubErrors();
        result = result * PRIME + ($subErrors == null ? 43 : $subErrors.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "ApiError(status=" + this.getStatus() + ", timestamp=" + this.getTimestamp() + ", message=" + this.getMessage() + ", debugMessage=" + this.getDebugMessage() + ", subErrors=" + this.getSubErrors() + ", statusCode=" + this.getStatusCode() + ")";
    }
    //</editor-fold>
}
