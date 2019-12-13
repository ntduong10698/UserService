package com.tavi.duongnt.user_service.entities.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private Object data;

    public static JsonResult build(String message, Object data){
        return new JsonResult(message, data);
    }
}
