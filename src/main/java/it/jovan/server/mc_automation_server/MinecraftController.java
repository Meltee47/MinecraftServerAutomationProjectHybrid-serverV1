package it.jovan.server.mc_automation_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class MinecraftController {

    @Autowired
    private StatusService statusService;

    @PostMapping("/host")
    public ResponseEntity<String> avviaHosting(@RequestParam String ip) {
        boolean successo = statusService.tryToHost(ip);
        if (successo) {
            return ResponseEntity.ok("Ora sei tu l'host!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Errore: Qualcuno sta già hostando su " + statusService.getStatus());
        }
    }

    @PostMapping("/stop")
    public String fermaHosting() {
        statusService.stopHosting();
        return "Stato resettato. Ora qualcun altro può hostare.";
    }
    
    //------------------------------------//
    //-----Invisible, but needed MEGA-----//
    //------------------------------------//
    
    // Usiamo valori di default per evitare crash se le variabili mancano
    @Value("${MEGA_USER:MISSING}")
    private String megaUser;

    @Value("${MEGA_PASS:MISSING}")
    private String megaPass;
    
    @GetMapping("/status")
    public String getStato() {
        return statusService.getStatus();
    }
    
    @GetMapping("/config")
    public ResponseEntity<String> getConfig() {
        // Se vedi "MISSING", significa che Eclipse non sta passando le variabili
        if ("MISSING".equals(megaUser) || "MISSING".equals(megaPass)) {
            System.err.println("[DEBUG] Variabili MEGA_USER o MEGA_PASS non trovate!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore: Credenziali non configurate sul server.");
        }
        return ResponseEntity.ok(megaUser + "|" + megaPass);
    }
    
}