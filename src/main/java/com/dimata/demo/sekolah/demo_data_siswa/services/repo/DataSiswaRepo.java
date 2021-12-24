package com.dimata.demo.sekolah.demo_data_siswa.services.repo;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataSiswaRepo extends R2dbcRepository<DataSiswa, Long> {
    
    Mono<DataSiswa> findById(Long id);
}
