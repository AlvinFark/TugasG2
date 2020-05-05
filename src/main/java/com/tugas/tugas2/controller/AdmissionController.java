package com.tugas.tugas2.controller;

import com.tugas.tugas2.model.Jurusan;
import com.tugas.tugas2.model.User;
import com.tugas.tugas2.model.enums.Status;
import com.tugas.tugas2.model.enums.StatusName;
import com.tugas.tugas2.payload.request.AdmissionRequest;
import com.tugas.tugas2.payload.response.BasicResponse;
import com.tugas.tugas2.payload.response.ObjectResponse;
import com.tugas.tugas2.repository.JurusanRepository;
import com.tugas.tugas2.repository.StatusRepository;
import com.tugas.tugas2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//untuk keperluan mendaftar dan melihat status pendaftaran
@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  JurusanRepository jurusanRepository;

  @Autowired
  StatusRepository statusRepository;

  //melihat data pendaftar, termasuk status pendaftaran
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable long id){

    User user = userRepository.findById(id).orElse(null);

    //handling user not found
    if (user==null){
      return new ResponseEntity<>(new BasicResponse("User with this id not found"), HttpStatus.NOT_FOUND);
    };

    return new ResponseEntity<>(new ObjectResponse("success", user), HttpStatus.OK);
  }

  //mendaftar
  @PostMapping()
  public ResponseEntity<?> createUser(@RequestBody AdmissionRequest admissionRequest){

    Jurusan jurusan = jurusanRepository.findById(admissionRequest.getJurusanId()).orElse(null);

    //handling jurusan not found
    if (jurusan==null){
      return new ResponseEntity<>(new BasicResponse("Jurusan with this id not found"), HttpStatus.NOT_FOUND);
    }

    Status status = statusRepository.findByName(StatusName.WAITING).orElse(null);

    User user = new User(admissionRequest.getName());
    user.setJurusan(jurusan);
    user.setStatus(status);

    return new ResponseEntity<>(new ObjectResponse("success", userRepository.save(user)), HttpStatus.OK);
  }

  //tidak ada delete dan edit karena pendaftar tidak bisa cancel dan mengubah nama serta jurusan setelah mendaftar
}
