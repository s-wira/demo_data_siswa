package com.dimata.demo.sekolah.demo_data_siswa.controllers;

import com.dimata.demo.sekolah.demo_data_siswa.core.search.CommonParam;
import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSiswaForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSiswaApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SiswaController {

    @Autowired
    private DataSiswaApi dataSiswaApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/data_siswa", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataSiswa> maintainerAddDataSiswa(@RequestBody DataSiswaForm form) {
        return dataSiswaApi.createDataSiswa(form);
    }

    @GetMapping(path = BASE_URL + "/data_siswa")
    public Flux<DataSiswa> maintainerGetAllDataSiswa(CommonParam param) {
        return dataSiswaApi.getAllDataSiswa(param);
    }

    @GetMapping(path = BASE_URL + "/data_siswa/{nisn}")
    public Mono<DataSiswa> maintainerGetDataSiswa(@PathVariable("nisn") Long nisn) {
        return dataSiswaApi.getDataSiswa(nisn);
    }

    @PutMapping(path = BASE_URL + "/data_siswa/{nisn}")
    public Mono<DataSiswa> maintainerUpdateDataSiswa(@PathVariable("nisn") long nisn, @RequestBody DataSiswaForm form) {
        return dataSiswaApi.updateDataSiswa(nisn, form);
    }

    @GetMapping(path = BASE_URL + "/data_siswa/{nama_sekolah}")
    public Mono<DataSiswa> maintainerGetDataSiswa(@PathVariable("nama_sekolah") String nama_sekolah ) {
        return dataSiswaApi.getDataSiswa(nama_sekolah);
    }
}
