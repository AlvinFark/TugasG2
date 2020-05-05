package com.tugas.tugas2.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdmissionRequest {

  String name;
  long jurusanId;

  public AdmissionRequest(String name, long jurusanId) {
    this.name = name;
    this.jurusanId = jurusanId;
  }
}
