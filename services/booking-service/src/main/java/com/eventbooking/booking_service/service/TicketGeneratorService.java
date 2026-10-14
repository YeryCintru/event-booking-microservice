package com.eventbooking.booking_service.service;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Documented;

import org.springframework.stereotype.Service;

import com.eventbooking.booking_service.dto.BookingDTO;
import com.eventbooking.booking_service.dto.EventDTO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class TicketGeneratorService {
    
    // Método para generar la imagen del QR en memoria
    public byte[] generateQRCodeImage(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return pngOutputStream.toByteArray();
    }

    // Método para armar el PDF completo
    public byte[] generateTicketPdf(BookingDTO booking, EventDTO event) throws Exception {
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        //Estilos y encabezdos
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        document.add(new Paragraph("🎟️ Entrada Oficial - EventBooking", titleFont));
        document.add(new Paragraph("Evento" + event.getTitle()));
        document.add(new Paragraph("Fecha: " + event.getClass()));
        document.add(new Paragraph("Reserva ID: " + booking.getId()));
        
        String qrPayload = "https://eventbooking.com/verify?bookingId=" + booking.getId();
        byte[] qrBytes = generateQRCodeImage(qrPayload, 150, 150);
        Image qrImage = Image.getInstance(qrBytes);
        document.add(qrImage);

        document.close();
        return out.toByteArray();
    }

}


