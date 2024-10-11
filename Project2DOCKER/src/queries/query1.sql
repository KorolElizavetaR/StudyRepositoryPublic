CREATE TABLE person
(
	person_id int GENERATED ALWAYS AS IDENTITY (START WITH 10000 INCREMENT BY 1) NOT NULL PRIMARY KEY,
	full_name varchar UNIQUE NOT NULL,
	birth_date DATE DEFAULT '1970.01.01' NOT NULL CHECK (birth_date BETWEEN '1920.01.01' AND CURRENT_DATE)
);
SELECT * FROM person;
DROP TABLE person;
TRUNCATE person;

CREATE TABLE book
(
	book_id int GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL,
	book_owner int REFERENCES person(person_id) ON DELETE SET NULL,
	book_name varchar NOT NULL,
	book_author varchar NOT NULL,
	taken_at TIMESTAMP
);
SELECT * FROM book;
DROP TABLE book;
TRUNCATE book;

INSERT INTO person (full_name, birth_date) VALUES ('Виконт Алеся Степановна', '2004.01.14');
INSERT INTO person (full_name, birth_date) VALUES ('Игнатовец Артем Александрович', '1965.11.01');
INSERT INTO person (full_name, birth_date) VALUES ('Фролова Татьяна Александровна', '2001.12.21');
INSERT INTO person (full_name, birth_date) VALUES ('Бисмут Ксения Евгеньевна', '1999.01.13');
INSERT INTO person (full_name, birth_date) VALUES ('Ишакович Иван Васильевич', '1989.05.07');

INSERT INTO book (book_name, book_author) VALUES ('Пикни́к на обо́чине', 'Аркадий и Борис Стругацкие');
INSERT INTO book (book_name, book_author) VALUES ('Понеде́льник начина́ется в суббо́ту', 'Аркадий и Борис Стругацкие');
INSERT INTO book (book_name, book_author) VALUES ('Мы', 'Евгений Замятин');
INSERT INTO book (book_name, book_author) VALUES ('Над пропастью во ржи', 'Джером Сэлинджер');
INSERT INTO book (book_name, book_author) VALUES ('Преступле́ние и наказа́ние', 'Фёдор Достоевский');
INSERT INTO book (book_name, book_author) VALUES ('Дьяволиада', 'Михаил Булгаков');

INSERT INTO book (book_owner, book_name, book_author, taken_at) VALUES (10001, 'Множественные умы Билли Миллигана', 'Дэниел Киз', '2024-07-30 10:24:45');
INSERT INTO book (book_owner, book_name, book_author, taken_at) VALUES (10004, 'Цветы для Э́лджернона', 'Дэниел Киз', '2024-08-28 11:40:30');
INSERT INTO book (book_owner, book_name, book_author, taken_at) VALUES	(10004, 'Улитка на склоне', 'Аркадий и Борис Стругацкие', '2024-08-13 19:56:35');
INSERT INTO book (book_owner, book_name, book_author, taken_at) VALUES	(10001, 'Бесы', 'Фёдор Достоевский', '2024-05-06 10:24:05');
INSERT INTO book (book_owner, book_name, book_author, taken_at) VALUES	(10000, 'Театра́льный рома́н', 'Михаил Булгаков', '2024-08-20 12:06:45');
	
	