package com.cmg.rms.rms_backend.repository.jooq;

import static org.jooq.impl.DSL.*;

import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
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

    Field<String> recipeName = field("RH.recipe_name", String.class);
    Field<String> description = field("RH.description", String.class);
    Field<String> category = field("FC.category_name", String.class);
    Field<String> image = val(null, String.class);
    Field<String> recipeIngredients = field("RD.recipe_ingredients", String.class);
    Field<String> recipeInstructions = field("RD.recipe_instructions", String.class);

    Select<?> query =
        dsl.select(recipeName, description, category, image, recipeIngredients, recipeInstructions)
            .from(TableUtil.table(TableUtil.RMS_RECIPE_HDRS, "RH"))
            .leftJoin(TableUtil.table(TableUtil.RMS_RECIPE_DTLS, "RD"))
            .on(field("RH.recipe_id").eq(field("RD.recipe_id")))
            .leftJoin(TableUtil.table(TableUtil.RMS_FOOD_CATEGORY, "FC"))
            .on(field("RH.category_id").eq(field("FC.category_id")))
            .where(condition);

    log.info(LogUtil.QUERY, query);

    RecipeDetailsDTO recipeDetails = query.fetchOneInto(RecipeDetailsDTO.class);

    log.info(LogUtil.EXIT_JOOQ, methodName);
    return recipeDetails;
  }
}
