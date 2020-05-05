package com.tugas.tugas2.model;

import com.tugas.tugas2.model.enums.Status;
import com.tugas.tugas2.model.enums.StatusName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  private Status status;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  private Jurusan jurusan;

  public User(String name) {
    this.name = name;
  }
}
