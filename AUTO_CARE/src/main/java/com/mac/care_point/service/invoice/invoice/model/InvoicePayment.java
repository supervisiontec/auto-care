/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.invoice.invoice.model;

import com.mac.care_point.service.job_card.model.JobCardMix;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Kavish Manjitha
 */
public class InvoicePayment implements Serializable {

    private TInvoice invoice;
    private TPayment payment;
    private List<TPaymentInformation> paymentInformationsList;
    private JobCardMix jobCardMix;

    public InvoicePayment() {
    }

    public InvoicePayment(TInvoice invoice, TPayment payment, List<TPaymentInformation> paymentInformationsList, JobCardMix jobCardMix) {
        this.invoice = invoice;
        this.payment = payment;
        this.paymentInformationsList = paymentInformationsList;
        this.jobCardMix = jobCardMix;
    }

    public JobCardMix getJobCardMix() {
        return jobCardMix;
    }

    public void setJobCardMix(JobCardMix jobCardMix) {
        this.jobCardMix = jobCardMix;
    }

    public TInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(TInvoice invoice) {
        this.invoice = invoice;
    }

    public TPayment getPayment() {
        return payment;
    }

    public void setPayment(TPayment payment) {
        this.payment = payment;
    }

    public List<TPaymentInformation> getPaymentInformationsList() {
        return paymentInformationsList;
    }

    public void setPaymentInformationsList(List<TPaymentInformation> paymentInformationsList) {
        this.paymentInformationsList = paymentInformationsList;
    }

}
