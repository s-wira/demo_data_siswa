package com.dimata.demo.sekolah.demo_data_siswa.core.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunctionOpt {
    String function;

    @Override
    public String toString(){
        return function;
    }
}
