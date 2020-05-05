package com.tugas.tugas2.controller;

import com.tugas.tugas2.model.Attendance;
import com.tugas.tugas2.model.Course;
import com.tugas.tugas2.model.Enrollment;
import com.tugas.tugas2.model.User;
import com.tugas.tugas2.model.enums.StatusName;
import com.tugas.tugas2.payload.request.KRSRequest;
import com.tugas.tugas2.payload.response.BasicResponse;
import com.tugas.tugas2.payload.response.ObjectResponse;
import com.tugas.tugas2.repository.AttendanceRepository;
import com.tugas.tugas2.repository.CourseRepository;
import com.tugas.tugas2.repository.UserRepository;
import com.tugas.tugas2.repository.EnrollmentRepository;
import com.tugas.tugas2.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

//untuk absen kelas dan absen ujian
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  EnrollmentRepository enrollmentRepository;

  @Autowired
  AttendanceService attendanceService;

  @Autowired
  AttendanceRepository attendanceRepository;

  @PostMapping
  public ResponseEntity<?> absen(@RequestBody KRSRequest attendanceRequest){

    User user = userRepository.findById(attendanceRequest.getIdUser()).orElse(null);

    //handling user not found
    if (user==null){
      return new ResponseEntity<>(new BasicResponse("User with this id not found"), HttpStatus.NOT_FOUND);
    };

    //handling user that isn't student
    if (user.getStatus().getName() != StatusName.ACCEPTED){
      return new ResponseEntity<>(new BasicResponse("You are not student, please view yourself at admission page"), HttpStatus.UNAUTHORIZED);
    }

    Course course = courseRepository.findById(attendanceRequest.getIdCourse()).orElse(null);

    //handling course not found
    if (course==null){
      return new ResponseEntity<>(new BasicResponse("Course with this id not found"), HttpStatus.NOT_FOUND);
    }

    Enrollment enrollment = enrollmentRepository.findByCourse_IdAndUser_Id(course.getId(),user.getId()).orElse(null);

    //handling user not enrolled to class
    if (enrollment==null){
      return new ResponseEntity<>(new BasicResponse("You aren't enrolled to this course"), HttpStatus.UNAUTHORIZED);
    }

    //handling absen diluar waktu kelas
    if (LocalDateTime.now().getDayOfWeek() != course.getDay().getName() ||
        LocalDateTime.now().toLocalTime().isAfter(course.getTimeStart()) ||
        LocalDateTime.now().toLocalTime().isBefore(course.getTimeEnd())){
      return new ResponseEntity<>(new BasicResponse("The class isn't started yet or already finished"), HttpStatus.BAD_REQUEST);
    }

    //handling sudah pernah absen di hari ini di kelas ini
    if (attendanceService.isExistByEnrollment_Course_IdAndEnrollment_User_IdAndLocalDate(course.getId(),user.getId(), LocalDate.now())){
      return new ResponseEntity<>(new BasicResponse("You've already logged attendance for today"), HttpStatus.CONFLICT);
    }

    Attendance attendance = new Attendance(
        enrollmentRepository.findByCourse_IdAndUser_Id(course.getId(),user.getId()).orElse(null),
        LocalDateTime.now()
    );

    return new ResponseEntity<>(new ObjectResponse("success", attendanceRepository.save(attendance)), HttpStatus.OK);
  }

  @PostMapping("/ujian")
  public ResponseEntity<?> absenUjian(@RequestBody KRSRequest attendanceRequest){
    User user = userRepository.findById(attendanceRequest.getIdUser()).orElse(null);

    //handling user not found
    if (user==null){
      return new ResponseEntity<>(new BasicResponse("User with this id not found"), HttpStatus.NOT_FOUND);
    };

    //handling user that isn't student
    if (user.getStatus().getName() != StatusName.ACCEPTED){
      return new ResponseEntity<>(new BasicResponse("You are not student, please view yourself at admission page"), HttpStatus.UNAUTHORIZED);
    }

    Course course = courseRepository.findById(attendanceRequest.getIdCourse()).orElse(null);

    //handling course not found
    if (course==null){
      return new ResponseEntity<>(new BasicResponse("Course with this id not found"), HttpStatus.NOT_FOUND);
    }

    Enrollment enrollment = enrollmentRepository.findByCourse_IdAndUser_Id(course.getId(),user.getId()).orElse(null);

    //handling user not enrolled to class
    if (enrollment==null){
      return new ResponseEntity<>(new BasicResponse("You aren't enrolled to this course"), HttpStatus.UNAUTHORIZED);
    }

    //handling belum ada pengumuman waktu ujian
    if (course.getTimeUjianStart()==null||course.getTimeUjianEnd()==null){
      return new ResponseEntity<>(new BasicResponse("Ujian time haven't been set"), HttpStatus.UNAUTHORIZED);
    }

    //handling sudah pernah absen ujian
    if (enrollment.getTimeAttendanceUjian()!=null){
      return new ResponseEntity<>(new BasicResponse("You've already logged attendance for today"), HttpStatus.CONFLICT);
    }

    //handling absen diluar waktu ujian
    if (LocalDateTime.now().getDayOfWeek() != course.getTimeUjianStart().getDayOfWeek() ||
        LocalDateTime.now().isAfter(course.getTimeUjianStart()) ||
        LocalDateTime.now().isBefore(course.getTimeUjianEnd())){
      return new ResponseEntity<>(new BasicResponse("The ujian isn't started yet or already finished"), HttpStatus.BAD_REQUEST);
    }

    enrollment.setTimeAttendanceUjian(LocalDateTime.now());
    return new ResponseEntity<>(new ObjectResponse("success", enrollmentRepository.save(enrollment)), HttpStatus.CONFLICT);
  }
}
