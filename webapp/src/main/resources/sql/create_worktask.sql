insert into work_tasks (
  start_date_and_time
  , description
  , who_caused_it
  , planned
  , action_item
  , category_of_work
  , project
)
values (
  :start_date_and_time
  , ':description'
  , ':who_caused_it'
  , ':planned'
  , :action_item
  , ':category_of_work'
  , :project
)