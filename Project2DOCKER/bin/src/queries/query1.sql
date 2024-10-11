CREATE TABLE person
(
	person_id int GENERATED ALWAYS AS IDENTITY (START WITH 10000 INCREMENT BY 1) NOT NULL PRIMARY KEY,
	full_name varchar UNIQUE NOT NULL,
	birth_year DATE DEFAULT '1970.01.01' NOT NULL CHECK (birth_year BETWEEN '1920.01.01' AND CURRENT_DATE)
);
SELECT * FROM person;

CREATE TABLE book
(
	book_id int GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL,
	book_owner int,
	book_name varchar NOT NULL,
	book_author varchar NOT NULL,
	CONSTRAINT FK_book_owner FOREIGN KEY (book_owner) REFERENCES person(person_id)
);
SELECT * FROM book;

INSERT INTO person (full_name, birth_year) 
	VALUES ('Виконт Алеся Степановна', '2004.01.14'),
			('Игнатовец Артем Александрович', '1999.12.21'),
			('Фролова Татьяна Александровна', '1999.12.21'),
			('Бисмут Ксения Евгеньевна', '1999.12.21'),
			('Ишакович Иван Васильевич', '1989.05.07');

INSERT INTO book (book_name, book_author) VALUES
	('Пикни́к на обо́чине', 'Аркадий и Борис Стругацкие'),
	('Понеде́льник начина́ется в суббо́ту', 'Аркадий и Борис Стругацкие'),
	('Мы', 'Евгений Замятин'),	
	('Над пропастью во ржи', 'Джером Сэлинджер'),
	('Преступле́ние и наказа́ние', 'Фёдор Достоевский'),
	('Дьяволиада', 'Михаил Булгаков');

INSERT INTO book (book_owner, book_name, book_author) VALUES
	(10001, 'Множественные умы Билли Миллигана', 'Дэниел Киз'),
	(10003, 'Цветы для Э́лджернона', 'Дэниел Киз'),
	(10003, 'Улитка на склоне', 'Аркадий и Борис Стругацкие'),
	(10001, 'Бесы', 'Фёдор Достоевский'),
	(10000, 'Театра́льный рома́н', 'Михаил Булгаков');
	
	