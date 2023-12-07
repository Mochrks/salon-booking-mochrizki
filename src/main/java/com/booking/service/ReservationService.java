package com.booking.service;

import java.util.*;

import com.booking.models.Customer;
import com.booking.models.Membership;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class ReservationService {
    private static int orderCounter = 1;

    public static void createReservation(List<Person> listAllCustomer, List<Person> listAllEmployee,
            List<Reservation> reservationList,
            List<Service> listAllServices, Scanner input) {

        // print data semua customer
        PrintService.showAllCustomer(listAllCustomer);

        // =======================================================================
        // Input Customer ID
        Customer foundCustomer = findCustomerById(listAllCustomer, input);
        // Input Employee ID
        boolean employeeFound = findEmployeeById(listAllEmployee, input);
        if (!employeeFound) {
            return;
        }
        // Input Service ID
        List<Service> selectedServices = selectServices(listAllServices, input);
        // =============================================================================

        // Perhitungan
        double totalCost = 0;
        for (Service service : selectedServices) {
            totalCost += service.getPrice();
        }

        double discountedCost = totalCost;
        if (foundCustomer instanceof Customer) {
            Customer customer = (Customer) foundCustomer;
            Membership membership = customer.getMember();

            if (membership != null) {
                String membershipName = membership.getMembershipName().toLowerCase();
                switch (membershipName) {
                    case "silver":
                        discountedCost *= 0.95; // Diskon 5% untuk member silver
                        break;
                    case "gold":
                        discountedCost *= 0.9; // Diskon 10% untuk member gold
                        break;
                    default:

                        break;
                }
            }
        }

        System.out.println("Booking Berhasil!");
        System.out.println("Total Biaya Booking setelah diskon : Rp." + discountedCost);

        // ========================================================================================
        // simpan objek
        String rsvId = generateReservasiID(reservationList);

        Reservation reservasiOrder = Reservation.builder()
                .reservationId(rsvId)
                .customer(foundCustomer)
                .services(selectedServices)
                .reservationPrice(discountedCost)
                .workstage("In process")
                .build();

        reservationList.add(reservasiOrder);
    }

    public static String getServiceNames(List<Service> services) {
        StringBuilder serviceNames = new StringBuilder();
        for (Service service : services) {
            serviceNames.append(service.getServiceName()).append(", ");
        }
        // Menghilangkan koma terakhir dan spasi
        return serviceNames.length() > 0 ? serviceNames.substring(0, serviceNames.length() - 2) : "";
    }

    public static void editReservationWorkstage(List<Reservation> reservationList, Scanner input) {
        boolean foundReservation = false;

        do {
            PrintService.displayAksiReservasi(reservationList);
            System.out.print("Silahkan Masukkan Reservation Id : ");
            String reservasiId = input.nextLine();

            Reservation found = null;

            for (Reservation reservation : reservationList) {
                if (reservation.getReservationId().equalsIgnoreCase(reservasiId)) {
                    if (reservation.getWorkstage().equalsIgnoreCase("In process")) {
                        found = reservation;
                        System.out.print("Selesaikan Reservasi : ");
                        String newReservasi = input.nextLine();
                        reservation.setWorkstage(newReservasi);
                        System.out.println("Reservasi dengan id " + reservasiId + " sudah " + newReservasi + ".");
                        foundReservation = true;
                        break;
                    } else {
                        System.out.println("Reservation yang dicari sudah selesai");
                    }
                }
            }

            if (found == null) {
                System.out.println("Reservation yang dicari tidak tersedia.");

            }
        } while (!foundReservation);
    }

    private static Customer findCustomerById(List<Person> listAllCustomer, Scanner input) {

        Customer foundCustomer = null;
        boolean customerFound = false;

        do {
            System.out.println("Silahkan Masukkan Customer Id:");
            String customerID = input.nextLine();

            for (Person customer : listAllCustomer) {
                if (customer.getId().equalsIgnoreCase(customerID)) {

                    foundCustomer = (Customer) customer;
                    customerFound = true;
                    break;
                }
            }

            if (!customerFound) {
                System.out.println("Customer ID tidak ditemukan. Silahkan coba lagi.");
            }
        } while (!customerFound);

        return foundCustomer;
    }

    private static boolean findEmployeeById(List<Person> listAllEmployee, Scanner input) {
        PrintService.showAllEmployee(listAllEmployee);
        boolean employeeFound = false;

        do {
            System.out.println("Silahkan Masukkan Employee Id:");
            String employeeID = input.nextLine();

            for (Person employee : listAllEmployee) {
                if (employee.getId().equalsIgnoreCase(employeeID)) {
                    employeeFound = true;
                    break;
                }
            }

            if (!employeeFound) {
                System.out.println("Employee ID tidak ditemukan. Silahkan coba lagi.");
            }
        } while (!employeeFound);

        return employeeFound;
    }

    private static List<Service> selectServices(List<Service> listAllServices, Scanner input) {
        PrintService.showAllServices(listAllServices);

        List<Service> selectedServices = new ArrayList<>();
        boolean moreServices;

        do {
            boolean foundService = false;
            String serviceID;

            do {
                System.out.println("Silahkan Masukkan Service Id:");
                serviceID = input.nextLine();

                for (Service service : listAllServices) {
                    if (service.getServiceId().equalsIgnoreCase(serviceID)) {
                        foundService = true;
                        selectedServices.add(service);
                        break;
                    }
                }

                if (!foundService) {
                    System.out.println("Service ID tidak ditemukan. Silahkan coba lagi.");
                }
            } while (!foundService);

            System.out.println("Ingin pilih service yang lain (Y/T)?");
            String choice = input.next();
            input.nextLine();
            moreServices = choice.equalsIgnoreCase("Y");
        } while (moreServices);

        return selectedServices;
    }

    public static String generateReservasiID(List<Reservation> reservationList) {
        return "Rsv-" + String.format("%02d", orderCounter++);
    }

}
