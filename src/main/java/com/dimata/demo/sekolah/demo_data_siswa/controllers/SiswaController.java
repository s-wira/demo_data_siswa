package com.dimata.demo.sekolah.demo_data_siswa.controllers;

import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSiswaForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSiswaApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SiswaController {

    @Autowired
    private DataSiswaApi dataSiswaApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/data-siswa", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataSiswa> maintainerAddDataSiswa(@RequestBody DataSiswaForm form) {
        return dataSiswaApi.createDataSiswa(form);
    }
}
