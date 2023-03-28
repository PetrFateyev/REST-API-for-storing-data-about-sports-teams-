# REST-API-for-storing-data-about-sports-teams-
Для запуска потребуются: Java 8, PostgreSQL и пользователь с соответствующими правами.     

Сборка проекта: Maven  

Сборка и запуск на Windows:  
```
mvn spring-boot:run
```  

Настройки по умолчанию:  
```
порт сервера: 5000  
хост БД:      localhost   
порт БД:      5432  
название БД:  postgres 
пользователь: postgres  
пароль:       123   
```
Можно изменить в файле src/main/resources/application.properties.

Таблицы в базе данных будут сформированы во время сборки. 
