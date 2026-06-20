select id
, start_datetime
, description
, requestor
, planned
, project
, category
, end_datetime
from work_tasks
where start_datetime between ? and ?