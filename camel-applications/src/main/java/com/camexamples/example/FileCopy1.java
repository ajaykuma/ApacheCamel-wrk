package com.camexamples.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.springframework.core.log.LogFormatUtils;

public class FileCopy1 {

    public static void main(String[] args) {

     String sfile = "inbox/test1.txt";
     String ofile = "inbox/test1out.txt";
     copyFile(sfile, ofile);
    }
    public static void copyFile(String sfile, String ofile) {
    	try {
    		FileReader fin = new FileReader(sfile);
    		FileWriter fout = new FileWriter(ofile, true);
    		int c;
    		while ((c = fin.read()) != -1) {
    			fout.write(c);
    	}
    	System.out.println("copy finished");
    	fin.close();
    	fout.close();
    } catch (Exception e) {
    	e.printStackTrace();
    }
    }}
