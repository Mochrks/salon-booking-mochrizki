package com.booking.service;

import java.util.Scanner;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static int validateInput(Scanner scanner) {
        int option = -1;
        boolean isValid = false;

        while (!isValid) {

            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan harus berupa angka!");
            }
        }

        return option;
    }

    // public static void validateCustomerId() {

    // }
}
