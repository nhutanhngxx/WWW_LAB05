package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.services.JobService;

import java.util.List;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
}
