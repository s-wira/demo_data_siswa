package com.dimata.demo.sekolah.demo_data_siswa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSiswaMainForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSekolahApi;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSiswaApi;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.SiswaMainApi;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SiswaMainController {
    
    @Autowired
    private SiswaMainApi siswaMainApi;
    @Autowired
    private DataSekolahApi dataSekolahApi;
    @Autowired
    private DataSiswaApi dataSiswaApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/siswa_main", 
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SiswaMain> maintainerAddSiswaMain(
        @RequestBody DataSiswaMainForm form
    ){
        return Mono.just(form)
            .flatMap(f -> {
                var dataSiswa = dataSiswaApi.createDataSiswa(form.getDataSiswa());
                var dataSekolah = dataSekolahApi.createDataSekolah(form.getDataSekolah());
                return Mono.zip(combinator, monos)
            })
    }

}
