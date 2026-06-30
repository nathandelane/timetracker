update work_tasks
set end_datetime = datetime('now')
where id = ?