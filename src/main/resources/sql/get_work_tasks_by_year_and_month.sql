select id
, start_datetime
, description
, requestor
, planned
, project
, category
from work_tasks
where start_datetime between ? and ?