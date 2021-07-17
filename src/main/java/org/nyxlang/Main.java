package org.nyxlang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public Main() {
        System.out.println("Welcome to nyxlang");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                var program = new StringBuilder();
                System.out.print("Enter program code > ");
                while (true) {
                    var line = reader.readLine();
                    program.append(line);
                    if (!line.endsWith("\\")) {
                        break;
                    } else {
                        program.replace(program.length() - 1, program.length(), "\n");
                    }
                }
                RunnerKt.execute(program.toString(), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
