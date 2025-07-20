package org.nomisng.domain.dto;

public class MapDTO {
    Object mKey;
    Object mValue;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public MapDTO() {
    }

    @SuppressWarnings("all")
    public Object getMKey() {
        return this.mKey;
    }

    @SuppressWarnings("all")
    public Object getMValue() {
        return this.mValue;
    }

    @SuppressWarnings("all")
    public void setMKey(final Object mKey) {
        this.mKey = mKey;
    }

    @SuppressWarnings("all")
    public void setMValue(final Object mValue) {
        this.mValue = mValue;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MapDTO)) return false;
        final MapDTO other = (MapDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mKey = this.getMKey();
        final Object other$mKey = other.getMKey();
        if (this$mKey == null ? other$mKey != null : !this$mKey.equals(other$mKey)) return false;
        final Object this$mValue = this.getMValue();
        final Object other$mValue = other.getMValue();
        if (this$mValue == null ? other$mValue != null : !this$mValue.equals(other$mValue)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof MapDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mKey = this.getMKey();
        result = result * PRIME + ($mKey == null ? 43 : $mKey.hashCode());
        final Object $mValue = this.getMValue();
        result = result * PRIME + ($mValue == null ? 43 : $mValue.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "MapDTO(mKey=" + this.getMKey() + ", mValue=" + this.getMValue() + ")";
    }
    //</editor-fold>
}
