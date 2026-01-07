package com.datasoft.bkash.ea.utils;

import com.datasoft.bkash.ea.model.ReportFilterCriteria;
import com.datasoft.bkash.ea.model.filter.FilterCriteria;
import com.datasoft.bkash.ea.model.plan.Assessees;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;

@Slf4j
public class QueryBuilderUtil {
    private static final String where = "WHERE ";

    public static String createQueryCondition(FilterCriteria filterCriteria) {
        StringBuilder condition = new StringBuilder();
        Class<?> clazz = filterCriteria.getClass();
        Field[] f = filterCriteria.getClass().getDeclaredFields();
        for (Field fld : f) {
            Field field = org.springframework.util.ReflectionUtils.findField(clazz, fld.getName());
            org.springframework.util.ReflectionUtils.makeAccessible(field);
            try {
                if (field.get(filterCriteria) != null) {
                    if (fld.getType().getTypeName().equals("java.lang.String")) {
                        condition.append(getColumnName(fld.getName())).append(" = '").append(Objects.nonNull(field.get(filterCriteria))? field.get(filterCriteria).toString().replace("'", "\\'"):field.get(filterCriteria)).append("' AND ");
                    } else if (fld.getType().getTypeName().equals("java.lang.Boolean") && fld.getName().equals("employeeStatus")) {
                        condition.append("IFNULL(employee_status,FALSE)").append(" = ").append(field.get(filterCriteria)).append(" AND ");
                    } else {
                        condition.append(getColumnName(fld.getName())).append(" = ").append(field.get(filterCriteria)).append(" AND ");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String clause = condition.toString();
        clause = (!clause.equals("")) ? where + clause.substring(0, clause.length() - 5) : "";
        log.info("Where Condition >> {}", clause);
        return clause;
    }

    public static String createAndCondition(FilterCriteria filterCriteria) {
        StringBuilder condition = new StringBuilder();
        Class<?> clazz = filterCriteria.getClass();
        Field[] f = filterCriteria.getClass().getDeclaredFields();
        for (Field fld : f) {
            Field field = org.springframework.util.ReflectionUtils.findField(clazz, fld.getName());
            org.springframework.util.ReflectionUtils.makeAccessible(field);
            try {
                if (field.get(filterCriteria) != null) {
                    if (fld.getType().getTypeName().equals("java.lang.String")) {
                        condition.append(" AND ").append(getColumnName(fld.getName())).append(" = '").append(field.get(filterCriteria)).append("'");
                    } else if (fld.getType().getTypeName().equals("java.lang.Boolean") && fld.getName().equals("isAssigned")) {
                        condition.append(" AND ").append("IFNULL(is_assigned,FALSE)").append(" = ").append(field.get(filterCriteria));
                    } else {
                        condition.append(" AND ").append(getColumnName(fld.getName())).append(" = ").append(field.get(filterCriteria));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("And Condition >> {}", condition.toString());
        return condition.toString();
    }

    public static String createAndCondition(Assessees filterCriteria) {
        StringBuilder condition = new StringBuilder();
        String finalQuery = "";
        if (filterCriteria != null) {
            Class<?> clazz = filterCriteria.getClass();
            Field[] f = filterCriteria.getClass().getDeclaredFields();
            for (Field fld : f) {
                Field field = org.springframework.util.ReflectionUtils.findField(clazz, fld.getName());
                org.springframework.util.ReflectionUtils.makeAccessible(field);
                try {
                    if (field.get(filterCriteria) != null) {
                        if (fld.getType().getTypeName().equals("java.lang.String")) {
                            condition.append(" AND ").append(getColumnName(fld.getName())).append(" = '").append(field.get(filterCriteria)).append("'");
                        } else {
                            condition.append(" AND ").append(getColumnName(fld.getName())).append(" = ").append(field.get(filterCriteria));
                        }
                    }
                } catch (Exception e) {
                    log.info("Exception >> {}", e.getMessage());
                }
            }

            if (!condition.toString().equals("")) {
                finalQuery = condition.substring(4);
            }

        }
        log.info("And Condition >> {}", finalQuery);
        return finalQuery;
    }

    public static String createAndConditionWithIn(Assessees filterCriteria) {
        StringBuilder condition = new StringBuilder();
        String finalQuery = "";
        if (filterCriteria != null) {
            Class<?> clazz = filterCriteria.getClass();
            Field[] f = filterCriteria.getClass().getDeclaredFields();
            for (Field fld : f) {
                Field field = org.springframework.util.ReflectionUtils.findField(clazz, fld.getName());
                org.springframework.util.ReflectionUtils.makeAccessible(field);
                try {
                    if (field.get(filterCriteria) != null) {
                        if (Objects.nonNull(field.get(filterCriteria)) && !field.get(filterCriteria).toString().equals(""))
                            if (fld.getType().getTypeName().equals("java.lang.String")) {
                                condition.append(" AND ").append(getColumnName(fld.getName())).append(" IN('").append(field.get(filterCriteria).toString().replace(",", "','")).append("')");
                            } else {
                                condition.append(" AND ").append(getColumnName(fld.getName())).append(" IN(").append(field.get(filterCriteria)).append(") ");
                            }
                    }
                } catch (Exception e) {
                    log.info("Exception >> {}", e.getMessage());
                }
            }

            if (!condition.toString().equals("")) {
                finalQuery = condition.substring(4);
            }

        }
        log.info("And Condition >> {}", finalQuery);
        return finalQuery;
    }

    public static String createAndConditionForFilter(FilterCriteria filterCriteria) {
        StringBuilder condition = new StringBuilder();
        String finalQuery = "";
        if (filterCriteria != null) {
            Class<?> clazz = filterCriteria.getClass();
            Field[] f = filterCriteria.getClass().getDeclaredFields();
            for (Field fld : f) {
                Field field = org.springframework.util.ReflectionUtils.findField(clazz, fld.getName());
                org.springframework.util.ReflectionUtils.makeAccessible(field);
                try {
                    if (field.get(filterCriteria) != null) {
                        if (fld.getType().getTypeName().equals("java.lang.String")) {
                            condition.append(" AND FIND_IN_SET(LOWER(").append(getColumnName(fld.getName())).append("), '").append(field.get(filterCriteria).toString().toLowerCase()).append("')");
                        }else {
                            condition.append(" AND ").append(getColumnName(fld.getName())).append(" = ").append(field.get(filterCriteria));
                        }
                    }
                } catch (Exception e) {
                    log.info("Exception >> {}", e.getMessage());
                }
            }
        }
        log.info("And Condition >> {}", condition);
        return condition.toString();
    }

    public static String createAndConditionWithAlias(ReportFilterCriteria filterCriteria) {
        StringBuilder condition = new StringBuilder();
        String finalQuery = "";
        if (filterCriteria != null) {
            Class<?> clazz = filterCriteria.getClass();
            Field[] f = filterCriteria.getClass().getDeclaredFields();
            for (Field fld : f) {
                Field field = org.springframework.util.ReflectionUtils.findField(clazz, fld.getName());
                org.springframework.util.ReflectionUtils.makeAccessible(field);
                try {
                    if (field.get(filterCriteria) != null) {
                        if (fld.getType().getTypeName().equals("java.lang.String") && !field.get(filterCriteria).toString().equals("")) {
                            condition.append(" AND FIND_IN_SET(").append("temp."+getColumnName(fld.getName())).append(", '").append(field.get(filterCriteria).toString().toLowerCase()).append("')");
                        }else if(!field.get(filterCriteria).toString().equals("")) {
                            condition.append(" AND ").append(getColumnName("temp."+fld.getName())).append(" = ").append(field.get(filterCriteria));
                        }
                    }
                } catch (Exception e) {
                    log.info("Exception >> {}", e.getMessage());
                }
            }
        }
        if (!condition.toString().equals("")) {
            finalQuery = condition.substring(4);
        }
        log.info("And Condition >> {}", condition);
        return finalQuery;
    }


    private static String getColumnName(String str) {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                value.append("_").append(str.charAt(i));
            } else {
                value.append(str.charAt(i));
            }
        }
        return value.toString().toLowerCase();
    }
}
