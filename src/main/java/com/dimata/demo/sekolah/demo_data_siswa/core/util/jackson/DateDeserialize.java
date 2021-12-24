package com.dimata.demo.sekolah.demo_data_siswa.core.util.jackson;

import java.time.LocalDate;

import com.dimata.demo.sekolah.demo_data_siswa.core.util.FormatUtil;
import com.fasterxml.jackson.databind.util.StdConverter;

public class DateDeserialize extends StdConverter<String, LocalDate> {
    @Override
    public LocalDate convert(String value) {
        return FormatUtil.convertDateToLocalDate(value, "dd-MM-yyyy");
    }
}
