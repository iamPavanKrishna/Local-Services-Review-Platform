package com.localservicesreview.servicemanagementservice.repositories;

public interface CustomQueries {

    String FIND_ALL_BY_CATEGORY_NAME = "select id from service where id in (select service_id from service_category where category_id in (select id from category where name = :name))";
    String FIND_ALL_PERIODS_BY_SERVICE_ID = "select p.id from period p left join service_period sp where sp.period_id = p.id";

}
