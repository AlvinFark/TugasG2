package com.tugas.tugas2.service;

import com.tugas.tugas2.model.Course;

import java.util.Set;

public interface CourseService {
  Set<Course> findAllByUser_Id(Long id);
}
