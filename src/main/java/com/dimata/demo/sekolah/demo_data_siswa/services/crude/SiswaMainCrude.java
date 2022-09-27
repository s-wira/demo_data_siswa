package com.dimata.demo.sekolah.demo_data_siswa.services.crude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.SiswaMain;
import com.dimata.demo.sekolah.demo_data_siswa.services.dbHandler.SiswaMainDbHandler;

import lombok.Data;
import lombok.AccessLevel;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SiswaMainCrude {
    
    @Autowired
    private SiswaMainDbHandler siswaMainDbHandler;

    public static Option initOption(SiswaMain record) {
        return new Option(record);
    }

    public Mono<SiswaMain> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<SiswaMain> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<SiswaMain> savedRecord = siswaMainDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<SiswaMain> updateRecord(Option option) {
        return siswaMainDbHandler.updateOnly(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final SiswaMain record;
        
        public Option(SiswaMain record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
