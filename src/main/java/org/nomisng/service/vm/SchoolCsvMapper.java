package org.nomisng.service.vm;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchoolCsvMapper {
    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String type;
    @CsvBindByPosition(position = 3)
    private String state;
    @CsvBindByPosition(position = 4)
    private String lga;
    @CsvBindByPosition(position = 5)
    private String address;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SchoolCsvMapper{id=").append(id).append(", name=").append(name).append(", type=").append(type).append(", state=").append(state).append(", lga=").append(lga).append(", address=").append(address).append("}");
        return builder.toString();
    }

}
