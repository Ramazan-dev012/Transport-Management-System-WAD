# Transport Management System - WAD

🚌 Система управления транспортом - веб-приложение на Jakarta EE

## 📋 Описание проекта

Transport Management System - это веб-приложение для управления автобусным парком, пассажирами и продажей билетов. Проект реализован с использованием Jakarta Servlets (doGet/doPost методы).

## ✨ Функционал

- 🚌 **Управление автобусами**: добавление, просмотр, удаление автобусов
- 👥 **Управление пассажирами**: регистрация пассажиров, просмотр списка
- 🎫 **Продажа билетов**: покупка билетов для пассажиров на свободные места
- 📊 **Статистика**: отображение данных о загруженности автобусов
- 🎟️ **Просмотр билетов**: список свободных и заполненных автобусов

## 🛠️ Технологии

- **Java 11**
- **Jakarta Servlet API 6.0**
- **Maven** - сборка проекта
- **HTML/CSS** - интерфейс пользователя
- **JSP** - главная страница

## 📦 Структура проекта

```
src/
├── main/
│   ├── java/com/example/transportsystem/
│   │   ├── Bus.java              # Класс автобуса
│   │   ├── Passenger.java        # Класс пассажира
│   │   ├── TransportService.java # Главный сервлет (doGet/doPost)
│   │   └── HelloServlet.java     # Тестовый сервлет
│   └── webapp/
│       ├── index.jsp             # Главная страница
│       └── WEB-INF/
│           └── web.xml           # Конфигурация веб-приложения
```

## 🚀 Как запустить

### Требования
- JDK 11 или выше
- Apache Tomcat 10+ или другой Jakarta EE сервер
- Maven

### Сборка проекта

```bash
# Windows
mvnw.cmd clean package

# Linux/Mac
./mvnw clean package
```

### Запуск
1. Соберите проект командой выше
2. Скопируйте файл `target/TransportSystem-1.0-SNAPSHOT.war` в папку `webapps` вашего Tomcat
3. Запустите Tomcat
4. Откройте в браузере: `http://localhost:8080/TransportSystem-1.0-SNAPSHOT/`

## 📝 HTTP методы (doGet/doPost)

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

## 🎨 Интерфейс

Современный адаптивный дизайн с использованием:
- Градиентные цвета
- Карточки (cards)
- Адаптивная сетка (grid)
- Интерактивные кнопки с эффектами hover
- Статистические карточки

## 👨‍💻 Автор

**Ramazan**
- GitHub: [@Ramazan-dev012](https://github.com/Ramazan-dev012)

## 📄 Лицензия

Этот проект создан в образовательных целях.

---

⭐ Если проект был полезен, поставьте звезду на GitHub!

