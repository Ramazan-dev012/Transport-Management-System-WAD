package com.example.transportsystem.controller;

import com.example.transportsystem.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    public static class SendEmailRequest {
        private String to;
        private String subject;
        private String text;

        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    /**
     * Тестовый эндпоинт из лабораторной: отправка письма.
     *
     * Пример:
     * POST /email/send?to=user@mail.com&subject=Hello&text=Test
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam(required = false) String to,
                                       @RequestParam(required = false) String subject,
                                       @RequestParam(required = false) String text,
                                       @RequestBody(required = false) SendEmailRequest body) {
        try {
            String finalTo = (body != null && body.getTo() != null && !body.getTo().isBlank()) ? body.getTo() : to;
            String finalSubject = (body != null && body.getSubject() != null) ? body.getSubject() : subject;
            String finalText = (body != null && body.getText() != null) ? body.getText() : text;

            if (finalTo == null || finalTo.isBlank()) {
                return ResponseEntity.badRequest().body("Email NOT sent. to is required");
            }
            if (finalSubject == null) finalSubject = "";
            if (finalText == null) finalText = "";

            emailService.sendEmail(finalTo, finalSubject, finalText);
            return ResponseEntity.ok("Email sent");
        } catch (Exception e) {
            String msg = e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "" : e.getMessage());
            return ResponseEntity.badRequest().body("Email NOT sent. " + msg);
        }
    }

    /**
     * Быстрый тест из браузера без CSRF:
     * GET /email/send-test?to=...&subject=...&text=...
     */
    @GetMapping("/send-test")
    public ResponseEntity<String> sendTest(@RequestParam String to,
                                           @RequestParam(defaultValue = "Test") String subject,
                                           @RequestParam(defaultValue = "Hello") String text) {
        try {
            emailService.sendEmail(to, subject, text);
            return ResponseEntity.ok("Email sent");
        } catch (Exception e) {
            String msg = e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "" : e.getMessage());
            return ResponseEntity.badRequest().body("Email NOT sent. " + msg);
        }
    }

    /**
     * Альтернативный маппинг для send-test без дефиса, для обхода возможных проблем с URL/прокси/клиентом.
     */
    @GetMapping("/sendtest")
    public ResponseEntity<String> sendTestAlias(@RequestParam String to,
                                                @RequestParam(defaultValue = "Test") String subject,
                                                @RequestParam(defaultValue = "Hello") String text) {
        return sendTest(to, subject, text);
    }

    /**
     * Простой эндпоинт "ping" для проверки доступности контроллера.
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ok");
    }

    /**
     * Удобно для Postman: отправка email через JSON body.
     * POST /email/send-json
     * Body: {"to":"...","subject":"...","text":"..."}
     */
    @PostMapping("/send-json")
    public ResponseEntity<String> sendJson(@RequestBody SendEmailRequest req) {
        try {
            if (req == null || req.getTo() == null || req.getTo().isBlank()) {
                return ResponseEntity.badRequest().body("Email NOT sent. to is required");
            }
            String subject = (req.getSubject() == null ? "" : req.getSubject());
            String text = (req.getText() == null ? "" : req.getText());
            emailService.sendEmail(req.getTo(), subject, text);
            return ResponseEntity.ok("Email sent");
        } catch (Exception e) {
            String msg = e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "" : e.getMessage());
            return ResponseEntity.badRequest().body("Email NOT sent. " + msg);
        }
    }
}
