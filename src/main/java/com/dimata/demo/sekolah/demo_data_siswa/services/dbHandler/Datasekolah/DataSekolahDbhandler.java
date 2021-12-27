package com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler.Datasekolah;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.DbHandlerBase;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.Datasekolah.DataSekolah;
import com.dimata.demo.sekolah.demo_data_siswa.services.repo.DataSekolahRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataSekolahDbhandler extends DbHandlerBase<DataSekolah, Long>{
    @Autowired
    private DataSekolahRepo repo;

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
}
