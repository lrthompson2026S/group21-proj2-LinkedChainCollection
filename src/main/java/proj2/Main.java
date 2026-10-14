package proj2;

import proj2.core.Driver;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter something:");

        System.out.println("Has next line: " + scanner.hasNextLine());

        System.out.println(scanner.nextLine());

        Driver.run(args);
    }
}


