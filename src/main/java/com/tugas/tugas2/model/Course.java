package com.tugas.tugas2.model;

import com.tugas.tugas2.model.enums.Day;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  private String name;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  private Jurusan jurusan;

  @NotNull
  private boolean isAvailable;

  @ManyToOne(fetch = FetchType.EAGER)
  private Day day;

  private LocalTime timeStart;

  private LocalTime timeEnd;

  private LocalDateTime timeUjianStart;

  private LocalDateTime timeUjianEnd;

  public Course(String name, Jurusan jurusan, boolean isAvailable, Day day, LocalTime timeStart, LocalTime timeEnd) {
    this.name = name;
    this.jurusan = jurusan;
    this.isAvailable = isAvailable;
    this.day = day;
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
  }
}
