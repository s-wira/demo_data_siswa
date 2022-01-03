package com.dimata.demo.sekolah.demo_data_siswa.services.repo;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataSekolahRepo extends R2dbcRepository<DataSekolah, Long> {
    Mono<DataSekolah> findById(long id);
    
}
