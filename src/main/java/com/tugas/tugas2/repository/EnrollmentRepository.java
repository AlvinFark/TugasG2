package com.tugas.tugas2.repository;

import com.tugas.tugas2.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  Set<Enrollment> findAllByUser_Id(Long id);
  Optional<Enrollment> findByCourse_IdAndUser_Id(Long courseId, Long userId);
}
