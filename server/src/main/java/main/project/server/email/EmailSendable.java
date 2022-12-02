package main.project.server.email;

public interface EmailSendable {
    void send(String[] to, String subject, String message, String templateName) throws InterruptedException;
}
