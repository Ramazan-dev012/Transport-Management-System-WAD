package com.example.transportsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "transportService", value = "/transport-service")
public class TransportService extends HttpServlet {

    // ─────────────────────────────────────────────────────────────────────────
    // Получение данных сессии (создаётся один раз на каждого пользователя)
    // ─────────────────────────────────────────────────────────────────────────

    private SessionData getSessionData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionData data = (SessionData) session.getAttribute("sessionData");
        if (data == null) {
            data = new SessionData();
            session.setAttribute("sessionData", data);
            System.out.println(">>> [SESSION] Создана новая сессия: " + session.getId());
        } else {
            System.out.println(">>> [SESSION] Сессия существует: " + session.getId()
                    + " | автобусов: " + data.getBuses().size()
                    + " | пассажиров: " + data.getPassengers().size());
        }
        return data;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // doGet
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        // Сброс сессии
        if ("resetSession".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("transport-service");
            return;
        }

        SessionData data = getSessionData(request);

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Transport Management System</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 1200px; margin: 0 auto; }");
        out.println("h1 { color: white; text-align: center; margin-bottom: 30px; font-size: 2.5em; text-shadow: 2px 2px 4px rgba(0,0,0,0.2); }");
        out.println("h2 { color: #333; margin: 20px 0; font-size: 1.8em; }");
        out.println(".nav { background: white; padding: 15px 20px; border-radius: 10px; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); display: flex; justify-content: center; flex-wrap: wrap; gap: 10px; }");
        out.println(".nav a { text-decoration: none; color: #667eea; font-weight: 600; padding: 10px 20px; border-radius: 5px; transition: all 0.3s; }");
        out.println(".nav a:hover { background: #667eea; color: white; transform: translateY(-2px); }");
        out.println(".nav a.reset { color: #f44336; }");
        out.println(".nav a.reset:hover { background: #f44336; color: white; }");
        out.println(".nav a.debug { color: #FF9800; }");
        out.println(".nav a.debug:hover { background: #FF9800; color: white; }");
        out.println(".card { background: white; border-radius: 15px; padding: 30px; margin: 20px 0; box-shadow: 0 8px 16px rgba(0,0,0,0.1); }");
        out.println(".stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin: 20px 0; }");
        out.println(".stat-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 25px; border-radius: 12px; text-align: center; box-shadow: 0 4px 8px rgba(0,0,0,0.2); }");
        out.println(".stat-card h3 { font-size: 2.5em; margin: 10px 0; }");
        out.println(".stat-card p { opacity: 0.9; font-size: 1.1em; }");
        out.println("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        out.println("th { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 15px; text-align: left; font-weight: 600; }");
        out.println("td { padding: 12px 15px; border-bottom: 1px solid #f0f0f0; }");
        out.println("tr:hover { background-color: #f8f9ff; }");
        out.println(".badge { display: inline-block; padding: 5px 12px; border-radius: 20px; font-size: 0.85em; font-weight: 600; }");
        out.println(".badge-success { background: #4CAF50; color: white; }");
        out.println(".badge-warning { background: #FF9800; color: white; }");
        out.println(".badge-danger { background: #f44336; color: white; }");
        out.println(".badge-info { background: #2196F3; color: white; }");
        out.println(".badge-purple { background: #9C27B0; color: white; }");
        out.println("button { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-weight: 600; transition: all 0.3s; margin: 5px; }");
        out.println("button:hover { transform: translateY(-2px); box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4); }");
        out.println("button.secondary { background: #6c757d; }");
        out.println("button.danger { background: #f44336; }");
        out.println("button.success { background: #4CAF50; }");
        out.println("button.info { background: #2196F3; }");
        out.println(".form-container { background: #f8f9ff; padding: 25px; border-radius: 10px; margin: 20px 0; }");
        out.println("label { display: block; margin: 15px 0 5px; color: #333; font-weight: 600; }");
        out.println("input, select { width: 100%; padding: 12px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 1em; transition: border 0.3s; }");
        out.println("input:focus, select:focus { outline: none; border-color: #667eea; }");
        out.println(".ticket-card { background: white; border-left: 4px solid #667eea; padding: 20px; margin: 15px 0; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".ticket-card:hover { box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2); }");
        out.println(".bus-available { border-left-color: #4CAF50; }");
        out.println(".bus-full { border-left-color: #f44336; }");
        out.println(".grid-2 { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; }");
        out.println(".empty-state { text-align: center; padding: 60px 20px; color: #666; }");
        out.println(".empty-state h3 { font-size: 1.5em; margin: 20px 0; }");
        out.println(".session-info { background: #fff3e0; border: 2px solid #FF9800; border-radius: 8px; padding: 10px 20px; font-size: 0.85em; color: #e65100; text-align: center; margin-bottom: 10px; }");
        out.println(".debug-table td { font-family: monospace; font-size: 0.9em; }");
        out.println(".debug-table td:first-child { font-weight: bold; color: #667eea; width: 220px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Transport Management System</h1>");

        // Строка с ID сессии вверху страницы
        HttpSession session = request.getSession(false);
        if (session != null) {
            String shortId = session.getId().substring(0, 8) + "...";
            out.println("<div class='container'>");
            out.println("<div class='session-info'>Session ID: <strong>" + shortId + "</strong> &nbsp;|&nbsp; Автобусов: <strong>" + data.getBuses().size() + "</strong> &nbsp;|&nbsp; Пассажиров: <strong>" + data.getPassengers().size() + "</strong></div>");
        } else {
            out.println("<div class='container'>");
        }

        out.println("<div class='nav'>");
        out.println("<a href='transport-service'>Главная</a>");
        out.println("<a href='transport-service?action=viewTickets'>Просмотр билетов</a>");
        out.println("<a href='transport-service?action=showBuses'>Автобусы</a>");
        out.println("<a href='transport-service?action=showPassengers'>Пассажиры</a>");
        out.println("<a href='transport-service?action=sessionDebug' class='debug'>Сессия (debug)</a>");
        out.println("<a href='transport-service?action=resetSession' class='reset' onclick=\"return confirm('Сбросить сессию? Все данные будут удалены!')\">Сбросить сессию</a>");
        out.println("<a href='index.jsp'>Назад</a>");
        out.println("</div>");

        if (action == null || action.isEmpty()) {
            displayMainPage(out, data);
        } else if (action.equals("viewTickets")) {
            displayViewTickets(out, data);
        } else if (action.equals("showBuses")) {
            displayBuses(out, data);
        } else if (action.equals("showPassengers")) {
            displayPassengers(out, data);
        } else if (action.equals("addBus")) {
            displayAddBusForm(out);
        } else if (action.equals("addPassenger")) {
            displayAddPassengerForm(out);
        } else if (action.equals("buyTicket")) {
            displayBuyTicketForm(out, data);
        } else if (action.equals("sessionDebug")) {
            displaySessionDebug(out, request);
        }

        out.println("</div>"); // Close container
        out.println("</body>");
        out.println("</html>");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // doPost
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "createBus":
                    createBus(request, response);
                    break;
                case "createPassenger":
                    createPassenger(request, response);
                    break;
                case "boardPassenger":
                    boardPassenger(request, response);
                    break;
                case "buyTicket":
                    buyTicket(request, response);
                    break;
                case "deleteBus":
                    deleteBus(request, response);
                    break;
                case "deletePassenger":
                    deletePassenger(request, response);
                    break;
                default:
                    response.sendRedirect("transport-service");
                    break;
            }
        } else {
            response.sendRedirect("transport-service");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Страницы отображения
    // ─────────────────────────────────────────────────────────────────────────

    private void displayMainPage(PrintWriter out, SessionData data) {
        List<Bus> buses = data.getBuses();
        List<Passenger> passengers = data.getPassengers();

        int totalCapacity = buses.stream().mapToInt(Bus::getCapacity).sum();
        long passengersWithTickets = passengers.stream().filter(Passenger::isHasTicket).count();

        out.println("<div class='card'>");
        out.println("<h2>Статистика системы</h2>");
        out.println("<div class='stats-grid'>");

        out.println("<div class='stat-card'>");
        out.println("<p>Всего автобусов</p>");
        out.println("<h3>" + buses.size() + "</h3>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<p>Всего пассажиров</p>");
        out.println("<h3>" + passengers.size() + "</h3>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<p>Общая вместимость</p>");
        out.println("<h3>" + totalCapacity + "</h3>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<p>Пассажиров с билетами</p>");
        out.println("<h3>" + passengersWithTickets + "</h3>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h2>Быстрые действия</h2>");
        out.println("<div style='display: flex; gap: 10px; flex-wrap: wrap;'>");
        out.println("<button class='info' onclick=\"location.href='transport-service?action=buyTicket'\">Купить билет</button>");
        out.println("<button class='success' onclick=\"location.href='transport-service?action=addBus'\">Добавить автобус</button>");
        out.println("<button class='success' onclick=\"location.href='transport-service?action=addPassenger'\">Добавить пассажира</button>");
        out.println("<button onclick=\"location.href='transport-service?action=viewTickets'\">Просмотр билетов</button>");
        out.println("</div>");
        out.println("</div>");
    }

    private void displaySessionDebug(PrintWriter out, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        out.println("<div class='card'>");
        out.println("<h2>Информация о сессии (debug)</h2>");

        if (session == null) {
            out.println("<p>Сессия не найдена.</p>");
            out.println("</div>");
            return;
        }

        out.println("<table class='debug-table'>");
        out.println("<tr><th>Параметр</th><th>Значение</th></tr>");
        out.println("<tr><td>Session ID</td><td>" + session.getId() + "</td></tr>");
        out.println("<tr><td>Новая сессия?</td><td><span class='badge " + (session.isNew() ? "badge-success'>Да" : "badge-warning'>Нет") + "</span></td></tr>");
        out.println("<tr><td>Создана</td><td>" + new Date(session.getCreationTime()) + "</td></tr>");
        out.println("<tr><td>Последний доступ</td><td>" + new Date(session.getLastAccessedTime()) + "</td></tr>");
        out.println("<tr><td>Таймаут (сек)</td><td>" + session.getMaxInactiveInterval() + " сек (" + session.getMaxInactiveInterval() / 60 + " мин)</td></tr>");

        SessionData data = (SessionData) session.getAttribute("sessionData");
        if (data != null) {
            out.println("<tr><td>Сессия создана в</td><td>" + new Date(data.getCreatedAt()) + "</td></tr>");
            out.println("<tr><td>Автобусов в сессии</td><td><span class='badge badge-info'>" + data.getBuses().size() + "</span></td></tr>");
            out.println("<tr><td>Пассажиров в сессии</td><td><span class='badge badge-info'>" + data.getPassengers().size() + "</span></td></tr>");
        }

        // Все атрибуты сессии
        out.println("<tr><td colspan='2' style='background:#f8f9ff; font-weight:bold; color:#667eea;'>Атрибуты сессии</td></tr>");
        Enumeration<String> attrs = session.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            Object val = session.getAttribute(attr);
            String display = (val instanceof SessionData) ? "[SessionData объект]" : String.valueOf(val);
            out.println("<tr><td>" + attr + "</td><td>" + display + "</td></tr>");
        }

        out.println("</table>");

        // Инструкция по тестированию
        out.println("<div style='margin-top:20px; background:#e8f5e9; border-radius:8px; padding:20px;'>");
        out.println("<h3 style='color:#2e7d32; margin-bottom:10px;'>Как проверить изоляцию сессий</h3>");
        out.println("<ol style='line-height: 2em; padding-left: 20px; color:#333;'>");
        out.println("<li>Открой этот сайт в <strong>Chrome</strong> — запомни Session ID выше</li>");
        out.println("<li>Открой этот же URL в <strong>Firefox</strong> (или режим инкогнито)</li>");
        out.println("<li>Session ID будет <strong>другим</strong> — это разные сессии</li>");
        out.println("<li>Добавь автобус в Chrome — в Firefox его не будет</li>");
        out.println("<li>Нажми <strong>\"Сбросить сессию\"</strong> — все данные очистятся и создадутся заново</li>");
        out.println("</ol>");
        out.println("</div>");

        out.println("<div style='margin-top:15px;'>");
        out.println("<button class='danger' onclick=\"if(confirm('Сбросить сессию?')) location.href='transport-service?action=resetSession'\">Сбросить сессию</button>");
        out.println("<button onclick=\"location.href='transport-service?action=sessionDebug'\">Обновить</button>");
        out.println("</div>");

        out.println("</div>");
    }

    private void displayViewTickets(PrintWriter out, SessionData data) {
        List<Bus> buses = data.getBuses();
        List<Passenger> passengers = data.getPassengers();

        out.println("<div class='card'>");
        out.println("<h2>Просмотр билетов и свободных автобусов</h2>");

        long availableBuses = buses.stream().filter(b -> !b.isFull()).count();
        long fullBuses = buses.stream().filter(Bus::isFull).count();
        long passengersWithTickets = passengers.stream().filter(Passenger::isHasTicket).count();

        out.println("<div class='stats-grid'>");
        out.println("<div class='stat-card' style='background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);'>");
        out.println("<p>Свободные автобусы</p>");
        out.println("<h3>" + availableBuses + "</h3>");
        out.println("</div>");

        out.println("<div class='stat-card' style='background: linear-gradient(135deg, #f44336 0%, #e91e63 100%);'>");
        out.println("<p>Заполненные автобусы</p>");
        out.println("<h3>" + fullBuses + "</h3>");
        out.println("</div>");

        out.println("<div class='stat-card' style='background: linear-gradient(135deg, #2196F3 0%, #03A9F4 100%);'>");
        out.println("<p>Куплено билетов</p>");
        out.println("<h3>" + passengersWithTickets + "</h3>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        // Свободные автобусы
        out.println("<div class='card'>");
        out.println("<h2>Свободные автобусы</h2>");

        List<Bus> availableBusesList = buses.stream()
                .filter(b -> !b.isFull())
                .collect(java.util.stream.Collectors.toList());

        if (availableBusesList.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<h3>Нет свободных автобусов</h3>");
            out.println("<p>Все автобусы заполнены или отсутствуют в системе</p>");
            out.println("<button onclick=\"location.href='transport-service?action=addBus'\">Добавить автобус</button>");
            out.println("</div>");
        } else {
            out.println("<div class='grid-2'>");
            for (Bus bus : availableBusesList) {
                int freeSeats = bus.getCapacity() - bus.getCurrentPassengers();
                double fillPercent = (bus.getCurrentPassengers() * 100.0) / bus.getCapacity();

                out.println("<div class='ticket-card bus-available'>");
                out.println("<h3>Маршрут " + bus.getRouteNumber() + "</h3>");
                out.println("<p><strong>Водитель:</strong> " + bus.getDriverName() + "</p>");
                out.println("<p><strong>Свободных мест:</strong> <span class='badge badge-success'>" + freeSeats + " из " + bus.getCapacity() + "</span></p>");
                out.println("<p><strong>Загруженность:</strong> " + String.format("%.1f", fillPercent) + "%</p>");
                out.println("<div style='margin-top: 10px;'>");
                out.println("<button class='info' onclick=\"location.href='transport-service?action=buyTicket'\">Купить билет</button>");
                out.println("</div>");
                out.println("</div>");
            }
            out.println("</div>");
        }
        out.println("</div>");

        // Купленные билеты
        out.println("<div class='card'>");
        out.println("<h2>Купленные билеты</h2>");

        List<Passenger> passengersWithTicketsList = passengers.stream()
                .filter(Passenger::isHasTicket)
                .collect(java.util.stream.Collectors.toList());

        if (passengersWithTicketsList.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<h3>Нет купленных билетов</h3>");
            out.println("<p>Ни один пассажир еще не купил билет</p>");
            out.println("<button class='info' onclick=\"location.href='transport-service?action=buyTicket'\">Купить билет</button>");
            out.println("</div>");
        } else {
            out.println("<table>");
            out.println("<tr><th>Пассажир</th><th>Телефон</th><th>Пункт назначения</th><th>Статус билета</th></tr>");
            for (Passenger p : passengersWithTicketsList) {
                out.println("<tr>");
                out.println("<td><strong>" + p.getName() + "</strong></td>");
                out.println("<td>" + p.getPhoneNumber() + "</td>");
                out.println("<td>" + p.getDestination() + "</td>");
                out.println("<td><span class='badge badge-success'>Оплачен</span></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
        out.println("</div>");

        // Заполненные автобусы
        List<Bus> fullBusesList = buses.stream()
                .filter(Bus::isFull)
                .collect(java.util.stream.Collectors.toList());

        if (!fullBusesList.isEmpty()) {
            out.println("<div class='card'>");
            out.println("<h2>Заполненные автобусы</h2>");
            out.println("<div class='grid-2'>");
            for (Bus bus : fullBusesList) {
                out.println("<div class='ticket-card bus-full'>");
                out.println("<h3>Маршрут " + bus.getRouteNumber() + "</h3>");
                out.println("<p><strong>Водитель:</strong> " + bus.getDriverName() + "</p>");
                out.println("<p><strong>Статус:</strong> <span class='badge badge-danger'>ЗАПОЛНЕН</span></p>");
                out.println("<p><strong>Пассажиров:</strong> " + bus.getCurrentPassengers() + " / " + bus.getCapacity() + "</p>");
                out.println("</div>");
            }
            out.println("</div>");
            out.println("</div>");
        }
    }

    private void displayBuses(PrintWriter out, SessionData data) {
        List<Bus> buses = data.getBuses();

        out.println("<div class='card'>");
        out.println("<h2>Список автобусов</h2>");
        out.println("<button class='success' onclick=\"location.href='transport-service?action=addBus'\">Добавить автобус</button>");

        if (buses.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<h3>Нет зарегистрированных автобусов</h3>");
            out.println("</div>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Маршрут</th><th>Вместимость</th><th>Водитель</th><th>Пассажиров</th><th>Статус</th><th>Действия</th></tr>");

            for (Bus bus : buses) {
                out.println("<tr>");
                out.println("<td><strong>#" + bus.getId() + "</strong></td>");
                out.println("<td>Маршрут " + bus.getRouteNumber() + "</td>");
                out.println("<td>" + bus.getCapacity() + " мест</td>");
                out.println("<td>" + bus.getDriverName() + "</td>");
                out.println("<td>" + bus.getCurrentPassengers() + " / " + bus.getCapacity() + "</td>");
                String statusBadge = bus.isFull()
                        ? "<span class='badge badge-danger'>Заполнен</span>"
                        : "<span class='badge badge-success'>Места есть</span>";
                out.println("<td>" + statusBadge + "</td>");
                out.println("<td>");
                out.println("<form method='post' action='transport-service' style='display:inline;'>");
                out.println("<input type='hidden' name='action' value='deleteBus'/>");
                out.println("<input type='hidden' name='busId' value='" + bus.getId() + "'/>");
                out.println("<button class='danger' type='submit' onclick=\"return confirm('Удалить автобус?');\">Удалить</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
        out.println("</div>");
    }

    private void displayPassengers(PrintWriter out, SessionData data) {
        List<Passenger> passengers = data.getPassengers();

        out.println("<div class='card'>");
        out.println("<h2>Список пассажиров</h2>");
        out.println("<button class='success' onclick=\"location.href='transport-service?action=addPassenger'\">Добавить пассажира</button>");

        if (passengers.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<h3>Нет зарегистрированных пассажиров</h3>");
            out.println("</div>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Имя</th><th>Телефон</th><th>Пункт назначения</th><th>Билет</th><th>Действия</th></tr>");

            for (Passenger passenger : passengers) {
                out.println("<tr>");
                out.println("<td><strong>#" + passenger.getId() + "</strong></td>");
                out.println("<td>" + passenger.getName() + "</td>");
                out.println("<td>" + passenger.getPhoneNumber() + "</td>");
                out.println("<td>" + passenger.getDestination() + "</td>");
                String ticketBadge = passenger.isHasTicket()
                        ? "<span class='badge badge-success'>Куплен</span>"
                        : "<span class='badge badge-warning'>Нет</span>";
                out.println("<td>" + ticketBadge + "</td>");
                out.println("<td>");
                out.println("<form method='post' action='transport-service' style='display:inline;'>");
                out.println("<input type='hidden' name='action' value='deletePassenger'/>");
                out.println("<input type='hidden' name='passengerId' value='" + passenger.getId() + "'/>");
                out.println("<button class='danger' type='submit' onclick=\"return confirm('Удалить пассажира?');\">Удалить</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
        out.println("</div>");
    }

    private void displayAddBusForm(PrintWriter out) {
        out.println("<div class='card'>");
        out.println("<h2>Добавить новый автобус</h2>");
        out.println("<div class='form-container'>");
        out.println("<form method='post' action='transport-service'>");
        out.println("<input type='hidden' name='action' value='createBus'/>");

        out.println("<label>Номер маршрута:</label>");
        out.println("<input type='text' name='routeNumber' placeholder='Например: 101' required/>");

        out.println("<label>Вместимость:</label>");
        out.println("<input type='number' name='capacity' placeholder='Количество мест' min='1' required/>");

        out.println("<label>Имя водителя:</label>");
        out.println("<input type='text' name='driverName' placeholder='Например: Иван Иванов' required/>");

        out.println("<div style='margin-top: 20px;'>");
        out.println("<button class='success' type='submit'>Создать автобус</button>");
        out.println("<button class='secondary' type='button' onclick=\"location.href='transport-service?action=showBuses'\">Отмена</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
    }

    private void displayAddPassengerForm(PrintWriter out) {
        out.println("<div class='card'>");
        out.println("<h2>Добавить нового пассажира</h2>");
        out.println("<div class='form-container'>");
        out.println("<form method='post' action='transport-service'>");
        out.println("<input type='hidden' name='action' value='createPassenger'/>");

        out.println("<label>Имя:</label>");
        out.println("<input type='text' name='name' placeholder='Например: Иван Петров' required/>");

        out.println("<label>Телефон:</label>");
        out.println("<input type='tel' name='phoneNumber' placeholder='+7 (XXX) XXX-XX-XX' required/>");

        out.println("<label>Пункт назначения:</label>");
        out.println("<input type='text' name='destination' placeholder='Например: Аэропорт' required/>");

        out.println("<div style='margin-top: 20px;'>");
        out.println("<button class='success' type='submit'>Создать пассажира</button>");
        out.println("<button class='secondary' type='button' onclick=\"location.href='transport-service?action=showPassengers'\">Отмена</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
    }

    private void displayBuyTicketForm(PrintWriter out, SessionData data) {
        List<Bus> buses = data.getBuses();
        List<Passenger> passengers = data.getPassengers();

        out.println("<div class='card'>");
        out.println("<h2>Купить билет на автобус</h2>");

        if (passengers.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<h3>Нет зарегистрированных пассажиров</h3>");
            out.println("<p>Сначала добавьте пассажира</p>");
            out.println("<button class='success' onclick=\"location.href='transport-service?action=addPassenger'\">Добавить пассажира</button>");
            out.println("</div>");
            out.println("</div>");
            return;
        }

        if (buses.isEmpty()) {
            out.println("<div class='empty-state'>");
            out.println("<h3>Нет доступных автобусов</h3>");
            out.println("<p>Сначала добавьте автобус</p>");
            out.println("<button class='success' onclick=\"location.href='transport-service?action=addBus'\">Добавить автобус</button>");
            out.println("</div>");
            out.println("</div>");
            return;
        }

        out.println("<div class='form-container'>");
        out.println("<form method='post' action='transport-service'>");
        out.println("<input type='hidden' name='action' value='buyTicket'/>");

        out.println("<label>Выберите пассажира:</label>");
        out.println("<select name='passengerId' required>");
        for (Passenger passenger : passengers) {
            String ticketStatus = passenger.isHasTicket() ? " (уже есть билет)" : "";
            out.println("<option value='" + passenger.getId() + "'>"
                    + passenger.getName() + " - " + passenger.getDestination() + ticketStatus + "</option>");
        }
        out.println("</select>");

        out.println("<label>Выберите автобус:</label>");
        out.println("<select name='busId' required>");
        for (Bus bus : buses) {
            String status = bus.isFull()
                    ? " (ЗАПОЛНЕН)"
                    : " (" + (bus.getCapacity() - bus.getCurrentPassengers()) + " мест)";
            String disabled = bus.isFull() ? " disabled" : "";
            out.println("<option value='" + bus.getId() + "'" + disabled + ">Маршрут "
                    + bus.getRouteNumber() + " - " + bus.getDriverName() + status + "</option>");
        }
        out.println("</select>");

        out.println("<p style='color: #667eea; font-size: 14px; margin-top: 15px;'>Билет будет куплен, и пассажир будет добавлен в автобус</p>");

        out.println("<div style='margin-top: 20px;'>");
        out.println("<button class='info' type='submit'>Купить билет</button>");
        out.println("<button class='secondary' type='button' onclick=\"location.href='transport-service'\">Отмена</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Действия (POST)
    // ─────────────────────────────────────────────────────────────────────────

    private void createBus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionData data = getSessionData(request);
        String routeNumber = request.getParameter("routeNumber");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String driverName = request.getParameter("driverName");

        Bus newBus = new Bus(data.getNextBusId(), routeNumber, capacity, driverName);
        data.getBuses().add(newBus);

        System.out.println(">>> [SESSION] " + request.getSession().getId()
                + " добавил автобус: " + routeNumber);

        response.sendRedirect("transport-service?action=showBuses");
    }

    private void createPassenger(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionData data = getSessionData(request);
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String destination = request.getParameter("destination");

        Passenger newPassenger = new Passenger(data.getNextPassengerId(), name, phoneNumber, destination);
        data.getPassengers().add(newPassenger);

        System.out.println(">>> [SESSION] " + request.getSession().getId()
                + " добавил пассажира: " + name);

        response.sendRedirect("transport-service?action=showPassengers");
    }

    private void boardPassenger(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionData data = getSessionData(request);
        int passengerId = Integer.parseInt(request.getParameter("passengerId"));
        int busId = Integer.parseInt(request.getParameter("busId"));

        Bus bus = data.getBuses().stream().filter(b -> b.getId() == busId).findFirst().orElse(null);
        Passenger passenger = data.getPassengers().stream().filter(p -> p.getId() == passengerId).findFirst().orElse(null);

        if (bus != null && passenger != null && bus.addPassenger()) {
            passenger.buyTicket();
        }

        response.sendRedirect("transport-service?action=showBuses");
    }

    private void buyTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionData data = getSessionData(request);
        int passengerId = Integer.parseInt(request.getParameter("passengerId"));
        int busId = Integer.parseInt(request.getParameter("busId"));

        Bus bus = data.getBuses().stream().filter(b -> b.getId() == busId).findFirst().orElse(null);
        Passenger passenger = data.getPassengers().stream().filter(p -> p.getId() == passengerId).findFirst().orElse(null);

        if (bus != null && passenger != null && !bus.isFull()) {
            bus.addPassenger();
            passenger.buyTicket();
            System.out.println(">>> [SESSION] " + request.getSession().getId()
                    + " купил билет: пассажир #" + passengerId + " в автобус #" + busId);
        }

        response.sendRedirect("transport-service");
    }

    private void deleteBus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionData data = getSessionData(request);
        int busId = Integer.parseInt(request.getParameter("busId"));
        data.getBuses().removeIf(bus -> bus.getId() == busId);
        response.sendRedirect("transport-service?action=showBuses");
    }

    private void deletePassenger(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionData data = getSessionData(request);
        int passengerId = Integer.parseInt(request.getParameter("passengerId"));
        data.getPassengers().removeIf(passenger -> passenger.getId() == passengerId);
        response.sendRedirect("transport-service?action=showPassengers");
    }
}

