package com.tugas.tugas2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "jurusans")
@Data
@NoArgsConstructor
public class Jurusan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  private String name;

  public Jurusan(long id, String name) {
    this.id = id;
    this.name = name;
  }
}
