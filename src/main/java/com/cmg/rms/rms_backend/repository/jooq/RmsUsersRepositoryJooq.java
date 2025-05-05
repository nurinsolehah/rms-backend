package com.cmg.rms.rms_backend.repository.jooq;

import static org.jooq.impl.DSL.*;

import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.security.UsersDTO;
import com.cmg.rms.rms_backend.util.JooqUtil;
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
public class RmsUsersRepositoryJooq {
  private final DSLContext dsl;

  public List<UsersDTO> getUserList(String username, String userRole, PaginationRequestDTO pgDTO) {
    final String methodName = "getUserList";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    Condition condition = noCondition();
    condition =
        JooqUtil.andCondition(condition, field("username"), Field::containsIgnoreCase, username);
    condition = JooqUtil.andCondition(condition, field("user_role"), Field::eq, userRole);

    Select<?> query =
        dsl.select(
                field("user_id", Long.class),
                field("username", String.class).as("username"),
                field("user_role", String.class).as("userRole"))
            .from(table(TableUtil.RMS_USERS))
            .where(condition)
            .orderBy(CoreUtilsRepositoryJooq.getOrderByField(pgDTO.sort(), pgDTO.sortDirection()))
            .offset((pgDTO.page() - 1) * pgDTO.size())
            .limit(pgDTO.size());

    log.info(LogUtil.QUERY, query);

    List<UsersDTO> result = query.fetchInto(UsersDTO.class);

    log.info(LogUtil.EXIT_JOOQ, methodName);
    return result;
  }

  {
  }
}
