# Веб-сервис цифрового учета книг в библиотеке

Стек технологий: Spring MVC, Spring Data JPA, PostgreSQL

Библиотекари имеют возможность: 
- добавлять новые книги
- регистрировать читателей
- выдавать читателям книги и освобождать книги (после того, как читатель возвращает книгу обратно в библиотеку)

Добавлен следующий функционал:
- возможность показывать список книг на нескольких страницах
- проверки правильности заполнения полей при добавлении/изменении данных
- сортировка книг по году издания
- поиск книг по названию
- проверка срока возврата книги в библиотеку (на странице читателя отдельно обозначаются книги, выписанные ему более 10 дней назад)

## Инструкция по установке

**1. Клонирование репозитория**

```bash
git clone https://github.com/priorgnewb/library.git
```

**2. Создание базы данных PostgreSQL**

```bash
create database library_db
```
- run `sql/library_create_tables.sql`

**3. Настройка подключения к PostgreSQL**

+ открыть `src/main/resources/hibernate.properties`
+ указать для `hibernate.connection.username`, `hibernate.connection.password` и других параметров значения, используемые вами в PostgreSQL

**4. Запуск проекта в IntellijIDEA**

**4.1. Импорт проекта в IntellijIDEA**

`File -> New -> Project -> From existing sources`

и указать путь к `pom.xml`

**4.2. Запуск проекта**

Запустить приложение `Run -> Run -> Edit Configurations -> Add new configuration -> Tomcat Server (Local)`

Deployment -> Add -> Artifact -> library:war exploded -> Ok

Удалить содержимое поля Application Context -> Apply -> Run

**4.3. Тестирование приложения**

В браузере приложение доступно по адресу `http://localhost:8080/books`
