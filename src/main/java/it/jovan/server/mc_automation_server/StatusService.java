package it.jovan.server.mc_automation_server;

import org.springframework.stereotype.Service;

@Service
public class StatusService {
    // Variabile in memoria: inizialmente nessuno hosta
    private String currentIP = "NONE";

    // Metodo per leggere lo stato
    public String getStatus() {
        return currentIP;
    }

    // Metodo "Sincronizzato": solo un utente alla volta può entrare qui
    public synchronized boolean tryToHost(String ip) {
        if (currentIP.equals("NONE")) {
            currentIP = ip;
            return true; // Occupato con successo
        }
        return false; // Già occupato da qualcun altro
    }

    // Metodo per liberare il posto
    public synchronized void stopHosting() {
        currentIP = "NONE";
    }
}
