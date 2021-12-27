package com.dimata.demo.sekolah.demo_data_siswa.forms.datasekolah;

import java.time.LocalDate;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.RecordAdapter;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.jackson.DateDeserialize;
import com.dimata.demo.sekolah.demo_data_siswa.enums.GenderSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.Datasekolah;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.Datasekolah.DataSekolah;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class DataSekolahForm implements RecordAdapter<DataSekolah>{
    private Long id;
    private Integer id_sekolah;
    private String nama_Sekolah;
    private String alamat;
    private String phoneNum;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;
    
    @JsonDeserialize(converter = DateDeserialize.class)
    private String zona;
    private String fax;
    @Override
    public DataSekolah convertNewRecord() {
        return DataSekolah.Builder.createNewRecord(id_sekolah, nama_Sekolah, alamat)
            .phoneNum(phoneNum)
            .zona(zona)
            .fax(fax)
            .id(id)
            .build();
    }
    @Override
    public DataSekolah convertToRecord() {
        return DataSekolah.Builder.emptyBuilder()
            .alamat(alamat)
            .kecamatan(kecamatan)
            .kabupaten(kabupaten)
            .provinsi(provinsi)
            .namaSekolah(nama_Sekolah)
            .id_sekolah(id_sekolah)
            .phoneNum(phoneNum)
            .id(id)
            .build();
    }
}
