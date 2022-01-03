package com.dimata.demo.sekolah.demo_data_siswa.services.crude.Datasekolah;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;
import com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler.DataSekolahDbHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataSekolahCrude {
    @Autowired
    private DataSekolahDbHandler dataSekolahDbHandler;

    public static Option initOption(DataSekolah record) {
        return new Option(record);
    }

    public Mono<DataSekolah> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataSekolah> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataSekolah> savedRecord = dataSekolahDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataSekolah> updateRecord(Option option) {
        return dataSekolahDbHandler.update(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataSekolah record;
        
        public Option(DataSekolah record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
