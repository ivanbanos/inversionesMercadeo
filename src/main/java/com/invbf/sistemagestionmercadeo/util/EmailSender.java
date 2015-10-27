/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.util;

import com.invbf.sistemagestionmercadeo.dao.ConfiguracionDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
public class EmailSender implements Serializable {

    private int port;
    private String host;
    private String from;
    private boolean auth = true;
    private String username;
    private String password;
    private Protocol protocol;
    private boolean debug = true;

    public EmailSender() {
    }

    public void sendEmail(String to, String subject, String mesaje, String nombre) throws MessagingException, IOException {
        FTPClient client = null;
        try {
            client = new FTPClient();
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port + "");
            switch (protocol) {
                case SMTPS:
                    props.put("mail.smtp.ssl.enable", true);
                    break;
                case POP3:
                case IMAP:
                case SMTP:
                case TLS:
                    props.put("mail.smtp.starttls.enable", true);
                    break;
            }

            Authenticator authenticator = null;
            if (auth) {
                props.put("mail.smtp.auth", "true");
                authenticator = new Authenticator() {
                    private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return pa;
                    }
                };
            }
            Session session = Session.getInstance(props, authenticator);
            session.setDebug(debug);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<p>" + mesaje + "</p>";
            if (!nombre.equals("noimage")) {
                htmlText += "<img src=\"cid:image\">";
            }
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);
            if (!nombre.equals("noimage")) {

                byte[] bytesArray = null;

                String remoteFile2 = nombre;

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

                InputStream inputStream = client.retrieveFileStream(remoteFile2);
                bytesArray = IOUtils.toByteArray(inputStream);

                boolean success = client.completePendingCommand();
                if (success) {
                    System.out.println("File has been downloaded successfully.");
                }
                inputStream.close();

                // second part (the image)
                messageBodyPart = new MimeBodyPart();

                DataSource fds = new ByteArrayDataSource(bytesArray, new MimetypesFileTypeMap().getContentType(nombre));

                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "<image>");
            }
            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            Transport.send(message);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {

            System.out.println(ex);
        } finally {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException ex) {

                System.out.println(ex);
            }
        }
    }

    public void sendEmailCliente(String to, String subject, String mesaje, String nombre) throws MessagingException, IOException {
        FTPClient client = null;
        try {
            client = new FTPClient();
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port + "");
            switch (protocol) {
                case SMTPS:
                    props.put("mail.smtp.ssl.enable", true);
                    break;
                case POP3:
                case IMAP:
                case SMTP:
                case TLS:
                    props.put("mail.smtp.starttls.enable", true);
                    break;
            }

            Authenticator authenticator = null;
            if (auth) {
                props.put("mail.smtp.auth", "true");
                authenticator = new Authenticator() {
                    private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return pa;
                    }
                };
            }
            Session session = Session.getInstance(props, authenticator);
            session.setDebug(debug);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            BodyPart messageBodyPart2 = new MimeBodyPart();
            String htmlText = "<p>" + mesaje + "</p>";
            if (!nombre.equals("noimage")) {
                htmlText += "<img style=\"width:1000px\" src=\"cid:image\">";
                htmlText += "<div style=\"background-color:#DFDFDF;width: 1000px;\">\n"
                        + "            <table style=\"background-color:#DFDFDF;width: 100%;\">\n"
                        + "                <tbody>\n"
                        + "                    <tr>\n"
                        + "                        <td>\n"
                        + "\n"
                        + "\n"
                        + "                            <table >\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>TEXAS LUXURY CASINO</b><br />texasluxury@ibfcolombia.com<br /><a href=\"http://www.texasluxurycasino.com\">texasluxurycasino.com</a></td>\n"
                        + "                                    <td>\n"
                        + "                                        <a href=\"http://www.facebook.com/TexasLuxuryCasino\">\n"
                        + "                                            <img src=\"cid:facebook1\" alt=\"facebook\" style=\"widht:100px;\">\n"
                        + "                                        </a>\n"
                        + "                                    </td>\n"
                        + "                                    <td>\n"
                        + "                                        <a href=\"http://www.twitter.com/TexasLuxuryC\">\n"
                        + "                                            <img src=\"cid:twitter1\" alt=\"twitter\" style=\"widht:100px;\">\n"
                        + "                                        </a>\n"
                        + "                                    </td>\n"
                        + "                                </tr>\n"
                        + "                            </table>\n"
                        + "\n"
                        + "\n"
                        + "                        </td>\n"
                        + "                        <td align=\"right\">\n"
                        + "\n"
                        + "\n"
                        + "                            <table >\n"
                        + "                                <tr>\n"
                        + "                                    <td>\n"
                        + "                                        <a href=\"http://www.facebook.com/MastersRoyaleCasino\">\n"
                        + "                                            <img src=\"cid:facebook2\" alt=\"facebook\" style=\"widht:100px;\">\n"
                        + "                                        </a>\n"
                        + "                                    </td>\n"
                        + "                                    <td>\n"
                        + "                                        <a href=\"http://www.twitter.com/MastersRoyaleC\">\n"
                        + "                                            <img src=\"cid:twitter2\" alt=\"twitter\" style=\"widht:100px;\">\n"
                        + "                                        </a>\n"
                        + "                                    </td>\n"
                        + "                                    <td><b>MASTERS ROYALE CASINO</b><br />mastersroyale@ibfcolombia.com<br /><a href=\"http://www.mastersroyale.com\">mastersroyale.com</a></td>\n"
                        + "                                </tr>\n"
                        + "                            </table>\n"
                        + "\n"
                        + "\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td align=\"center\" colspan=\"2\">\n"
                        + "                            Usted recibe este email porque est&aacute; registrado en nuestra base de datos. Si usted ha recibido informaci&oacute;n no deseada o no requerida, a trav&eacute;s de nuestros servicios, por favor envíenos una copia de ese email con sus comentarios a <a href=\"mailto:comunicaciones@ibfcolombia.com\" target=\"_top\">comunicaciones@ibfcolombia.com</a> para nuestra revisi&oacute;n. Nosotros solucionaremos la situaci&oacute;n inmediatamente. No responda a este mensaje ya que ha sido generado autom&aacute;ticamente para su informaci&oacute;n.\n"
                        + "                            <br />\n"
                        + "                            Cartagena de Indias - Barranquilla - Colombia\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                </tbody>\n"
                        + "            </table>\n"
                        + "\n"
                        + "\n"
                        + "        </div>";
            }
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);
            if (!nombre.equals("noimage")) {

                client = new FTPClient();
                byte[] bytesArray = null;
                byte[] facebook = null;
                byte[] twitter = null;

                String remoteFile2 = nombre;

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

                InputStream inputStream = client.retrieveFileStream(remoteFile2);
                bytesArray = IOUtils.toByteArray(inputStream);

                boolean success = client.completePendingCommand();
                if (success) {
                    System.out.println("File has been downloaded successfully.");
                }
                inputStream.close();

                InputStream fstream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/facebook_1.png");
                facebook = IOUtils.toByteArray(fstream);
                fstream.close();
                InputStream tstream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/twitter_1.png");
                twitter = IOUtils.toByteArray(tstream);
                tstream.close();
                // second part (the image)
                messageBodyPart = new MimeBodyPart();
                DataSource fds = new ByteArrayDataSource(bytesArray, new MimetypesFileTypeMap().getContentType(nombre));
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "<image>");

                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();
                DataSource fds2 = new ByteArrayDataSource(facebook, new MimetypesFileTypeMap().getContentType("facebook_1.png"));

                messageBodyPart.setDataHandler(new DataHandler(fds2));
                messageBodyPart.setHeader("Content-ID", "<facebook1>");
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
                DataSource fds3 = new ByteArrayDataSource(facebook, new MimetypesFileTypeMap().getContentType("facebook_1.png"));

                messageBodyPart.setDataHandler(new DataHandler(fds3));
                messageBodyPart.setHeader("Content-ID", "<facebook2>");
                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();
                DataSource fds4 = new ByteArrayDataSource(twitter, new MimetypesFileTypeMap().getContentType("twitter_1.png"));

                messageBodyPart.setDataHandler(new DataHandler(fds4));
                messageBodyPart.setHeader("Content-ID", "<twitter1>");
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
                DataSource fds5 = new ByteArrayDataSource(twitter, new MimetypesFileTypeMap().getContentType("twitter_1.png"));

                messageBodyPart.setDataHandler(new DataHandler(fds5));
                messageBodyPart.setHeader("Content-ID", "<twitter2>");
                multipart.addBodyPart(messageBodyPart);
            }
            // add image to the multipart

            // put everything together
            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {

            System.out.println(ex);
        } finally {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException ex) {

                System.out.println(ex);
            }
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setProtocol(String protocol) {
        this.protocol = Protocol.valueOf(protocol);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    void sendEmailNotificador(String to, String subject, String mesaje) throws MessagingException, IOException {
        if (to != null) {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port + "");
            switch (protocol) {
                case SMTPS:
                    props.put("mail.smtp.ssl.enable", true);
                    break;
                case POP3:
                case IMAP:
                case SMTP:
                case TLS:
                    props.put("mail.smtp.starttls.enable", true);
                    break;
            }

            Authenticator authenticator = null;
            if (auth) {
                props.put("mail.smtp.auth", "true");
                authenticator = new Authenticator() {
                    private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return pa;
                    }
                };
            }
            Session session = Session.getInstance(props, authenticator);
            session.setDebug(debug);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<div><span style=\"color:#1f497d\">Este correo ha sido generado automáticamente por el Sistema de Gestión de Mercadeo de Inversiones Buena fortuna S.A.S.<u></u><u></u></span>"
                    + "<p>" + mesaje + "</p>";

            htmlText += "<p class=\"MsoNormal\"><span style=\"color:black\"><img src=\"cid:image\"></span></p>";

            htmlText += "<p class=\"MsoNormal\"><span lang=\"ES\" style=\"font-size:10.0pt;font-family:&quot;Arial&quot;,&quot;sans-serif&quot;;color:#7f7f7f\">Nota: El contenido de este mensaje de datos es confidencial y se entiende dirigido y para uso exclusivo del destinatario, por lo que no podrá distribuirse y/o difundirse por ningún medio sin la previa autorización del emisor original. Si usted no es el destinatario, se le prohíbe su utilización total o parcial para cualquier fin.<u></u><u></u></span></p>"
                    + "</div></div>";
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            InputStream iStream = new URL("http://regalos.ibfcolombia.com/mastersemail.png").openStream();
            byte[] bytesArray = IOUtils.toByteArray(iStream);
            // second part (the image)
            messageBodyPart = new MimeBodyPart();

            DataSource fds = new ByteArrayDataSource(bytesArray, new MimetypesFileTypeMap().getContentType("IBFImage.png"));

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            Transport.send(message);
        }
    }
}
