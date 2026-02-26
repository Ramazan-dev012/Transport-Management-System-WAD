# Transport Management System

Веб-приложение для управления транспортной системой с авторизацией пользователей.

## Описание

Transport Management System - это современное веб-приложение на Spring Boot для управления автобусным парком, пассажирами и продажей билетов. Система включает авторизацию пользователей, защищённые страницы и REST API.

## Функционал

### Публичный доступ (без авторизации):
- Просмотр главной страницы со статистикой
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

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - авторизация и аутентификация
- **Spring Data JPA** - работа с базой данных
- **H2 Database** - встроенная база данных
- **Thymeleaf** - шаблонизатор HTML
- **Maven** - сборка проекта
- **HTML/CSS** - современный UI

## Быстрый старт

### Требования:
- Java JDK 17 или выше
- Maven (или используйте встроенный Maven Wrapper)

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
│   └── User.java                           # Модель пользователя (JPA Entity)
├── repository/
│   └── UserRepository.java                 # JPA Repository для пользователей
├── service/
│   ├── TransportManagementService.java     # Бизнес-логика транспорта
│   ├── AuthService.java                    # Сервис регистрации
│   └── CustomUserDetailsService.java       # Spring Security UserDetails
├── config/
│   └── SecurityConfig.java                 # Конфигурация Spring Security
├── controller/
│   ├── HomeController.java                 # Главный контроллер
│   ├── AuthController.java                 # Контроллер авторизации
│   ├── TransportController.java            # MVC контроллер (Thymeleaf)
│   └── TransportRestController.java        # REST API контроллер
├── Bus.java                                # Модель автобуса
├── Passenger.java                          # Модель пассажира
└── TransportSystemApplication.java         # Main класс Spring Boot

src/main/resources/
├── templates/
│   ├── auth/
│   │   ├── login.html                      # Страница входа
│   │   └── register.html                   # Страница регистрации
│   ├── main.html                           # Главная страница
│   ├── buses.html                          # Список автобусов
│   ├── passengers.html                     # Список пассажиров
│   ├── viewTickets.html                    # Просмотр билетов
│   ├── buyTicket.html                      # Покупка билета
│   ├── addBus.html                         # Добавление автобуса
│   └── addPassenger.html                   # Добавление пассажира
├── static/
│   ├── css/style.css                       # Стили
│   └── api-tester.html                     # Тестер REST API
└── application.properties                  # Конфигурация приложения
```

## Авторизация

### Регистрация:
1. Перейдите на `/auth/register`
2. Заполните форму (логин, email, пароль)
3. Нажмите "Создать аккаунт"

### Вход:
1. Перейдите на `/auth/login`
2. Введите логин и пароль
3. Нажмите "Войти"

### Тестовые данные:
При первом запуске в базе данных автоматически создаются:
- 3 тестовых автобуса
- 2 тестовых пассажира

## Доступные URL

| URL | Описание |
|-----|----------|
| `/` | Редирект на главную страницу |
| `/transport` | Главная страница со статистикой |
| `/auth/login` | Вход в систему |
| `/auth/register` | Регистрация |
| `/transport/buses` | Список автобусов |
| `/transport/buses/add` | Добавить автобус (требует авторизации) |
| `/transport/passengers` | Список пассажиров |
| `/transport/passengers/add` | Добавить пассажира (требует авторизации) |
| `/transport/viewTickets` | Просмотр билетов |
| `/transport/buyTicket` | Купить билет (требует авторизации) |
| `/h2-console` | H2 Console (БД) |
| `/api/transport/**` | REST API endpoints |

## Конфигурация

### База данных (H2):
```properties
spring.datasource.url=jdbc:h2:mem:transportdb
spring.datasource.username=sa
spring.datasource.password=
```

### Порт сервера:
```properties
server.port=8080
```

Для изменения порта отредактируйте `application.properties`.

## REST API

### Автобусы:
- `GET /api/transport/buses` - список всех автобусов
- `POST /api/transport/buses` - добавить автобус
- `DELETE /api/transport/buses/{id}` - удалить автобус

### Пассажиры:
- `GET /api/transport/passengers` - список всех пассажиров
- `POST /api/transport/passengers` - добавить пассажира
- `DELETE /api/transport/passengers/{id}` - удалить пассажира

### Билеты:
- `POST /api/transport/tickets/buy` - купить билет

## Скриншоты

### Главная страница
Отображает статистику системы: количество автобусов, пассажиров, общую вместимость.

### Страница авторизации
Современный дизайн с градиентным фоном и стеклянным эффектом.

### Управление автобусами
Просмотр, добавление и удаление автобусов с отображением загруженности.

## Лицензия

Этот проект создан в образовательных целях.

## Автор

**Ramazan**
- GitHub: [@Ramazan-dev012](https://github.com/Ramazan-dev012)

## 📧 Контакты

Если у вас есть вопросы или предложения, создайте Issue в репозитории.

---

⭐ Если проект был полезен, поставьте звезду!

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
