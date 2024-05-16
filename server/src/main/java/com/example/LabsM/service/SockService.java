package com.example.LabsM.service;

import com.example.LabsM.entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Service
public class SockService {
    public Integer checkOp(String cardnum, String code, Float amount, String airpacc) {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("192.168.0.110", 12345);
        try {
            socket.setSoTimeout(5000);
            socket.connect(address);
            System.out.println("Connected");
            OutputStream stream = socket.getOutputStream();
            InputStream instream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            String message = "airport";
            stream.write(message.getBytes(StandardCharsets.UTF_8));
            stream.flush();
            String recline;
            try {
                recline = reader.readLine();
                System.out.println("Received: " + recline);
            } catch (Exception e) {
                System.out.println("ReadLine timeout 1");
            };

            Transaction trobj = new Transaction(cardnum, code, amount, airpacc);
            ObjectMapper mapper = new ObjectMapper();
            String sendste = mapper.writeValueAsString(trobj);
            stream.write(sendste.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent data");
            stream.flush();

            try {
                recline = reader.readLine();
                System.out.println("Received: " + recline);
                if(recline.equals("success")) {
                    System.out.println("Transaction Successful");
                    return 1;
                } else if (recline.equals("notenough")) {
                    System.out.println("Not Enough");
                    return 2;
                } else if (recline.equals("wrongairport")) {
                    System.out.println("Wrong airport");
                    return 3;
                } else if (recline.equals("wrongclient")) {
                    System.out.println("Wrong client");
                    return 4;
                }
            } catch (Exception e) {
                System.out.println("ReadLine timeout 2");
            };

            socket.close();
            System.out.println("Disconnected");

        } catch (Exception e) {
            System.out.println(e);
            return 1;
        };

        return 0;
    }
}
