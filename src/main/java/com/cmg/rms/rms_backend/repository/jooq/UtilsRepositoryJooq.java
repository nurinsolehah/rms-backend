package com.cmg.rms.rms_backend.repository.jooq;

import static org.jooq.impl.DSL.*;

import com.cmg.rms.rms_backend.dto.DropdownDTO;
import com.cmg.rms.rms_backend.util.LogUtil;
import com.cmg.rms.rms_backend.util.TableUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Select;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class UtilsRepositoryJooq {
  private final DSLContext dsl;

  public List<DropdownDTO> getFoodCategory() {
    final String methodName = "getFoodCategory";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    Condition condition = noCondition();
    condition = condition.and(field("RFC.active_flag").eq("A"));

    Field<String> value = field("category_code", String.class).as("value");
    Field<String> description = field("category_name", String.class).as("description");

    Select<?> query =
        dsl.select(value, description)
            .from(TableUtil.table(TableUtil.RMS_FOOD_CATEGORY, "RFC"))
            .where(condition)
            .orderBy(field("category_code").asc());

    log.info(LogUtil.QUERY, query);

    List<DropdownDTO> foodCategoryList = query.fetchInto(DropdownDTO.class);

    log.info(LogUtil.EXIT_JOOQ, methodName);
    return foodCategoryList;
  }
}
