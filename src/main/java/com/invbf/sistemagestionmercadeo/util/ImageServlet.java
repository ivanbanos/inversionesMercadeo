/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.controladores.SessionBean;
import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    // Properties ---------------------------------------------------------------------------------
    // Init ---------------------------------------------------------------------------------------
    public void init() throws ServletException {
        // Define base path somehow. You can define it as init-param of the servlet.
        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\var\webapp\images".
        // In Linux/Mac/UNIX, it is just straightforward "/var/webapp/images".
    }

    // Actions ------------------------------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entra a buscar la imagen");

        // Get requested image by path info.
        String requestedImage = request.getPathInfo();
        System.out.println(requestedImage);
        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        System.out.println("Entra a buscar la imagen");
        byte[] bytesArray = null;

        FTPClient client = new FTPClient();
        String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
        String sUser = ConfiguracionDao.findByNombre("FTPuser").getValor();
        String sPassword = ConfiguracionDao.findByNombre("FTPpassword").getValor();

        client.connect(sFTP);
        boolean login = client.login(sUser, sPassword);

        int reply = client.getReplyCode();

        System.out.println("Respuesta recibida de conexión FTP:" + reply);

        if (FTPReply.isPositiveCompletion(reply)) {
            System.out.println("Conectado Satisfactoriamente");
        } else {
            System.out.println("Imposible conectarse al servidor");
        }
        client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
        client.setFileType(FTP.BINARY_FILE_TYPE);

        InputStream inputStream = client.retrieveFileStream(requestedImage);
        if (inputStream != null) {
            bytesArray = IOUtils.toByteArray(inputStream);
        }

        boolean success = client.completePendingCommand();
        if (success) {
            System.out.println("File has been downloaded successfully.");
        }
        inputStream.close();
        if (bytesArray == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        System.out.println("Entra a buscar la imagen");
        // Get content type by filename.
        String contentType = getServletContext().getMimeType(requestedImage);

        System.out.println(contentType);
        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        if (contentType == null || !contentType.startsWith("image")) {
            // Do your thing if the file appears not being a real image.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(bytesArray.length));

        // Write image content to response.
        response.getOutputStream().write(bytesArray);
    }
}
