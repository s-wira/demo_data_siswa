package com.dimata.demo.sekolah.demo_data_siswa.services.crude;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;
import com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler.DataSiswaDbHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataSiswaCrude {
    
    @Autowired
    private DataSiswaDbHandler dataSiswaDbHandler;

    public static Option initOption(DataSiswa record) {
        return new Option(record);
    }

    public Mono<DataSiswa> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataSiswa> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataSiswa> savedRecord = dataSiswaDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataSiswa> updateRecord(Option option) {
        return dataSiswaDbHandler.update(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataSiswa record;
        
        public Option(DataSiswa record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
