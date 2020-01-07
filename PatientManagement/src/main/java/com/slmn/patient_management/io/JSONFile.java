package com.slmn.patient_management.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class JSONFile {
    private String filename;

    public JSONFile(String filename) {
        this.filename = filename;
    }

    public String read() throws FileNotFoundException {
        String fileContent = (new Scanner(new File(this.filename)).useDelimiter("\n")).next();
        System.out.println("File output:");
        System.out.println(fileContent);
        return fileContent;
    }

    public String readOrCreate(String defaultText) {
        File file = new File(this.filename);
        if (!file.isFile()) {
            // return early
            try {
                System.out.println("Creating a new file at " + file.getAbsolutePath());
                file.createNewFile();
                PrintWriter out = new PrintWriter(file.getAbsolutePath());
                out.println(defaultText);
                out.close();
                System.out.println("Default text:");
                System.out.println(defaultText);
            } catch (IOException e) {
                System.out.println("New file couldn't be created - returning default text");
                e.printStackTrace();
            }

            return defaultText;
        }

        // handle normally
        try {
            return this.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File couldn't be read - returning default text");
            return defaultText;
        }
    }

    public void write() {

    }

}