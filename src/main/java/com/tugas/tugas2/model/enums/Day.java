package com.tugas.tugas2.model.enums;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "day")
@Data
@NoArgsConstructor
public class Day {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private DayOfWeek name;

  public Day(DayOfWeek name) {
    this.name = name;
  }
}
