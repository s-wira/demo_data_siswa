package com.dimata.demo.sekolah.demo_data_siswa.services.api;

import com.dimata.demo.sekolah.demo_data_siswa.forms.DataSiswaForm;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.services.crude.DataSiswaCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class DataSiswaApi {
    
    @Autowired
    private DataSiswaCrude dataSiswaCrude;

    public Mono<DataSiswa> createDataSiswa(DataSiswaForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataSiswaCrude.Option option = DataSiswaCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataSiswaCrude::create);
    }
}
