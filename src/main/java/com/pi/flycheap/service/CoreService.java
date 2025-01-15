package com.pi.flycheap.service;

import com.pi.flycheap.entity.Ticket;
import com.pi.flycheap.integration.SkyScanner;
import com.pi.flycheap.integration.model.request.FlightRequest;
import com.pi.flycheap.integration.model.response.FlightResponse;
import com.pi.flycheap.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
            FlightRequest flightRequest = new FlightRequest(ticket.getStartPoint(), ticket.getEndPoint(), ticket.getStartDate(), ticket.getEndDate());
            List<FlightResponse> flights = skyScanner.getFlights(flightRequest);
            flights.forEach(flight -> {
                if (flight.getPrice() < ticket.getPrice()){emailService.sendMail("","","");}
            });
        });
    }
}
