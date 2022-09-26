package com.dimata.demo.sekolah.demo_data_siswa.forms;

import java.time.LocalDate;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.RecordAdapter;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.jackson.DateDeserialize;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class DataSekolahForm implements RecordAdapter<DataSekolah>{
    private Long id;
    private String namaSekolah;
    private String alamat;
    private String phoneNum;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;
    private String zona;
    private String fax;
    @Override
    public DataSekolah convertNewRecord() {
        return DataSekolah.Builder.createNewRecord(namaSekolah, alamat)
            .phoneNum(phoneNum)
            .zona(zona)
            .fax(fax)
            .id(id)
            .kecamatan(kecamatan)
            .kabupaten(kabupaten)
            .provinsi(provinsi)
            .zona(zona)
            .build();
    }
    @Override
    public DataSekolah convertToRecord() {
        return DataSekolah.Builder.emptyBuilder()
            .alamat(alamat)
            .kecamatan(kecamatan)
            .kabupaten(kabupaten)
            .provinsi(provinsi)
            .namaSekolah(namaSekolah)
            .id(id)
            .fax(fax)
            .phoneNum(phoneNum)
            .build();
    }
}
