package com.tugas.tugas2.service.impl;

import com.tugas.tugas2.model.Attendance;
import com.tugas.tugas2.repository.AttendanceRepository;
import com.tugas.tugas2.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class AttendanceServiceImpl implements AttendanceService {

  @Autowired
  AttendanceRepository attendanceRepository;

  @Override
  public boolean isExistByEnrollment_Course_IdAndEnrollment_User_IdAndLocalDate(Long courseId, Long userId, LocalDate localDate) {

    Set<Attendance> attendances = attendanceRepository.getAllByEnrollment_Course_IdAndEnrollment_User_Id(courseId,userId);
    for (Attendance attendance:attendances) {
      if (attendance.getTime().toLocalDate().equals(localDate)){
        return true;
      }
    }
    return false;
  }
}
