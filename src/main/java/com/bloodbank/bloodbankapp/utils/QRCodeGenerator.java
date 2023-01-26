package com.bloodbank.bloodbankapp.utils;

import com.bloodbank.bloodbankapp.model.Appointment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {

    public static void generateQRImage() throws WriterException, IOException {
        String url = "http://localhost:3000/confirmation";
        int width = 200, height = 200;
        String fp = "static/confirmationQR.png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(fp);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static byte[] getQRImageBytes() throws WriterException, IOException {
        String url = "http://localhost:3000/confirmation";
        int width = 200, height = 200;
        String fp = "../../../../../../static/confirmationQR.png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public static void generateQRForAppointment(Long id) throws WriterException, IOException {
        String url = "http://localhost:3000/appointment-processing/"+id.toString();
        int width = 200, height = 200;
        String fp = "static/confirmation_"+id.toString()+"_QR.png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(fp);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


}
