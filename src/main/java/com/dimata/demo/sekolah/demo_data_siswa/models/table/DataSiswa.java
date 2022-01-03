package com.dimata.demo.sekolah.demo_data_siswa.models.table;

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
public class DataSiswa implements UpdateAvailable<DataSiswa>, Persistable<Long>{
    
    public static final String TABLE_NAME = "data_siswa";
    public static final String ID_COL = "nisn";
    public static final String NIS_COL = "nis";
    public static final String NAMA_SISWA_COL = "nama_siswa";
    public static final String ALAMAT_COL = "alamat";
    public static final String PHONE_NUM_COL = "phone_num_col";
    public static final String GENDER_COL = "gender";
    public static final String BIRTH_PLACE_COL = "birth_place";
    public static final String BIRTH_DATE_COL = "birth_date";
    public static final String CITIZENSHIP_COL = "citizenship";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private Integer nis;
        private String namaSiswa;
        private String alamat;
        private String phoneNum;
        private GenderSiswa gender;
        private String birthPlace;
        private LocalDate birthDate;
        private String citizenship;
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(Integer nis, String namaSiswa, String alamatSiswa, GenderSiswa gender) {
            return new Builder().newRecord(true)
                .nis(Objects.requireNonNull(nis, "NIS diperlukan"))
                .namaSiswa(Objects.requireNonNull(namaSiswa, "Nama siswa tidak boleh kosong"))
                .alamat(Objects.requireNonNull(alamatSiswa, "Alamat tidak boleh kosong"))
                .gender(Objects.requireNonNull(gender, "Gender tidak boleh kosong"));
        }

        public static Builder updateBuilder(DataSiswa oldRecord, DataSiswa newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .alamat(changeItOrNot(newRecord.getAlamat(), oldRecord.getAlamat()))
                .birthDate(changeItOrNot(newRecord.getBirthDate(), oldRecord.getBirthDate()))
                .birthPlace(changeItOrNot(newRecord.getBirthPlace(), oldRecord.getBirthPlace()))
                .citizenship(changeItOrNot(newRecord.getCitizenship(), oldRecord.getCitizenship()))
                .gender(changeItOrNot(newRecord.getGender(), oldRecord.getGender()))
                .namaSiswa(changeItOrNot(newRecord.getNamaSiswa(), oldRecord.getNamaSiswa()))
                .nis(changeItOrNot(newRecord.getNis(), oldRecord.getNis()))
                .phoneNum(changeItOrNot(newRecord.getPhoneNum(), oldRecord.getPhoneNum()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataSiswa build() {
            DataSiswa result = new DataSiswa();
            
            result.setAlamat(alamat);
            result.setBirthDate(birthDate);
            result.setBirthPlace(birthPlace);
            result.setCitizenship(citizenship);
            result.setGender(gender);
            result.setId(id);
            result.setNamaSiswa(namaSiswa);
            result.setNis(nis);
            result.setPhoneNum(phoneNum);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private String nis;
    private String namaSiswa;
    private String alamat;
    private String phoneNum;
    private Integer gender;
    private String birthPlace;
    @JsonSerialize(converter = DateSerialize.class)
    private LocalDate birthDate;
    private String citizenship;
    @Transient
    @JsonIgnore
    private Long insertId;

    public void setGender(GenderSiswa gender) {
        if (gender != null) {
            this.gender = gender.getCode();
        }
    }

    public GenderSiswa getGender() {
        if (gender != null) {
            return GenderSiswa.getGender(this.gender);
        }
        return null;
    }

    public static DataSiswa fromRow(Row row) {
        var result = new DataSiswa();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setNis(ManipulateUtil.parseRow(row, NIS_COL, Integer.class));
        result.setNamaSiswa(ManipulateUtil.parseRow(row, NAMA_SISWA_COL, String.class));
        result.setPhoneNum(ManipulateUtil.parseRow(row, PHONE_NUM_COL, String.class));
        result.setAlamat(ManipulateUtil.parseRow(row, ALAMAT_COL, String.class));
        result.setGender(GenderSiswa.getGender(ManipulateUtil.parseRow(row, GENDER_COL, Integer.class)));
        result.setBirthPlace(ManipulateUtil.parseRow(row, BIRTH_DATE_COL, String.class));
        result.setBirthDate(ManipulateUtil.parseRow(row, BIRTH_DATE_COL, LocalDate.class));
        result.setCitizenship(ManipulateUtil.parseRow(row, CITIZENSHIP_COL, String.class));
        
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
    public DataSiswa update(DataSiswa newData) {
        return Builder.updateBuilder(this, newData).build();
    }

    
}
