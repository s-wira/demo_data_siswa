package com.dimata.demo.sekolah.demo_data_siswa.forms;

import java.util.List;

import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSekolah;
import com.dimata.demo.sekolah.demo_data_siswa.models.table.DataSiswa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListSiswaForm {
    private DataSekolah sekolah;
    private List<DataSiswa> daftarSiswa;
}
