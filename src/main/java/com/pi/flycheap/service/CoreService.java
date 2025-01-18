package com.pi.flycheap.service;

import com.pi.flycheap.entity.Ticket;
import com.pi.flycheap.integration.SkyScanner;
import com.pi.flycheap.integration.model.response.FlightResponse;
import com.pi.flycheap.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoreService {

    private final EmailService emailService;
    private final SkyScanner skyScanner;
    private final TicketRepository ticketRepository;

    @Scheduled(cron = "0 0,30 * * * *")
    public void checkTickets(){
        List<Ticket> tickets = ticketRepository.findAllBy();
        tickets.forEach(ticket -> {
            List<FlightResponse> flights;
            try {
                flights = skyScanner.getFlights(ticket);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException("Exception encountered for try at: " + LocalDateTime.now());
            }
            flights.forEach(flight -> {
                if (flight.getPrice() < ticket.getPrice()){
                    System.out.println("Flight is eligible: " + flight.getAirline() + " " + flight.getPrice());
                    //emailService.sendMail("","","");
                }
            });
        });
    }
}
