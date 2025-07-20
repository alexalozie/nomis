package org.nomisng.domain.dto;

import org.nomisng.domain.entity.Item;
import org.nomisng.domain.entity.RegimenDrug;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class DrugDTO {
    private Long id;
    @NotBlank(message = "Abbreviation is mandatory")
    private String abbrev;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Strength is mandatory")
    private String strength;
    @NotBlank(message = "Pack Size is mandatory")
    private Integer packSize;
    private String doseForm;
    private Integer morning;
    private Integer afternoon;
    private Integer evening;
    @NotBlank(message = "Item is mandatory")
    private Item item;
    private List<RegimenDrug> regimenDrugs;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getAbbrev() {
        return this.abbrev;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public String getStrength() {
        return this.strength;
    }

    @SuppressWarnings("all")
    public Integer getPackSize() {
        return this.packSize;
    }

    @SuppressWarnings("all")
    public String getDoseForm() {
        return this.doseForm;
    }

    @SuppressWarnings("all")
    public Integer getMorning() {
        return this.morning;
    }

    @SuppressWarnings("all")
    public Integer getAfternoon() {
        return this.afternoon;
    }

    @SuppressWarnings("all")
    public Integer getEvening() {
        return this.evening;
    }

    @SuppressWarnings("all")
    public Item getItem() {
        return this.item;
    }

    @SuppressWarnings("all")
    public List<RegimenDrug> getRegimenDrugs() {
        return this.regimenDrugs;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setAbbrev(final String abbrev) {
        this.abbrev = abbrev;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setStrength(final String strength) {
        this.strength = strength;
    }

    @SuppressWarnings("all")
    public void setPackSize(final Integer packSize) {
        this.packSize = packSize;
    }

    @SuppressWarnings("all")
    public void setDoseForm(final String doseForm) {
        this.doseForm = doseForm;
    }

    @SuppressWarnings("all")
    public void setMorning(final Integer morning) {
        this.morning = morning;
    }

    @SuppressWarnings("all")
    public void setAfternoon(final Integer afternoon) {
        this.afternoon = afternoon;
    }

    @SuppressWarnings("all")
    public void setEvening(final Integer evening) {
        this.evening = evening;
    }

    @SuppressWarnings("all")
    public void setItem(final Item item) {
        this.item = item;
    }

    @SuppressWarnings("all")
    public void setRegimenDrugs(final List<RegimenDrug> regimenDrugs) {
        this.regimenDrugs = regimenDrugs;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DrugDTO)) return false;
        final DrugDTO other = (DrugDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$packSize = this.getPackSize();
        final Object other$packSize = other.getPackSize();
        if (this$packSize == null ? other$packSize != null : !this$packSize.equals(other$packSize)) return false;
        final Object this$morning = this.getMorning();
        final Object other$morning = other.getMorning();
        if (this$morning == null ? other$morning != null : !this$morning.equals(other$morning)) return false;
        final Object this$afternoon = this.getAfternoon();
        final Object other$afternoon = other.getAfternoon();
        if (this$afternoon == null ? other$afternoon != null : !this$afternoon.equals(other$afternoon)) return false;
        final Object this$evening = this.getEvening();
        final Object other$evening = other.getEvening();
        if (this$evening == null ? other$evening != null : !this$evening.equals(other$evening)) return false;
        final Object this$abbrev = this.getAbbrev();
        final Object other$abbrev = other.getAbbrev();
        if (this$abbrev == null ? other$abbrev != null : !this$abbrev.equals(other$abbrev)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$strength = this.getStrength();
        final Object other$strength = other.getStrength();
        if (this$strength == null ? other$strength != null : !this$strength.equals(other$strength)) return false;
        final Object this$doseForm = this.getDoseForm();
        final Object other$doseForm = other.getDoseForm();
        if (this$doseForm == null ? other$doseForm != null : !this$doseForm.equals(other$doseForm)) return false;
        final Object this$item = this.getItem();
        final Object other$item = other.getItem();
        if (this$item == null ? other$item != null : !this$item.equals(other$item)) return false;
        final Object this$regimenDrugs = this.getRegimenDrugs();
        final Object other$regimenDrugs = other.getRegimenDrugs();
        if (this$regimenDrugs == null ? other$regimenDrugs != null : !this$regimenDrugs.equals(other$regimenDrugs)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof DrugDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $packSize = this.getPackSize();
        result = result * PRIME + ($packSize == null ? 43 : $packSize.hashCode());
        final Object $morning = this.getMorning();
        result = result * PRIME + ($morning == null ? 43 : $morning.hashCode());
        final Object $afternoon = this.getAfternoon();
        result = result * PRIME + ($afternoon == null ? 43 : $afternoon.hashCode());
        final Object $evening = this.getEvening();
        result = result * PRIME + ($evening == null ? 43 : $evening.hashCode());
        final Object $abbrev = this.getAbbrev();
        result = result * PRIME + ($abbrev == null ? 43 : $abbrev.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $strength = this.getStrength();
        result = result * PRIME + ($strength == null ? 43 : $strength.hashCode());
        final Object $doseForm = this.getDoseForm();
        result = result * PRIME + ($doseForm == null ? 43 : $doseForm.hashCode());
        final Object $item = this.getItem();
        result = result * PRIME + ($item == null ? 43 : $item.hashCode());
        final Object $regimenDrugs = this.getRegimenDrugs();
        result = result * PRIME + ($regimenDrugs == null ? 43 : $regimenDrugs.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "DrugDTO(id=" + this.getId() + ", abbrev=" + this.getAbbrev() + ", name=" + this.getName() + ", strength=" + this.getStrength() + ", packSize=" + this.getPackSize() + ", doseForm=" + this.getDoseForm() + ", morning=" + this.getMorning() + ", afternoon=" + this.getAfternoon() + ", evening=" + this.getEvening() + ", item=" + this.getItem() + ", regimenDrugs=" + this.getRegimenDrugs() + ")";
    }

    @SuppressWarnings("all")
    public DrugDTO() {
    }

    @SuppressWarnings("all")
    public DrugDTO(final Long id, final String abbrev, final String name, final String strength, final Integer packSize, final String doseForm, final Integer morning, final Integer afternoon, final Integer evening, final Item item, final List<RegimenDrug> regimenDrugs) {
        this.id = id;
        this.abbrev = abbrev;
        this.name = name;
        this.strength = strength;
        this.packSize = packSize;
        this.doseForm = doseForm;
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
        this.item = item;
        this.regimenDrugs = regimenDrugs;
    }
    //</editor-fold>
}
