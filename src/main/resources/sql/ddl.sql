create table work_tasks (
    id integer not null primary key
    , start_datetime datetime not null default current_timestamp
    , description varchar(1024) not null
    , requestor varchar(80) not null
    , planned varchar(1) not null
    , project varchar(128)
    , category varchar(128) not null
    , check(planned in ('Y','N'))
);