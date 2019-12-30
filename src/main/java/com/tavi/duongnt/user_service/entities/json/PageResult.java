package com.tavi.duongnt.user_service.entities.json;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private Object data;

    private Long totalElements;

    private Integer totalPages;

    public static PageResult build(String message, Object data, Long totalElements, Integer totalPages){
        return new PageResult(message, data, totalElements, totalPages);
    }
}
