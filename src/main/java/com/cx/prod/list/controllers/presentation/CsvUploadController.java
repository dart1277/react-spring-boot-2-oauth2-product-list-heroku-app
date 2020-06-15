package com.cx.prod.list.controllers.presentation;

import com.cx.prod.list.model.users.User;
import com.cx.prod.list.services.users.UserCsvService;
import com.cx.prod.list.services.users.UserDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/csv")
public class CsvUploadController {

    private static final String FILE_VALIDATION_ERROR = "fileValidationError";
    private static final String PRESENTATION_CSV_UPLOAD = "presentation/csv_upload";
    private static final String CSV_FILE_POST_REDIRECT_PATH = "redirect:/admin/csv/search";

    private Logger logger = LoggerFactory.getLogger(CsvUploadController.class);

    @Autowired
    private UserCsvService userCsvService;

    @Autowired
    private UserDaoService userDaoService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadCsvForm() {
        return "presentation/csv_upload";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String uploadCsvFile(@RequestParam("file") MultipartFile multipartFile, Model model) {
        String redirectPath = CSV_FILE_POST_REDIRECT_PATH;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            redirectPath = handleCsvFileUploadRequest(multipartFile, model, redirectPath);
        } else {
            redirectPath = handleEmptyOrNullFileUpload(model);
        }
        return redirectPath;
    }

    private String handleCsvFileUploadRequest(@RequestParam("file") MultipartFile multipartFile, Model model, String redirectPath) {
        String fileName = multipartFile.getOriginalFilename();
        try (InputStreamReader reader = new InputStreamReader(multipartFile.getInputStream())) {
            processCsvFile(reader);
            logger.info("Processed multipart file: " + fileName);
        } catch (Exception e) {
            redirectPath = handleFileUploadError(model, e);
        }
        return redirectPath;
    }

    private void processCsvFile(InputStreamReader reader) throws IOException {
        List<User> users = userCsvService.parseCsvFile(reader);
        userDaoService.mergeUsers(users);
    }

    private String handleFileUploadError(Model model, Exception e) {
        logger.error("Multipart file upload failed " + e.getMessage());
        model.addAttribute(FILE_VALIDATION_ERROR, "File cannot be parsed");
        return PRESENTATION_CSV_UPLOAD;
    }

    private String handleEmptyOrNullFileUpload(Model model) {
        logger.debug("Multipart file is null");
        model.addAttribute(FILE_VALIDATION_ERROR, "File is not selected");
        return PRESENTATION_CSV_UPLOAD;
    }


}
