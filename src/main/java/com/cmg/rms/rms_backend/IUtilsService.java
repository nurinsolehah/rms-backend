package com.cmg.rms.rms_backend;

import java.util.List;

import com.cmg.rms.rms_backend.dto.DropdownDTO;

public interface IUtilsService {
     List<DropdownDTO> getFoodCategory();
     void removeFoodCategory(String categoryCode);
}
