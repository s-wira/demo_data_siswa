package com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler;

import com.dimata.demo.sekolah.demo_data_siswa.core.api.DbHandlerBase;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.services.repo.DataSiswaRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataSiswaDbHandler extends DbHandlerBase<DataSiswa, Long> {
    
    @Autowired
    private DataSiswaRepo repo;

    @Override
    protected R2dbcRepository<DataSiswa, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<DataSiswa> setGenerateId(DataSiswa record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<DataSiswa> setGenerateIdBatch(Flux<DataSiswa> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }

    public Mono<DataSiswa> updateOnly(DataSiswa record, Long id) {
        return null;
    }

    public Mono<DataSiswa> create(DataSiswa record) {
        return null;
    }
    
}
