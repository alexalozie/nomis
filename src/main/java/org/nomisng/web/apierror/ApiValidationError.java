package org.nomisng.web.apierror;

public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public String getObject() {
        return this.object;
    }

    @SuppressWarnings("all")
    public String getField() {
        return this.field;
    }

    @SuppressWarnings("all")
    public Object getRejectedValue() {
        return this.rejectedValue;
    }

    @SuppressWarnings("all")
    public String getMessage() {
        return this.message;
    }

    @SuppressWarnings("all")
    public void setObject(final String object) {
        this.object = object;
    }

    @SuppressWarnings("all")
    public void setField(final String field) {
        this.field = field;
    }

    @SuppressWarnings("all")
    public void setRejectedValue(final Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    @SuppressWarnings("all")
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "ApiValidationError(object=" + this.getObject() + ", field=" + this.getField() + ", rejectedValue=" + this.getRejectedValue() + ", message=" + this.getMessage() + ")";
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ApiValidationError)) return false;
        final ApiValidationError other = (ApiValidationError) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$object = this.getObject();
        final Object other$object = other.getObject();
        if (this$object == null ? other$object != null : !this$object.equals(other$object)) return false;
        final Object this$field = this.getField();
        final Object other$field = other.getField();
        if (this$field == null ? other$field != null : !this$field.equals(other$field)) return false;
        final Object this$rejectedValue = this.getRejectedValue();
        final Object other$rejectedValue = other.getRejectedValue();
        if (this$rejectedValue == null ? other$rejectedValue != null : !this$rejectedValue.equals(other$rejectedValue)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof ApiValidationError;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $object = this.getObject();
        result = result * PRIME + ($object == null ? 43 : $object.hashCode());
        final Object $field = this.getField();
        result = result * PRIME + ($field == null ? 43 : $field.hashCode());
        final Object $rejectedValue = this.getRejectedValue();
        result = result * PRIME + ($rejectedValue == null ? 43 : $rejectedValue.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    @SuppressWarnings("all")
    public ApiValidationError(final String object, final String field, final Object rejectedValue, final String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
    //</editor-fold>
}
