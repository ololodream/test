package com.example.bookingdemo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/bookings")
public class BookingController {
    /*private List<HotelBooking> bookings;*/
    BookingRepository bookingRepository;
    public BookingController(BookingRepository bookingRepository)
    {
        this.bookingRepository = bookingRepository;
    }

   /* public BookingController(){
        bookings = new ArrayList<>();
        bookings.add(new HotelBooking("Marriot", 200.50,3));
        bookings.add(new HotelBooking("Ibis", 90,4));
        bookings.add(new HotelBooking("Novotel", 140,1));
    }*/

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<HotelBooking> getAll()
    {
        /*return bookings;*/
        return this.bookingRepository.findAll();
    }

    /*@RequestMapping(value = "/affordable/{price}", method = RequestMethod.GET)
    public List<HotelBooking> getAffordable(@PathVariable double price)
    {
        *//*return bookings.stream().filter(x -> x.getPricePerNight() < price ).collect(Collectors.toList());*//*
        return this.bookingRepository.findByPricePerNightLessThan(price);
    }*/

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public List<HotelBooking> create(@RequestBody HotelBooking hotelBooking)
    {
        /*bookings.add(hotelBooking);
        return bookings;*/
        bookingRepository.save(hotelBooking);
        return bookingRepository.findAll();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public List<HotelBooking> delete(@PathVariable long id)
    {

        bookingRepository.deleteById(id);
        return bookingRepository.findAll();
    }
}
