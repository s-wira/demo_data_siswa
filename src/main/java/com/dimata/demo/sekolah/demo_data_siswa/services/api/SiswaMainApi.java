package com.dimata.demo.sekolah.demo_data_siswa.services.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.dimata.demo.sekolah.demo_data_siswa.core.search.CommonParam;
import com.dimata.demo.sekolah.demo_data_siswa.core.search.SelectQBuilder;
import com.dimata.demo.sekolah.demo_data_siswa.core.search.WhereQuery;
import com.dimata.demo.sekolah.demo_data_siswa.forms.SiswaMainForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;
import com.dimata.demo.sekolah.demo_data_siswa.services.crude.SiswaMainCrude;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SiswaMainApi {
    
    @Autowired
    private SiswaMainCrude siswaMainCrude;
    @Autowired
    private R2dbcEntityTemplate template;

    public Mono<SiswaMain> createDataSiswa(SiswaMainForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            SiswaMainCrude.Option option = SiswaMainCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(siswaMainCrude::create);
    }

    public Flux<SiswaMain> createDataSiswaBatch(List<SiswaMainForm> form) {
        return Mono.just(form)
            .flatMapMany(Flux::fromIterable)
            .flatMap(f -> {
                SiswaMainCrude.Option option = SiswaMainCrude.initOption(f.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(siswaMainCrude::create);
    }

    public Flux<SiswaMain> getAllDataSiswa(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(SiswaMain.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(SiswaMain::fromRow)
            .all();
    }

    public Mono<SiswaMain> getDataSiswa(Long id) {
        var sql = SelectQBuilder.emptyBuilder(SiswaMain.TABLE_NAME)
            .addWhere(WhereQuery.when(SiswaMain.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(SiswaMain::fromRow)
            .one();
    }

    public Flux<SiswaMain> getAllSiswaMainFromSekolahId(Long sekolahId) {
        var sql = SelectQBuilder.emptyBuilder(SiswaMain.TABLE_NAME)
            .addWhere(WhereQuery.when(SiswaMain.ID_SEKOLAH_COL).is(sekolahId))
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(SiswaMain::fromRow)
            .all();
    }

    public Mono<SiswaMain> updateDataSiswa(Long id, SiswaMainForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                SiswaMainCrude.Option option = SiswaMainCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(siswaMainCrude::updateRecord);
    }
}
