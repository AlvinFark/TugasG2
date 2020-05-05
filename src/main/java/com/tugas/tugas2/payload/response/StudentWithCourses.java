package com.tugas.tugas2.payload.response;

import com.tugas.tugas2.model.Course;
import com.tugas.tugas2.model.Jurusan;
import com.tugas.tugas2.model.User;
import com.tugas.tugas2.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class StudentWithCourses {

  private long id;
  private String name;
  private Status status;
  private Jurusan jurusan;
  private Set<Course> courses;

  public StudentWithCourses(User user, Set<Course> courses){
    this.id = user.getId();
    this.name = user.getName();
    this.status = user.getStatus();
    this.jurusan = user.getJurusan();
    this.courses = courses;
  }
}
