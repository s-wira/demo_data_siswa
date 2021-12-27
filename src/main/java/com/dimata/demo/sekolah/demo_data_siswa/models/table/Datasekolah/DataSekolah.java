package com.dimata.demo.sekolah.demo_data_siswa.models.table.Datasekolah;



import java.time.LocalDate;
import java.util.Objects;

import static com.dimata.demo.sekolah.demo_data_siswa.core.util.ManipulateUtil.changeItOrNot;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.UpdateAvailable;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.GenerateUtil;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.ManipulateUtil;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.jackson.DateSerialize;
import com.dimata.demo.sekolah.demo_data_siswa.enums.GenderSiswa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import io.r2dbc.spi.Row;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DataSekolah implements UpdateAvailable<DataSekolah>, Persistable<Long>{
    public static final String TABLE_NAME = "data_sekolah";
    public static final String IDSEKOLAH_COL = "id_sekolah";
    public static final String NAMA_SEKOLAH_COL = "namaSekolah";
    public static final String ALAMAT_COL = "alamat";
    public static final String PHONENUM_COL = "phoneNum";
    public static final String FAX_COL = "fax";
    public static final String KECAMATAN_COL = "kecamatan ";
    public static final String KABUPATEN_COL = "kabupaten";
    public static final String PROVINSI_COL = "provinsi";
    public static final String ZONA_COL = "zona";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private Integer id_sekolah;
        private String namaSekolah;
        private String alamat;
        private String phoneNum;
        private Stirng fax ;
        private String kecamatan ;
        private String kabupaten;
        private String provinsi;
        private Integer zona;
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(Integer id_sekolah, String namaSekolah, String alamat) {
            return new Builder().newRecord(true)
                .id_sekolah(Objects.requireNonNull(id_sekolah, "id_sekolah diperlukan"))
                .namaSekolah(Objects.requireNonNull(namaSekolah, "Nama sekolah tidak boleh kosong"))
                .alamat(Objects.requireNonNull(alamat, "Alamat tidak boleh kosong"));
        }

        public static Builder updateBuilder(DataSekolah oldRecord, DataSekolah newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .alamat(changeItOrNot(newRecord.getAlamat(), oldRecord.getAlamat()))
                .id_sekolah(changeItOrNot(newRecord.getId_sekolah(), oldRecord.getId_sekolah()))
                .namaSekolah(changeItOrNot(newRecord.getNamaSekolah(), oldRecord.getNamaSekolah()))
                .kecamatan(changeItOrNot(newRecord.getKecamaan(), oldRecord.getKecamatan()))
                .kabupaten(changeItOrNot(newRecord.getKabupaten(), oldRecord.getKabupaten()))
                .provinsi(changeItOrNot(newRecord.getProvinsi(), oldRecord.getProvinsi()))
                .fax(changeItOrNot(newRecord.getFax(), oldRecord.getFax()))
                .phoneNum(changeItOrNot(newRecord.getPhoneNum(), oldRecord.getPhoneNum()))
                .zona(changeItOrNot(newRecord.getZona(), oldRecord.getZona()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataSekolah build() {
            DataSekolah result = new DataSekolah();
            
            result.setAlamat(alamat);
            result.setId_sekolah(id_sekolah);
            result.setNamaSekolah(namaSekolah);
            result.setKecamtan(kecamatan);
            result.setKabupaten(kabupaten);
            result.setId(id);
            result.setProvinsi(provinsi);
            result.seFax(fax);
            result.setPhoneNum(phoneNum);
            result.setZone(zone);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private Integer id_sekolah;
    private String namaSekolah;
    private String alamat;
    private String phoneNum;
    private Integer fax;
    private String kecamatan;
    @JsonSerialize(converter = DateSerialize.class)
    private String kabupaten;
    private String provinsi;
    private String zona;
    @Transient
    @JsonIgnore
    private Long insertId;


    public static DataSekolah fromRow(Row row) {
        var result = new DataSekolah();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setIdSekolah(ManipulateUtil.parseRow(row, ID_SEKOLAH_COL, Integer.class));
        result.setNamaSekolah(ManipulateUtil.parseRow(row, NAMA_SEKOLAH_COL, String.class));
        result.setPhoneNum(ManipulateUtil.parseRow(row, PHONE_NUM_COL, String.class));
        result.setAlamat(ManipulateUtil.parseRow(row, ALAMAT_COL, String.class));
        result.setKecamatan(ManipulateUtil.parseRow(row, KECAMATAN_COL, Integer.class));
        result.setKabupaten(ManipulateUtil.parseRow(row, KABUPATEN_COL, String.class));
        result.setProvinsi(ManipulateUtil.parseRow(row, PROVINSI_COL, LocalDate.class));
        result.setFax(ManipulateUtil.parseRow(row, FAX_COL, String.class));
        result.setZona(ManipulateUtil.parseRow(row, ZONA_COL, String.class));
        return result;
    }

    @Override
    public boolean isNew() {
        if (id == null && insertId == null) {
            id = new GenerateUtil().generateOID();
            return true;
        } else if (id == null) {
            id = insertId;
            return true;
        }
        return false;
    }

    @Override
    public DataSekolah update(DataSekolah newData) {
        return Builder.updateBuilder(this, newData).build();
    }
}
