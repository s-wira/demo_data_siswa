package com.dimata.demo.sekolah.demo_data_siswa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSiswaMainForm;
import com.dimata.demo.sekolah.demo_data_siswa.forms.ListSiswaForm;
import com.dimata.demo.sekolah.demo_data_siswa.forms.SiswaMainForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSekolahApi;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.DataSiswaApi;
import com.dimata.demo.sekolah.demo_data_siswa.services.api.SiswaMainApi;

import reactor.core.publisher.Flux;
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
                var dataSiswa = dataSiswaApi
                    .createDataSiswa(f.getDataSiswa());
                Mono<DataSekolah> dataSekolah;
                if (f.getIdSekolah()==null) {
                    dataSekolah = dataSekolahApi
                    .createDataSekolah(f.getDataSekolah());
                } else {
                    dataSekolah = dataSekolahApi.getDataSekolah(f.getIdSekolah());
                }
                return Mono.zip(dataSiswa, dataSekolah);
            })
            .flatMap(z -> {
                SiswaMainForm siswaMainForm = new SiswaMainForm();
                siswaMainForm.setIdSekolah(z.getT2().getId());
                siswaMainForm.setNisn(z.getT1().getId());
                siswaMainForm.setNis(Integer.parseInt(
                    z.getT1().getNis())
                );

                return siswaMainApi.createDataSiswa(siswaMainForm);
            });
    }

    @GetMapping(path = BASE_URL + "/siswa_main/{idSekolah}")
    public Mono<ListSiswaForm> maintainerGetListSiswaSekolah(@PathVariable("idSekolah") Long id) {
        return Mono.just(id)
            .flatMap(f -> dataSekolahApi.getDataSekolah(f))
            .flatMap(f -> {
                var ids = siswaMainApi.getAllSiswaMainFromSekolahId(f.getId()).collectList();
                return Mono.zip(Mono.just(f), ids);
            })
            .flatMap(z -> {
                var dataSiswa = Mono.just(z.getT2())
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(f -> {
                        return dataSiswaApi.getDataSiswa(f.getNisn());
                    }).collectList();
                return Mono.zip(Mono.just(z.getT1()), dataSiswa);
            })
            .map(z -> {
                ListSiswaForm data = new ListSiswaForm();
                data.setDaftarSiswa(z.getT2());
                data.setSekolah(z.getT1());
                return data;
            });
    }

}
