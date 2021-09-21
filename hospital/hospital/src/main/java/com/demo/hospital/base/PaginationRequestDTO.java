package com.demo.hospital.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaginationRequestDTO {

    @NotNull
    Integer totalRecordPerPage;
    @NotNull
    Integer page;
    @NotNull
    String orderBy;
    @NotNull
    String order;
    @NotNull
    Boolean isFilterApply;

}
