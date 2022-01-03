package com.dimata.demo.sekolah.demo_data_siswa.services.api.datasekolah;


import com.dimata.demo.sekolah.demo_data_siswa.core.search.CommonParam;
import com.dimata.demo.sekolah.demo_data_siswa.core.search.SelectQBuilder;
import com.dimata.demo.sekolah.demo_data_siswa.core.search.WhereQuery;
import com.dimata.demo.sekolah.demo_data_siswa.forms.datasekolah.DataSekolahForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.Datasekolah.DataSekolah;
import com.dimata.demo.sekolah.demo_data_siswa.services.crude.Datasekolah.DataSekolahCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class DataSekolahApi {
    @Autowired
    private DataSekolahCrude dataSekolahCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataSekolah> createDataSekolah(DataSekolahForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataSekolahCrude.Option option = DataSekolahCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataSekolahCrude::create);
    }

    public Flux<DataSekolah> getAllDataSekolah(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataSekolah.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataSekolah::fromRow)
            .all();
    }

    public Mono<DataSekolah> getDataSekolah(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataSekolah.TABLE_NAME)
            .addWhere(WhereQuery.when(DataSekolah.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataSekolah::fromRow)
            .one();
    }

    public Mono<DataSekolah> updateDataSekolah(Long id, DataSekolahForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataSekolahCrude.Option option = DataSekolahCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataSekolahCrude::updateRecord);
    }    
}
