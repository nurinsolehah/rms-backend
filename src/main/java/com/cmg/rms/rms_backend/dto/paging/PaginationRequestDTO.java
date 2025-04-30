package com.cmg.rms.rms_backend.dto.paging;

public record PaginationRequestDTO(String sort, String sortDirection, Long page, Long size) {
}
