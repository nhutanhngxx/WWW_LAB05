package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.iuh.fit.backend.models.Address;
import vn.com.iuh.fit.backend.models.Candidate;
import vn.com.iuh.fit.backend.repositories.AddressRepository;
import vn.com.iuh.fit.backend.repositories.CandidatesRepository;
import vn.com.iuh.fit.backend.services.CandidatesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CandidateController {
    @Autowired
    private CandidatesRepository candidatesRepository;
    @Autowired
    private CandidatesService candidateServices;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CandidatesService candidatesService;

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidatesRepository.findAll());
        return "candidates/candidates";
    }

    @GetMapping("/candidates")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Candidate> candidatePage= candidateServices.findAll(currentPage - 1,pageSize,"id","asc");
        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidates/candidates-paging";
    }

    @RequestMapping("/add-candidate")
    public String showAddCandidateForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "candidates/add-candidate";
    }

    @PostMapping("/add-candidate")
    public String addCandidate(@Validated @ModelAttribute("candidate") Candidate candidate, @ModelAttribute("address") Address address,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "candidates/add-candidate";
        }
        addressRepository.save(address);
        candidate.setAddress(address);
        candidateServices.save(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/update-candidates/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Candidate candidate = candidateServices.getCandidateById(id);
        if (candidate == null) {
            return "redirect:/candidates";
        }
        model.addAttribute("candidate", candidate);
        return "candidates/update-candidates";
    }

    @PostMapping("/update-candidates")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        Candidate existingCandidate = candidateServices.getCandidateById(candidate.getId());
        if (existingCandidate != null) {
            existingCandidate.setFullName(candidate.getFullName());
            existingCandidate.setDob(candidate.getDob());
            existingCandidate.setEmail(candidate.getEmail());
            existingCandidate.setPhone(candidate.getPhone());
            if (candidate.getAddress() != null) {
                existingCandidate.setAddress(candidate.getAddress());
                addressRepository.save(candidate.getAddress());
            }
            candidateServices.updateCandidate(existingCandidate);
        }
        return "redirect:/candidates";
    }

    @GetMapping("/search-candidates")
    public String searchCandidates(@RequestParam("name") String name, Model model) {
        List<Candidate> candidates = candidatesService.findByName(name);
        model.addAttribute("candidates", candidates);
        return "candidates/candidates";
    }

}
