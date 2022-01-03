package com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.DbHandlerBase;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.util.StringSwitcher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataSekolahDbhandler extends DbHandlerBase<DataSekolah, Long>{
    @Autowired
    private R2dbcRepository<DataSekolah, Long> repo;

    @Override
    protected R2dbcRepository<DataSekolah, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<DataSekolah> setGenerateId(DataSekolah record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<DataSekolah> setGenerateIdBatch(Flux<DataSekolah> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }

    


    // TODO : apa ini static ?
    public static Mono<DataSekolah> update(DataSekolah record, Long id) {
        return null;
    }

    public static Mono<DataSekolah> create(DataSekolah record) {
        return null;
    }
}
