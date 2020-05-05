package com.tugas.tugas2.model.enums;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "status")
@Data
@NoArgsConstructor
public class Status {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private StatusName name;

  public Status(StatusName name) {
    this.name = name;
  }
}
