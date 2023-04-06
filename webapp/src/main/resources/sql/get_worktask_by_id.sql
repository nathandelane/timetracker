select id
, start_date_and_time
, description
, who_caused_it
, planned
, action_item
, category_of_work
, project
from work_tasks
where id = :work_task_id