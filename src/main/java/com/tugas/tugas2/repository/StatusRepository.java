package com.tugas.tugas2.repository;

import com.tugas.tugas2.model.enums.Status;
import com.tugas.tugas2.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
  Optional<Status> findByName(StatusName statusName);
}
