package com.booking.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
        public static void printMenu(String title, String[] menuArr) {
                int num = 1;
                System.out.println(title);
                for (int i = 0; i < menuArr.length; i++) {
                        if (i == (menuArr.length - 1)) {
                                num = 0;
                        }
                        System.out.println(num + ". " + menuArr[i]);
                        num++;
                }
        }

        public String printServices(List<Service> serviceList) {
                String result = "";
                // Bisa disesuaikan kembali
                for (Service service : serviceList) {
                        result += service.getServiceName() + ", ";
                }
                return result;
        }

        public static void displayReservasi(List<Reservation> reservationList) {
                int num = 1;
                System.out.println(
                                "=================================================================================================================================================================");
                System.out.printf("| %-4s | %-8s | %-20s | %-55s | %-10s | %-10s |\n", "No", "ID", "Nama Customer",
                                "Service",
                                "Total Biaya", "Workstage");
                System.out.println(
                                "=================================================================================================================================================================");

                for (Reservation reservasi : reservationList) {
                        if (reservasi.getWorkstage().equalsIgnoreCase("Waiting")
                                        || reservasi.getWorkstage().equalsIgnoreCase("In process")) {
                                // get data customer
                                Customer customer = reservasi.getCustomer();
                                String customerName = customer.getName();
                                // get data-data services
                                List<Service> services = reservasi.getServices();
                                String serviceNames = ReservationService.getServiceNames(services);

                                System.out.printf("| %-4s | %-8s | %-20s | %-55s | %-10s | %-10s | \n", num,
                                                reservasi.getReservationId(),
                                                customerName,
                                                serviceNames,
                                                reservasi.getReservationPrice(),
                                                reservasi.getWorkstage());
                                num++;
                        }
                }

                System.out.println(
                                "=================================================================================================================================================================");

        }

        public static void displayAksiReservasi(List<Reservation> reservationList) {
                int num = 1;
                System.out.println(
                                "=================================================================================================================================================================");
                System.out.printf("| %-4s | %-8s | %-55s | %-10s  |\n", "No", "ID",
                                "Service",
                                "Total Biaya");
                System.out.println(
                                "=================================================================================================================================================================");

                for (Reservation reservasi : reservationList) {

                        // get data-data services
                        List<Service> services = reservasi.getServices();
                        String serviceNames = ReservationService.getServiceNames(services);

                        System.out.printf("| %-4s | %-8s | %-55s | %-10s  | \n", num,
                                        reservasi.getReservationId(),
                                        serviceNames,
                                        reservasi.getReservationPrice());
                        num++;
                }

                System.out.println(
                                "=================================================================================================================================================================");

        }

        public static void showHistoryReservation(List<Reservation> reservationList) {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                double totalReservation = 0.0;
                int num = 1;
                System.out.println(
                                "=================================================================================================================================================================");
                System.out.printf("| %-4s | %-8s | %-20s | %-55s | %-10s | %-10s |\n", "No", "ID", "Nama Customer",
                                "Service",
                                "Total Biaya", "Workstage");
                System.out.println(
                                "=================================================================================================================================================================");

                for (Reservation reservasi : reservationList) {
                        // get data customer
                        Customer customer = reservasi.getCustomer();
                        String customerName = customer.getName();
                        // get data-data services
                        List<Service> services = reservasi.getServices();
                        String serviceNames = ReservationService.getServiceNames(services);

                        System.out.printf("| %-4s | %-8s | %-20s | %-55s | %-10s | %-10s | \n", num,
                                        reservasi.getReservationId(),
                                        customerName,
                                        serviceNames,
                                        reservasi.getReservationPrice(),
                                        reservasi.getWorkstage());

                        if (reservasi.getWorkstage().equalsIgnoreCase("finish")) {
                                totalReservation += reservasi.getReservationPrice();
                        }
                        num++;
                }

                System.out.println(
                                "=================================================================================================================================================================");
                System.out.printf("| %-13s | %-93s |\n", "Total Keuntungan", decimalFormat.format(totalReservation));

                System.out.println(
                                "=================================================================================================================================================================");

        }

        public static void showAllCustomer(List<Person> listAllCustomer) {
                int num = 1;

                System.out.println("=====================================================================");
                System.out.println("Data Customer ");
                System.out.println(
                                "=======================================================================================================================");
                System.out.printf("|%-5s | %-13s | %-10s | %-20s | %-10s |%-20s | %-20s |\n", "No.",
                                "ID", "Nama", "Alamat", "Pengalaman", "Membership", "Uang");
                System.out.println(
                                "=======================================================================================================================");
                for (Person customer : listAllCustomer) {
                        if (customer instanceof Customer) {
                                Customer customer2 = (Customer) customer;

                                System.out.printf("|%-5s | %-13s | %-10s | %-20s | %-10s |%-20s | %-20s |\n", num,
                                                customer.getId(),
                                                customer.getName(), customer.getAddress(), customer.getName(),
                                                customer2.getMember().getMembershipName(), customer2.getWallet());
                                num++;
                                System.out.print("\n");
                        }
                }

                System.out.println(
                                "=======================================================================================================================");

        }

        public static void showAllEmployee(List<Person> listAllEmployee) {
                System.out.println("=====================================================================");
                System.out.println("Data Employee ");
                System.out.println("================================================================================");
                System.out.printf("|%-5s | %-13s | %-10s | %-20s | %-10s |\n", "No", "ID", "Nama", "Alamat",
                                "Pengalaman");
                System.out.println("================================================================================");

                int[] num = { 1 };

                listAllEmployee.stream()
                                .filter(employee -> employee instanceof Employee)
                                .map(employee -> (Employee) employee)
                                .forEach(employee -> {
                                        System.out.printf("|%-5s | %-13s | %-10s | %-20s | %-10s |\n", num[0],
                                                        employee.getId(),
                                                        employee.getName(), employee.getAddress(),
                                                        employee.getExperience());
                                        num[0]++;
                                        System.out.print("\n");
                                });

                System.out.println("================================================================================");
        }

        public static void showAllServices(List<Service> listAllServices) {
                System.out.println("=====================================================================");
                System.out.println("Data Services ");
                System.out.println("================================================================================");
                System.out.printf("|%-5s | %-23s | %-20s | %-20s |\n", "No", "ID", "Nama", "Price");
                System.out.println("================================================================================");

                int[] num = { 1 };
                listAllServices.stream()
                                .filter(service -> service instanceof Service)
                                .forEachOrdered(service -> {
                                        System.out.printf("|%-5s | %-23s | %-20s | %-20s |\n",
                                                        num[0],
                                                        service.getServiceId(), service.getServiceName(),
                                                        service.getPrice());
                                        num[0]++;
                                        System.out.print("\n");
                                });

                System.out.println("================================================================================");
        }

}
