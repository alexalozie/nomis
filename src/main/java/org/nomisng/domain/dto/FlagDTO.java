package org.nomisng.domain.dto;

public class FlagDTO {
    private Long id;
    private String name;
    private String fieldName;
    private String fieldValue;
    private Integer datatype;
    private String operator;
    private Boolean continuous;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public FlagDTO() {
    }
    //</editor-fold>

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public String getFieldName() {
        return this.fieldName;
    }

    @SuppressWarnings("all")
    public String getFieldValue() {
        return this.fieldValue;
    }

    @SuppressWarnings("all")
    public Integer getDatatype() {
        return this.datatype;
    }

    @SuppressWarnings("all")
    public String getOperator() {
        return this.operator;
    }

    @SuppressWarnings("all")
    public Boolean getContinuous() {
        return this.continuous;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    @SuppressWarnings("all")
    public void setFieldValue(final String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @SuppressWarnings("all")
    public void setDatatype(final Integer datatype) {
        this.datatype = datatype;
    }

    @SuppressWarnings("all")
    public void setOperator(final String operator) {
        this.operator = operator;
    }

    @SuppressWarnings("all")
    public void setContinuous(final Boolean continuous) {
        this.continuous = continuous;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FlagDTO)) return false;
        final FlagDTO other = (FlagDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$datatype = this.getDatatype();
        final Object other$datatype = other.getDatatype();
        if (this$datatype == null ? other$datatype != null : !this$datatype.equals(other$datatype)) return false;
        final Object this$continuous = this.getContinuous();
        final Object other$continuous = other.getContinuous();
        if (this$continuous == null ? other$continuous != null : !this$continuous.equals(other$continuous)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$fieldName = this.getFieldName();
        final Object other$fieldName = other.getFieldName();
        if (this$fieldName == null ? other$fieldName != null : !this$fieldName.equals(other$fieldName)) return false;
        final Object this$fieldValue = this.getFieldValue();
        final Object other$fieldValue = other.getFieldValue();
        if (this$fieldValue == null ? other$fieldValue != null : !this$fieldValue.equals(other$fieldValue)) return false;
        final Object this$operator = this.getOperator();
        final Object other$operator = other.getOperator();
        if (this$operator == null ? other$operator != null : !this$operator.equals(other$operator)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof FlagDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $datatype = this.getDatatype();
        result = result * PRIME + ($datatype == null ? 43 : $datatype.hashCode());
        final Object $continuous = this.getContinuous();
        result = result * PRIME + ($continuous == null ? 43 : $continuous.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $fieldName = this.getFieldName();
        result = result * PRIME + ($fieldName == null ? 43 : $fieldName.hashCode());
        final Object $fieldValue = this.getFieldValue();
        result = result * PRIME + ($fieldValue == null ? 43 : $fieldValue.hashCode());
        final Object $operator = this.getOperator();
        result = result * PRIME + ($operator == null ? 43 : $operator.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "FlagDTO(id=" + this.getId() + ", name=" + this.getName() + ", fieldName=" + this.getFieldName() + ", fieldValue=" + this.getFieldValue() + ", datatype=" + this.getDatatype() + ", operator=" + this.getOperator() + ", continuous=" + this.getContinuous() + ")";
    }
}
