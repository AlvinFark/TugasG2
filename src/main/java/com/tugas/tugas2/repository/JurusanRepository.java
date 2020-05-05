package com.tugas.tugas2.repository;

import com.tugas.tugas2.model.Jurusan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JurusanRepository extends JpaRepository<Jurusan, Long> {
}
