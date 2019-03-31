create table student (
  id integer primary key not null,
  name varchar(255) not null collate nocase,
  enrolled date not null,
  unique(name)
)
