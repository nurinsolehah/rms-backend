package com.cmg.rms.rms_backend.security;

import static org.jooq.impl.DSL.*;

import com.cmg.rms.rms_backend.util.LogUtil;
import com.cmg.rms.rms_backend.util.TableUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class UsersRepositoryJooq {
  private final DSLContext dsl;

  public UsersDTO findByUserId(Long userId) {

    Select<?> query =
        dsl.select(
                field("usr_id", Long.class),
                field("username", String.class),
                field("email", String.class),
                field("contact_no", String.class),
                field("user_role", String.class))
            .from(table(TableUtil.RMS_USERS))
            .where(field("usr_id").eq(userId));

    log.info(LogUtil.QUERY, query);
    return query.fetchOneInto(UsersDTO.class);
  }
}
