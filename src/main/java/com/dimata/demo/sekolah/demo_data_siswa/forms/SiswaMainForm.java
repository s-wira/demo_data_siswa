package com.dimata.demo.sekolah.demo_data_siswa.forms;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.RecordAdapter;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SiswaMainForm implements RecordAdapter<SiswaMain>{
    
    private Long id;
    private Long nisn;
    private Integer nis;
    private Long idSekolah;

    @Override
    public SiswaMain convertNewRecord() {
        return SiswaMain.Builder.createNewRecord(nisn, nis, idSekolah).build();
    }

    @Override
    public SiswaMain convertToRecord() {
        return SiswaMain.Builder.emptyBuilder()
            .nis(nis)
            .nisn(nisn)
            .idSekolah(idSekolah)
            .build();
    }
    
}
