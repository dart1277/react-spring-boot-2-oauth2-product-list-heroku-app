package com.cx.prod.list.controllers.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/csv")
public class CsvSearchController {

    private Logger logger = LoggerFactory.getLogger(CsvSearchController.class);

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String uploadCsvForm() {
        return "presentation/csv_search";
    }

}
