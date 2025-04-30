package com.cmg.rms.rms_backend.util;

import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

public class TableUtil {

  public static String RMS_RECIPE_HDRS = "rms_recipe_hdrs";

  public static String RMS_RECIPE_DTLS = "rms_recipe_dtls";

  public static String RMS_USERS = "rms_users";

  public static String RMS_FOOD_CATEGORY = "rms_food_category";

  /**
   * @param table required
   * @param alias optional
   * @return table with tableAlias
   */
  public static Table<Record> table(String table, String alias) {
    String tableName = "%s %s";
    if (alias == null) {
      alias = "";
    }
    return DSL.table(String.format(tableName, table, alias));
  }

  public static Table<Record> table(String table) {
    return DSL.table(table);
  }
}
