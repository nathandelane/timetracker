CREATE TABLE IF NOT EXISTS work_tasks (
  id integer not null primary key autoincrement,
  start_date_and_time integer not null,
  description text not null,
  who_caused_it text not null,
  planned text not null,
  action_item text,
  category_of_work text not null,
  project text
);