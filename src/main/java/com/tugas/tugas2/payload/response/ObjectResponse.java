package com.tugas.tugas2.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ObjectResponse extends BasicResponse {

  Object result;

  public ObjectResponse(String message, Object result) {
    super(message);
    this.result = result;
  }
}
