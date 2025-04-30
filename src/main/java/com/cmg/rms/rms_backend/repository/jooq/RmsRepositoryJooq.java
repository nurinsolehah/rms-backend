package com.cmg.rms.rms_backend.repository.jooq;

import static org.jooq.impl.DSL.*;

import com.cmg.rms.rms_backend.dto.CreateRecipeRequestDTO;
import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.util.JooqUtil;
import com.cmg.rms.rms_backend.util.LogUtil;
import com.cmg.rms.rms_backend.util.TableUtil;
import java.time.LocalDateTime;
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
public class RmsRepositoryJooq {

  private final DSLContext dsl;

  public List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO) {
    final String methodName = "getRecipeList";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    Condition condition = noCondition();
    condition =
        JooqUtil.andCondition(
            condition, field("recipe_name"), Field::containsIgnoreCase, requestDTO.recipeName());

    condition =
        JooqUtil.andCondition(condition, field("category"), Field::eq, requestDTO.category());

    Field<Long> recipeId = field("recipe_id", Long.class).as("recipeId");
    Field<String> recipeName = field("recipe_name", String.class).as("recipeName");
    Field<String> description = field("description", String.class).as("description");
    Field<String> category = field("category", String.class).as("category");

    Select<?> query =
        dsl.select(recipeId, recipeName, description, category)
            .from(TableUtil.table(TableUtil.RMS_RECIPE_HDRS, "RRH"))
            .where(condition)
            .orderBy(CoreUtilsRepositoryJooq.getOrderByField(pgDTO.sort(), pgDTO.sortDirection()))
            .offset((pgDTO.page() - 1) * pgDTO.size())
            .limit(pgDTO.size());

    log.info(LogUtil.QUERY, query);

    List<RecipeListDTO> recipeList = query.fetchInto(RecipeListDTO.class);

    log.info(LogUtil.EXIT_JOOQ, methodName);

    return recipeList;
  }

  public Long getRecipeListPages(RecipeListRequestDTO requestDTO) {
    final String methodName = "getRecipeListPages";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    Condition condition = noCondition();
    condition =
        JooqUtil.andCondition(
            condition, field("recipe_name"), Field::containsIgnoreCase, requestDTO.recipeName());

    condition =
        JooqUtil.andCondition(condition, field("category"), Field::eq, requestDTO.category());

    Select<?> query =
        dsl.selectCount().from(TableUtil.table(TableUtil.RMS_RECIPE_HDRS, "RRH")).where(condition);

    log.info(LogUtil.QUERY, query);

    Long result = query.fetchOneInto(Long.class);

    log.info(LogUtil.EXIT_JOOQ, methodName);
    return result;
  }

  public RecipeDetailsDTO getRecipeDetails(Long recipeId) {
    final String methodName = "getRecipeDetails";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    Condition condition = noCondition();
    condition = condition.and(field("RH.recipe_id").eq(recipeId));
    condition = condition.and(field("RH.active_flag").eq("A"));

    Field<String> recipeName = field("RH.recipe_name", String.class).as("recipeName");
    Field<String> description = field("RH.description", String.class).as("description");
    Field<String> category = field("RH.category", String.class).as("category");
    Field<String> recipeIngredients =
        field("RD.recipe_ingredients", String.class).as("recipeIngredients");
    Field<String> recipeInstructions =
        field("RD.recipe_instructions", String.class).as("recipeInstructions");

    Select<?> query =
        dsl.select(recipeName, description, category, recipeIngredients, recipeInstructions)
            .from(TableUtil.table(TableUtil.RMS_RECIPE_HDRS, "RH"))
            .leftJoin(TableUtil.table(TableUtil.RMS_RECIPE_DTLS, "RD"))
            .on(field("RH.recipe_id").eq(field("RD.recipe_id")))
            .where(condition);

    log.info(LogUtil.QUERY, query);

    RecipeDetailsDTO recipeDetails = query.fetchOneInto(RecipeDetailsDTO.class);

    log.info(LogUtil.EXIT_JOOQ, methodName);
    return recipeDetails;
  }

  public void addRecipe(CreateRecipeRequestDTO requestDTO) {
    final String methodName = "addRecipe";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    dsl.transaction(
        ctx -> {
          dsl.insertInto(TableUtil.table(TableUtil.RMS_RECIPE_HDRS))
              .set(field("recipe_name"), requestDTO.recipeName())
              .set(field("description"), requestDTO.description())
              .set(field("category"), requestDTO.category())
              .set(field("created_by"), 1L)
              .set(field("created_date"), LocalDateTime.now())
              .set(field("updated_by"), 1L)
              .set(field("updated_date"), LocalDateTime.now())
              .set(field("active_flag"), "A")
              .execute();
        });

    log.info(LogUtil.EXIT_JOOQ, methodName);
  }

  public void deleteRecipe(Long recipeId) {
    final String methodName = "deleteRecipe";
    log.info(LogUtil.ENTRY_JOOQ, methodName);

    dsl.transaction(
        ctx -> {
          dsl.deleteFrom(table(TableUtil.RMS_RECIPE_HDRS))
              .where(field("recipe_id").eq(recipeId))
              .execute();
        });

    log.info(LogUtil.EXIT_JOOQ, methodName);
  }
}
