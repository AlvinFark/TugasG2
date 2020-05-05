package com.tugas.tugas2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
@Data
@NoArgsConstructor
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Enrollment enrollment;

  private LocalDateTime time;

  public Attendance(Enrollment enrollment, LocalDateTime time) {
    this.enrollment = enrollment;
    this.time = time;
  }
}
