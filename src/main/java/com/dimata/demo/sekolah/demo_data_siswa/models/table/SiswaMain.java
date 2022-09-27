package com.dimata.demo.sekolah.demo_data_siswa.models.table;

import java.util.Objects;

import static com.dimata.demo.sekolah.demo_data_siswa.core.util.ManipulateUtil.changeItOrNot;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.UpdateAvailable;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.GenerateUtil;
import com.dimata.demo.sekolah.demo_data_siswa.core.util.ManipulateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class SiswaMain implements UpdateAvailable<SiswaMain>, Persistable<Long>{
    public static final String TABLE_NAME = "siswa_main";
    public static final String ID_COL = "id_main";
    public static final String NISN_COL = "nisn";
    public static final String NIS_COL = "nis";
    public static final String ID_SEKOLAH_COL = "id_sekolah";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {
        private Long id;
        private Long nisn;
        private int nis;
        private Long idSekolah;
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(Long nisn, int nis, Long idSekolah) {
            return new Builder().newRecord(true)
                .nis(Objects.requireNonNull(nis, "NIS diperlukan"))
                .nisn(Objects.requireNonNull(nisn, "NISN tidak boleh kosong"))
                .idSekolah(Objects.requireNonNull(idSekolah, "IdSekolah tidak boleh kosong"));
        }

        public static Builder updateBuilder(SiswaMain oldRecord, SiswaMain newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .nis(changeItOrNot(newRecord.getNis(), oldRecord.getNis()))
                .nisn(changeItOrNot(newRecord.getNisn(), oldRecord.getNisn()))
                .idSekolah(changeItOrNot(newRecord.getIdSekolah(), oldRecord.getIdSekolah()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public SiswaMain build() {
            SiswaMain result = new SiswaMain();
            result.setId(id);
            result.setNisn(nisn);
            result.setNis(nis);
            result.setIdSekolah(idSekolah);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private Long nisn;
    private int nis;
    private Long idSekolah;
    @Transient
    @JsonIgnore
    private Long insertId;

    public static SiswaMain fromRow(Row row) {
        var result = new SiswaMain();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setNis(ManipulateUtil.parseRow(row, NIS_COL, Integer.class));
        result.setNisn(ManipulateUtil.parseRow(row, NISN_COL, Long.class));
        result.setIdSekolah(ManipulateUtil.parseRow(row, ID_SEKOLAH_COL, Long.class));
        
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
    public SiswaMain update(SiswaMain newData) {
        return Builder.updateBuilder(this, newData).build();
    }


}
