package com.cmg.rms.rms_backend.util;

import static org.jooq.impl.DSL.table;

import java.util.function.BiFunction;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

public class JooqUtil {

  public static <T> Condition condition(
      Field<T> field, BiFunction<Field<T>, T, Condition> comparison, T value) {
    return comparison.apply(field, value);
  }

  public static <T> Condition andCondition(
      Condition condition, Field<T> field, BiFunction<Field<T>, T, Condition> comparison, T value) {
    if (value != null && !value.equals("")) {
      condition = condition.and(comparison.apply(field, value));
    }
    return condition;
  }

  public static <T> Condition orCondition(
      Condition condition, Field<T> field, BiFunction<Field<T>, T, Condition> comparison, T value) {
    if (value != null && !value.equals("")) {
      condition = condition.or(comparison.apply(field, value));
    }
    return condition;
  }

  public static Table<Record> tableTest(String field, String alias) {
    return table(field);
  }
}
