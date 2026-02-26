# Transport Management System

Система управления транспортом - веб-приложение на Spring Boot.

## Описание проекта

Transport Management System - это веб-приложение для управления автобусным парком, пассажирами и продажей билетов. Проект реализован с использованием Spring Boot, Thymeleaf и REST API.

## Функционал

- **Управление автобусами**: добавление, просмотр, удаление автобусов
- **Управление пассажирами**: регистрация пассажиров, просмотр списка
- **Продажа билетов**: покупка билетов для пассажиров на свободные места
- **Просмотр билетов**: список свободных и заполненных автобусов
- **REST API**: полноценный REST-интерфейс для работы с данными

## Технологии

- **Java 21**
- **Spring Boot 3.2**
- **Thymeleaf** - шаблонизатор для HTML страниц
- **Spring Web MVC** - веб-контроллеры
- **Maven** - сборка проекта
- **HTML/CSS** - интерфейс пользователя
- **Jakarta Servlet API** - сервлеты (doGet/doPost)

## Структура проекта

```
src/
├── main/
│   ├── java/com/example/transportsystem/
│   │   ├── Bus.java                          # Класс автобуса
│   │   ├── Passenger.java                    # Класс пассажира
│   │   ├── TransportService.java             # Бизнес-логика (Servlet)
│   │   ├── HelloServlet.java                 # Тестовый сервлет
│   │   ├── TransportSystemApplication.java   # Точка входа Spring Boot
│   │   ├── controller/
│   │   │   ├── TransportController.java      # MVC контроллер (Thymeleaf)
│   │   │   ├── TransportRestController.java  # REST API контроллер
│   │   │   └── HelloController.java          # Тестовый контроллер
│   │   └── service/
│   │       └── TransportManagementService.java # Сервис управления данными
│   ├── resources/
│   │   ├── templates/                        # Thymeleaf HTML шаблоны
│   │   ├── static/                           # Статические файлы (CSS, JS)
│   │   └── application.properties            # Настройки приложения
│   └── webapp/
│       ├── index.jsp                         # JSP страница
│       └── WEB-INF/web.xml                   # Конфигурация сервлетов
```

## Как запустить

### Требования

- JDK 21 или выше
- Maven (или использовать встроенный mvnw)

### Запуск через Maven

```bash
mvnw.cmd spring-boot:run
```

### Запуск через bat-файл

```bash
run-spring-boot.bat
```

После запуска приложение доступно по адресу: `http://localhost:8080`

## REST API

Базовый URL: `http://localhost:8080/api/transport`

| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/transport/buses | Получить все автобусы |
| POST | /api/transport/buses | Создать автобус |
| DELETE | /api/transport/buses/{id} | Удалить автобус |
| GET | /api/transport/buses/available | Доступные автобусы |
| GET | /api/transport/passengers | Получить всех пассажиров |
| POST | /api/transport/passengers | Создать пассажира |
| DELETE | /api/transport/passengers/{id} | Удалить пассажира |
| POST | /api/transport/tickets | Купить билет |
| GET | /api/transport/statistics | Статистика |

## HTTP методы сервлетов (doGet/doPost)

### doGet - отображение данных
- `?action=viewTickets` - просмотр билетов
- `?action=showBuses` - список автобусов
- `?action=showPassengers` - список пассажиров
- `?action=addBus` - форма добавления автобуса
- `?action=addPassenger` - форма добавления пассажира
- `?action=buyTicket` - форма покупки билета

### doPost - обработка действий
- `action=createBus` - создать автобус
- `action=createPassenger` - создать пассажира
- `action=buyTicket` - купить билет
- `action=deleteBus` - удалить автобус
- `action=deletePassenger` - удалить пассажира

## Автор

**Ramazan**
- GitHub: [@Ramazan-dev012](https://github.com/Ramazan-dev012)

## Лицензия

Проект создан в образовательных целях в рамках курса Web Application Development (WAD).
