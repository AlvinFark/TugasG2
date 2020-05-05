package com.tugas.tugas2.service;

import java.time.LocalDate;

public interface AttendanceService {
  boolean isExistByEnrollment_Course_IdAndEnrollment_User_IdAndLocalDate(Long courseId, Long userId, LocalDate localDate);
}
