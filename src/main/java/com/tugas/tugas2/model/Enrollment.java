package com.tugas.tugas2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//table relasi user to cours
@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
public class Enrollment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  private Course course;

  private float grade;

  private LocalDateTime timeAttendanceUjian;

  public Enrollment(User user, Course course) {
    this.user = user;
    this.course = course;
    this.grade = 0;
  }
}
