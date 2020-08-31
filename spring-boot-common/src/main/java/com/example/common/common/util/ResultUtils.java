package com.example.common.common.util;

import com.example.common.common.model.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public enum ResultUtils {

  INSTANCE;


  public <T> Result<T> getFailResult(BindingResult bindingResult, Result<T> result) {

    result.setStatus(Result.fail);
    List<ObjectError> errorList = bindingResult.getAllErrors();
    StringBuilder messages = new StringBuilder();
    errorList.forEach(error -> {
      messages.append(error.getDefaultMessage()).append(";");
    });
    result.setMessage(messages.toString());
    return result;
  }
}
