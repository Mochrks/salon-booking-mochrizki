package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = { "Show Data", "Create Reservation", "Complete/cancel reservation", "Exit" };
        String[] subMenuArr = { "Recent Reservation", "Show Customer", "Show Available Employee",
                "Show History Reservation", "Back to main menu" };

        int optionMainMenu;
        int optionSubMenu;

        boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            System.out.println("=====================================================================");
            PrintService.printMenu("Main Menu", mainMenuArr);
            System.out.println("=====================================================================");
            System.out.print("Masukkan pilihan anda: ");
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        System.out.println("=====================================================================");
                        PrintService.printMenu("Show Data", subMenuArr);
                        System.out.println("=====================================================================");
                        System.out.print("Masukkan pilihan anda: ");
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                PrintService.displayReservasi(reservationList);
                                break;
                            case 2:
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                PrintService.showHistoryReservation(reservationList);

                                break;
                            case 0:
                                backToSubMenu = true; // Kembali ke menu utama
                                break;

                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    ReservationService.createReservation(personList, personList, reservationList, serviceList, input);

                    break;
                case 3:
                    ReservationService.editReservationWorkstage(reservationList, input);
                    break;
                case 0:
                    backToMainMenu = true; // keluar

                    break;
            }
        } while (!backToMainMenu);

    }
}
