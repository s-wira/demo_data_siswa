package com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.DbHandlerBase;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;
import com.dimata.demo.sekolah.demo_data_siswa.services.repo.SiswaMainRepo;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class SiswaMainDbHandler extends DbHandlerBase<SiswaMain, Long>{
    
    @Autowired
    private SiswaMainRepo repo;
    
    @Override
    protected R2dbcRepository<SiswaMain, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<SiswaMain> setGenerateId(SiswaMain record) {
        return Mono.just(record)
        .map(z -> {
            long id = getGenerateUtil().generateOID();
            z.setInsertId(id);
            return z;
        });
    }

    @Override
    protected Flux<SiswaMain> setGenerateIdBatch(Flux<SiswaMain> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }
    
}
