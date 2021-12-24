package com.dimata.demo.sekolah.demo_data_siswa.services.api;

import com.dimata.demo.sekolah.demo_data_siswa.core.search.CommonParam;
import com.dimata.demo.sekolah.demo_data_siswa.core.search.SelectQBuilder;
import com.dimata.demo.sekolah.demo_data_siswa.core.search.WhereQuery;
import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSiswaForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.services.crude.DataSiswaCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSiswaApi {
    
    @Autowired
    private DataSiswaCrude dataSiswaCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataSiswa> createDataSiswa(DataSiswaForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataSiswaCrude.Option option = DataSiswaCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataSiswaCrude::create);
    }

    public Flux<DataSiswa> getAllDataSiswa(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataSiswa.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataSiswa::fromRow)
            .all();
    }

    public Mono<DataSiswa> getDataSiswa(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataSiswa.TABLE_NAME)
            .addWhere(WhereQuery.when(DataSiswa.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataSiswa::fromRow)
            .one();
    }

    public Mono<DataSiswa> updateDataSiswa(Long id, DataSiswaForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataSiswaCrude.Option option = DataSiswaCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataSiswaCrude::updateRecord);
    }
}
