package com.tugas.tugas2.controller;

import com.tugas.tugas2.model.Course;
import com.tugas.tugas2.model.User;
import com.tugas.tugas2.model.Enrollment;
import com.tugas.tugas2.model.enums.StatusName;
import com.tugas.tugas2.payload.request.KRSRequest;
import com.tugas.tugas2.payload.response.BasicResponse;
import com.tugas.tugas2.payload.response.ObjectResponse;
import com.tugas.tugas2.payload.response.StudentWithCourses;
import com.tugas.tugas2.repository.CourseRepository;
import com.tugas.tugas2.repository.UserRepository;
import com.tugas.tugas2.repository.EnrollmentRepository;
import com.tugas.tugas2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//untuk keperluan mahasiswa yang telah diterima untuk mengurus masalah kelas seperti isi krs dan lihat nilai
@RestController
@RequestMapping("/api/student")
public class StudentController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  CourseService courseService;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  EnrollmentRepository enrollmentRepository;

  //melihat data diri mahasiswa, termasuk juga melihat kelas yang diambil dan nilainya
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable long id){

    User user = userRepository.findById(id).orElse(null);

    //handling user not found
    if (user==null){
      return new ResponseEntity<>(new BasicResponse("User with this id not found"), HttpStatus.NOT_FOUND);
    };

    //handling user that isn't student
    if (user.getStatus().getName() != StatusName.ACCEPTED){
      return new ResponseEntity<>(new BasicResponse("You are not student, please view yourself at admission page"), HttpStatus.UNAUTHORIZED);
    }

    StudentWithCourses student = new StudentWithCourses(user,courseService.findAllByUser_Id(user.getId()));

    return new ResponseEntity<>(new ObjectResponse("success", student), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> addCoursesToStudent(@RequestBody KRSRequest krsRequest){

    User user = userRepository.findById(krsRequest.getIdUser()).orElse(null);

    //handling user not found
    if (user==null){
      return new ResponseEntity<>(new BasicResponse("User with this id not found"), HttpStatus.NOT_FOUND);
    };

    //handling user that isn't student
    if (user.getStatus().getName() != StatusName.ACCEPTED){
      return new ResponseEntity<>(new BasicResponse("You are not student, please view yourself at admission page"), HttpStatus.UNAUTHORIZED);
    }

    Course course = courseRepository.findById(krsRequest.getIdCourse()).orElse(null);

    //handling course not found
    if (course==null){
      return new ResponseEntity<>(new BasicResponse("Course with this id not found"), HttpStatus.NOT_FOUND);
    }

    //handling course is for jurusan who student isn't enrolled to
    if (course.getJurusan()!=user.getJurusan()){
      return new ResponseEntity<>(new BasicResponse("This course isn't available for your jurusan"), HttpStatus.UNAUTHORIZED);
    }

    //handling course isn't open this time
    if (!course.isAvailable()){
      return new ResponseEntity<>(new BasicResponse("This course isn't available at this time"), HttpStatus.UNAUTHORIZED);
    }

    Enrollment enrollment = new Enrollment(user, course);
    StudentWithCourses student = new StudentWithCourses(user,courseService.findAllByUser_Id(user.getId()));

    //handling if the student already enrolled to that class
    for (Course c:student.getCourses()) {
      if (c==course){
        return new ResponseEntity<>(new ObjectResponse("You've already enrolled to this course", student), HttpStatus.CONFLICT);
      }
    }

    enrollmentRepository.save(enrollment);
    student = new StudentWithCourses(user,courseService.findAllByUser_Id(user.getId()));
    return new ResponseEntity<>(new ObjectResponse("success", student), HttpStatus.OK);
  }
}
