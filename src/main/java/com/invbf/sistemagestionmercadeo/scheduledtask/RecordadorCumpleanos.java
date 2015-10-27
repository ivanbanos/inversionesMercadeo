/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.scheduledtask;

import com.invbf.sistemagestionmercadeo.facade.impl.AdminFacadeImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author ivan
 */
public class RecordadorCumpleanos implements Job {

    @Override
    public void execute(final JobExecutionContext ctx)
            throws JobExecutionException {
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            System.out.println("Enviando correo");
            
        
            new AdminFacadeImpl().enviarCorreoCumpeanos();

    }
}