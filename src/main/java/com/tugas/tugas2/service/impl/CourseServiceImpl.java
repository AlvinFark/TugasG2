package com.tugas.tugas2.service.impl;

import com.tugas.tugas2.model.Course;
import com.tugas.tugas2.model.Enrollment;
import com.tugas.tugas2.repository.EnrollmentRepository;
import com.tugas.tugas2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

  @Autowired
  EnrollmentRepository enrollmentRepository;

  @Override
  public Set<Course> findAllByUser_Id(Long id) {
    Set<Enrollment> enrollments = enrollmentRepository.findAllByUser_Id(id);
    Set<Course> courses = new java.util.HashSet<>(Collections.emptySet());

    for (Enrollment enrollment : enrollments) {
      courses.add(enrollment.getCourse());
    }

    return courses;
  }
}
