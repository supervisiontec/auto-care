/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_card;

import com.mac.care_point.service.job_card.model.JobCard;
import com.mac.care_point.service.job_card.model.JobCardMix;
import com.mac.care_point.service.job_card.model.THistoryMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Kavish Manjitha
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/job-card")
public class JobCardController {

    @Autowired
    private JobCardService jobCardService;

    public static final String IMAGE_LOCATION = "./upload-image";
    public static final String IMAGE_NAME_FILTER_TEMPLATE = "job-no-%s";
    public static final String IMAGE_NAME_TEMPLATE = "job-no-%s-%s.jpg";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-S");

    @RequestMapping(value = "/get-client-history/{indexNo}", method = RequestMethod.GET)
    public List<JobCard> getClientHistory(@PathVariable Integer indexNo) {
        return jobCardService.getClientHistory(indexNo);
    }

    @RequestMapping(value = "/get-job-card/{indexNo}", method = RequestMethod.GET)
    public JobCard getJobCard(@PathVariable Integer indexNo) {
        return jobCardService.getJobCard(indexNo);
    }

    @RequestMapping(value = "/get-job-detail-by-vehicle-no/{VehicleNo}", method = RequestMethod.GET)
    public List<JobCard> getJobCardByVehicleNo(@PathVariable String VehicleNo) {
        return jobCardService.getJobCardByVehicleNo(VehicleNo);
    }

    @RequestMapping(value = "/get-invoice-pending-job-card", method = RequestMethod.GET)
    public List<JobCard> findByBranchAndStatusAndInvoiceOrderByIndexNoDesc() {
        return jobCardService.findByBranchAndStatusAndInvoiceOrderByIndexNoDesc(SecurityUtil.getCurrentUser().getBranch());
    }
    @RequestMapping(value = "/get-invoice-pending-job-card2", method = RequestMethod.GET)
    public List<JobCardMix> devideJobCard() {
        return jobCardService.devideJobCard(SecurityUtil.getCurrentUser().getBranch());
    }

    @RequestMapping(value = "/get-default-check-list-pending-job-card", method = RequestMethod.GET)
    public List<JobCard> findByBranchAndStatusAndDefaultFinalCheckOrderByIndexNoDesc() {
        return jobCardService.findByBranchAndStatusAndDefaultFinalCheckOrderByIndexNoDesc(SecurityUtil.getCurrentUser().getBranch());
    }

    @RequestMapping(value = "/get-service-and-stock-pending-job-card", method = RequestMethod.GET)
    public List<JobCard> findByBranchAndStatusAndInvoiceAndDefaultFinalCheckOrderByIndexNoDesc() {
        return jobCardService.findByBranchAndStatusAndInvoiceAndDefaultFinalCheckOrderByIndexNoDesc(SecurityUtil.getCurrentUser().getBranch());
    }

    @RequestMapping(value = "/get-not-finished-job-cards", method = RequestMethod.GET)
    public List<JobCard> getNotFinishedJobCard() {
        return jobCardService.getNotFinishedJobCard(SecurityUtil.getCurrentUser().getBranch());
    }

    @RequestMapping(value = "/save-job-card", method = RequestMethod.POST)
    public Integer saveJovCard(@RequestBody JobCard jobCard) {
        jobCard.setBranch(SecurityUtil.getCurrentUser().getBranch());
        return jobCardService.saveJobCard(jobCard).getIndexNo();
    }

    @RequestMapping(value = "/service-charge/{jobCard}/{status}", method = RequestMethod.GET)
    public JobCard setServiceChargers(@PathVariable Integer jobCard, @PathVariable Boolean status) {
        return jobCardService.setServiceChargers(jobCard, status);
    }

    @RequestMapping(value = "/upload-image/{jobCard}/{imageNumber}", method = RequestMethod.POST)
    public void saveImage(@RequestParam("file") MultipartFile file, @PathVariable("jobCard") Integer jobCard, @PathVariable("imageNumber") Integer imageNumber,HttpServletResponse response) throws IOException {
        File uploadFile = new File(IMAGE_LOCATION, String.format(IMAGE_NAME_TEMPLATE, jobCard, imageNumber));
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        ImageIO.write(bufferedImage, "JPG", uploadFile);
    }

    @RequestMapping(value = "/download-image/{fileName:.+}", method = RequestMethod.GET)
    public void downloadImage(@PathVariable String fileName, HttpServletResponse response) throws FileNotFoundException, IOException {
        InputStream inputStream = new FileInputStream(IMAGE_LOCATION + "/" + fileName);
        System.out.println("fileName : "+fileName);
        OutputStream outputStream = response.getOutputStream();

        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) > 0) {
            outputStream.write(bytes);
        }
        outputStream.flush();
    }

    @RequestMapping(value = "/image-names/{jobCard}", method = RequestMethod.GET)
    public List<String> imageName(@PathVariable String jobCard) {
        File imageDir = new File(IMAGE_LOCATION);
        File[] imageFiles = imageDir.listFiles((File pathname) -> {
            return pathname.getName().startsWith(String.format(IMAGE_NAME_FILTER_TEMPLATE, jobCard));
        });

        List<String> imageFileNames = new ArrayList<>();
        for (File imageFile : imageFiles) {
            imageFileNames.add(imageFile.getName());
        }

        return imageFileNames;
    }

    @RequestMapping(value = "/find-job-history/{vehicleNo}", method = RequestMethod.GET)
    public List<JobCard> findJobHistory(@PathVariable("vehicleNo") String vehicleNo) {
        return jobCardService.findJobHistory(vehicleNo);
    }
    @RequestMapping(value = "/get-job-history-for-front/{vehicleNo}", method = RequestMethod.GET)
    public List<THistoryMix> getJobHostory(@PathVariable("vehicleNo") String vehicleNo) {
        return jobCardService.getJobHostory(vehicleNo);
    }

    @RequestMapping(value = "/update-price-category-details/{employee}", method = RequestMethod.POST)
    public JobCard updateJobCardDetailsAndVehicleDetails(@RequestBody JobCard jobCard, @PathVariable Integer employee) {
        return jobCardService.updateJobCardDetailsAndVehicleDetails(jobCard, employee);
    }
}
