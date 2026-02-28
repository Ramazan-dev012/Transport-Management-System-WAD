# Transport Management System

Веб-приложение для управления транспортной системой с авторизацией пользователей.

## Описание

Transport Management System — это современное веб-приложение на Spring Boot для управления автобусным парком, пассажирами и продажей билетов. Система включает авторизацию пользователей, защищённые страницы и REST API.

## Функционал

### Публичный доступ (без авторизации):
- Главная страница со статистикой
- Просмотр списка автобусов
- Просмотр списка пассажиров
- Просмотр билетов и свободных мест

### Требует авторизации:
- Покупка билетов
- Добавление автобусов
- Добавление пассажиров
- Управление данными

### Авторизация:
- Регистрация новых пользователей
- Вход в систему
- Защита паролей (BCrypt)
- Роли пользователей

## Технологии

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Security** — авторизация и аутентификация
- **Spring Data JPA** — работа с базой данных
- **PostgreSQL** — реляционная база данных
- **Thymeleaf** — шаблонизатор HTML
- **Maven** — сборка проекта
- **HTML/CSS** — современный UI

## Быстрый старт

### Требования:
- Java JDK 21 и выше
- PostgreSQL
- Maven (или используйте встроенный Maven Wrapper)

### Настройка базы данных:

Создайте базу данных PostgreSQL и настройте `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=ваш_пароль
spring.jpa.hibernate.ddl-auto=update
```

### Запуск:

1. **Клонируйте репозиторий:**
```bash
git clone https://github.com/Ramazan-dev012/Transport-Management-System-WAD.git
cd Transport-Management-System-WAD
```

2. **Запустите приложение:**
```bash
mvnw spring-boot:run
```

Или через IDE (IntelliJ IDEA):
- Откройте проект
- Запустите `TransportSystemApplication.java`

3. **Откройте в браузере:**
```
http://localhost:8080/transport
```

## Структура проекта

```
src/main/java/com/example/transportsystem/
├── model/
│   ├── BusEntity.java               # Сущность автобуса
│   ├── PassengerEntity.java         # Сущность пассажира
│   ├── TicketEntity.java            # Сущность билета
│   └── User.java                    # Сущность пользователя
├── repository/
│   ├── BusRepository.java           # Запросы автобусов
│   ├── PassengerRepository.java     # Запросы пассажиров
│   ├── TicketRepository.java        # Запросы билетов
│   └── UserRepository.java          # Запросы пользователей
├── service/
│   ├── TransportManagementService.java  # Основная бизнес-логика
│   ├── AuthService.java                 # Сервис регистрации
│   └── CustomUserDetailsService.java    # Интеграция Spring Security
├── config/
│   └── SecurityConfig.java              # Конфигурация Spring Security
├── controller/
│   ├── HomeController.java              # Редирект на главную
│   ├── AuthController.java              # Страницы входа и регистрации
│   ├── TransportController.java         # MVC контроллер (Thymeleaf)
│   └── TransportRestController.java     # REST API контроллер
└── TransportSystemApplication.java      # Точка входа Spring Boot

src/main/resources/
├── templates/
│   ├── auth/
│   │   ├── login.html               # Страница входа
│   │   └── register.html            # Страница регистрации
│   ├── main.html                    # Главная страница со статистикой
│   ├── buses.html                   # Список автобусов
│   ├── passengers.html              # Список пассажиров
│   ├── viewTickets.html             # Просмотр билетов и свободных мест
│   ├── buyTicket.html               # Покупка билета
│   ├── addBus.html                  # Форма добавления автобуса
│   └── addPassenger.html            # Форма добавления пассажира
├── static/
│   ├── css/style.css                # Стили
│   └── api-tester.html              # Тестер REST API
└── application.properties           # Конфигурация приложения
```

## Модели данных

### BusEntity (таблица buses)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Первичный ключ |
| routeNumber | String | Номер маршрута |
| capacity | int | Общая вместимость |
| driverName | String | Имя водителя |
| currentPassengers | int | Текущее количество пассажиров |

### PassengerEntity (таблица passengers)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Первичный ключ |
| name | String | Полное имя |
| phoneNumber | String | Номер телефона |
| destination | String | Направление |
| hasTicket | boolean | Статус билета |

### TicketEntity (таблица tickets)
| Поле | Тип | Описание |
|------|-----|----------|
| id | Long | Первичный ключ |
| passenger | PassengerEntity | Пассажир (FK) |
| bus | BusEntity | Автобус (FK) |
| seatNumber | int | Номер места |
| purchasedAt | LocalDateTime | Время покупки |

## Доступные страницы

| URL | Описание | Авторизация |
|-----|----------|-------------|
| `/transport` | Главная страница со статистикой | Не требуется |
| `/transport/buses` | Список автобусов | Не требуется |
| `/transport/passengers` | Список пассажиров | Не требуется |
| `/transport/viewTickets` | Просмотр билетов и свободных мест | Не требуется |
| `/transport/buses/add` | Форма добавления автобуса | Требуется |
| `/transport/passengers/add` | Форма добавления пассажира | Требуется |
| `/transport/buyTicket` | Покупка билета | Требуется |
| `/auth/login` | Страница входа | Не требуется |
| `/auth/register` | Страница регистрации | Не требуется |

## REST API

Базовый URL: `/api/transport`

### Автобусы
| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/buses` | Получить все автобусы |
| GET | `/buses/{id}` | Получить автобус по ID |
| GET | `/buses/available` | Автобусы со свободными местами |
| GET | `/buses/full` | Заполненные автобусы |
| POST | `/buses` | Добавить автобус |
| DELETE | `/buses/{id}` | Удалить автобус |

### Пассажиры
| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/passengers` | Получить всех пассажиров |
| GET | `/passengers/{id}` | Получить пассажира по ID |
| GET | `/passengers/withTickets` | Пассажиры с билетами |
| POST | `/passengers` | Добавить пассажира |
| DELETE | `/passengers/{id}` | Удалить пассажира |

### Билеты
| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/tickets` | Получить все билеты |
| POST | `/tickets` | Купить билет |

### Статистика
| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/statistics` | Получить статистику системы |

## Конфигурация

### Порт сервера:
```properties
server.port=8080
```

## Лицензия

Этот проект создан в образовательных целях.

## Автор

**Ramazan**
- GitHub: [@Ramazan-dev012](https://github.com/Ramazan-dev012)
