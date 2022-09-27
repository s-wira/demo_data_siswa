package com.dimata.demo.sekolah.demo_data_siswa.services.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;

import reactor.core.publisher.Mono;

public interface SiswaMainRepo extends R2dbcRepository<SiswaMain, Long> {
    Mono<SiswaMain> findById(Long id);
}
