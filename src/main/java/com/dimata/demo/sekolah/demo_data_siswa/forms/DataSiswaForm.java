package com.dimata.demo.sekolah.demo_data_siswa.forms;

import java.time.LocalDate;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.RecordAdapter;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.jackson.DateDeserialize;
import com.dimata.demo.sekolah.demo_data_siswa.enums.GenderSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSiswaForm implements RecordAdapter<DataSiswa> {

    private Long id;
    private Integer nis;
    private String namaSiswa;
    private String alamat;
    private String phoneNum;
    private GenderSiswa gender;
    private String birthPlace;
    @JsonDeserialize(converter = DateDeserialize.class)
    private LocalDate birthDate;
    private String citizenship;
    @Override
    public DataSiswa convertNewRecord() {
        return DataSiswa.Builder.createNewRecord(nis, namaSiswa, alamat, gender)
            .phoneNum(phoneNum)
            .birthDate(birthDate)
            .birthPlace(birthPlace)
            .citizenship(citizenship)
            .id(id)
            .build();
    }
    @Override
    public DataSiswa convertToRecord() {
        return DataSiswa.Builder.emptyBuilder()
            .alamat(alamat)
            .birthDate(birthDate)
            .birthPlace(birthPlace)
            .citizenship(citizenship)
            .gender(gender)
            .namaSiswa(namaSiswa)
            .nis(nis)
            .phoneNum(phoneNum)
            .id(id)
            .build();
    }
}
