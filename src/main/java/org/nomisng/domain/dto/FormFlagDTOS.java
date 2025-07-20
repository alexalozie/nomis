package org.nomisng.domain.dto;

import org.nomisng.domain.entity.Flag;
import java.util.List;

public class FormFlagDTOS {
    private List<FormFlagDTO> formFlagDTOS;
    private Flag flag;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public FormFlagDTOS() {
    }

    @SuppressWarnings("all")
    public List<FormFlagDTO> getFormFlagDTOS() {
        return this.formFlagDTOS;
    }

    @SuppressWarnings("all")
    public Flag getFlag() {
        return this.flag;
    }

    @SuppressWarnings("all")
    public void setFormFlagDTOS(final List<FormFlagDTO> formFlagDTOS) {
        this.formFlagDTOS = formFlagDTOS;
    }

    @SuppressWarnings("all")
    public void setFlag(final Flag flag) {
        this.flag = flag;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FormFlagDTOS)) return false;
        final FormFlagDTOS other = (FormFlagDTOS) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$formFlagDTOS = this.getFormFlagDTOS();
        final Object other$formFlagDTOS = other.getFormFlagDTOS();
        if (this$formFlagDTOS == null ? other$formFlagDTOS != null : !this$formFlagDTOS.equals(other$formFlagDTOS)) return false;
        final Object this$flag = this.getFlag();
        final Object other$flag = other.getFlag();
        if (this$flag == null ? other$flag != null : !this$flag.equals(other$flag)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof FormFlagDTOS;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $formFlagDTOS = this.getFormFlagDTOS();
        result = result * PRIME + ($formFlagDTOS == null ? 43 : $formFlagDTOS.hashCode());
        final Object $flag = this.getFlag();
        result = result * PRIME + ($flag == null ? 43 : $flag.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "FormFlagDTOS(formFlagDTOS=" + this.getFormFlagDTOS() + ", flag=" + this.getFlag() + ")";
    }
    //</editor-fold>
}
