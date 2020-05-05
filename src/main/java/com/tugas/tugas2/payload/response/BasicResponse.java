package com.tugas.tugas2.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicResponse {

  String message;

  public BasicResponse(String message) {
    this.message = message;
  }
}
