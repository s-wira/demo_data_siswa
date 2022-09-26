package com.dimata.demo.sekolah.demo_data_siswa.controllers;

import com.dimata.demo.sekolah.demo_data_siswa.core.search.CommonParam;
import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSekolahForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSekolahApi;

import java.util.List;

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
public class SekolahControler {
    @Autowired
    private DataSekolahApi dataSekolahApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/data_sekolah", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataSekolah> maintainerAddDataSekolah(@RequestBody DataSekolahForm form) {

        return dataSekolahApi.createDataSekolah(form);

    }

    @PostMapping(path = BASE_URL + "/data_sekolah/batch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<DataSekolah> maintainerAddDataSekolahBatch(@RequestBody List<DataSekolahForm> form) {
        return dataSekolahApi.createDataSekolahBatch(form);
    }

    // @PostMapping(path = BASE_URL + "/data_sekolah/batch", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public Flux<DataSekolah> maintainerAddDataSekolahBatch(@RequestBody List<DataSekolahForm> form) {
    //     return dataSekolahApi.createDataSekolahBatch(form);
    // }

    @GetMapping(path = BASE_URL + "/data_sekolah")
    public Flux<DataSekolah> maintainerGetAllDataSekolah(CommonParam param) {
        return dataSekolahApi.getAllDataSekolah(param);
    }

    @GetMapping(path = BASE_URL + "/data_sekolah/{id_sekolah}")
    public Mono<DataSekolah> maintainerGetDataSekolah(@PathVariable("id_sekolah") Long id_sekolah) {
        return dataSekolahApi.getDataSekolah(id_sekolah);
    }

    @PutMapping(path = BASE_URL + "/data_sekolah/{id_sekolah}")
    public Mono<DataSekolah> maintainerUpdateDataSekolah(@PathVariable("id_sekolah") long idSekolah, @RequestBody DataSekolahForm form) {
        return dataSekolahApi.updateDataSekolah(idSekolah, form);
    }
}
