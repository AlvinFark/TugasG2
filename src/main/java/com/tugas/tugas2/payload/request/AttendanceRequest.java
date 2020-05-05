package com.tugas.tugas2.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceRequest {
  long idUser;
  long idCourse;
  LocalDateTime time;
}
