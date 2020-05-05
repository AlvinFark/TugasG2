package com.tugas.tugas2.repository;

import com.tugas.tugas2.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
  Set<Attendance> getAllByEnrollment_Course_IdAndEnrollment_User_Id(Long courseId, Long userId);
}
