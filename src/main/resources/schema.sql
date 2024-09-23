create schema if not exists holidays;

set schema holidays;

create table if not exists holidays(
    holiday_month integer,
    holiday_day integer
);