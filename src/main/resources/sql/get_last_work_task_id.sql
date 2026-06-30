select max(id) as max_id
from work_tasks
where start_datetime is not null
and end_datetime is null