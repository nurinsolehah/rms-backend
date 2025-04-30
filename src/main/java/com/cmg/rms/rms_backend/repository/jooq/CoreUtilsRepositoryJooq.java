package com.cmg.rms.rms_backend.repository.jooq;

import static org.jooq.impl.DSL.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class CoreUtilsRepositoryJooq {

  private final DSLContext dsl;

  public static SortField<Object> getOrderByField(String sort, String sortDirection) {
    return sortDirection.equals("asc") ? field(name(sort)).asc() : field(name(sort)).desc();
  }

  public static List<SortField<?>> getOrderByField(
      String sort, String sortDirection, List<SortField<?>> defaultSortField) {
    List<SortField<?>> orderBy = new ArrayList<>();
    if (sort != null) {
      orderBy.add(getOrderByField(sort, sortDirection));
    }
    orderBy.addAll(defaultSortField);

    return orderBy;
  }

  public static List<SortField<?>> getOrderByField(List<SortField<?>> sortField) {
    List<SortField<?>> orderBy = new ArrayList<>();
    orderBy.addAll(sortField);
    return orderBy;
  }

  /**
   * Return a field that will concatenate the user's first and last names into a single string,
   * separated by a space. The field names are qualified with the given prefix.
   *
   * @param prefix the prefix for the field names
   * @return a field that will produce a string with the user's full name
   */
  public Field<String> getUserFullName(String prefix) {
    return trim(
        function(
            "concat",
            String.class,
            field(prefix + ".usr_firstname"),
            space(1),
            field(prefix + ".usr_lastname")));
  }
}
