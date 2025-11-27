package de.tim.ipwa0201.ghostnet.web;

import de.tim.ipwa0201.ghostnet.service.GhostNetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class GhostNetController {

    private final GhostNetService ghostNetService;

    public GhostNetController(GhostNetService ghostNetService) {
        this.ghostNetService = ghostNetService;
    }

    @GetMapping("/ghostnets")
    public String list(Model model) {
        model.addAttribute("nets", ghostNetService.findAll());
        return "ghostnets-list";
    }

    @GetMapping("/ghostnets/open")
    public String listOpen(Model model) {
        model.addAttribute("nets", ghostNetService.findOpenNets());
        return "ghostnets-open";
    }

    @GetMapping("/ghostnets/new")
    public String showCreateForm(Model model) {
        GhostNetCreateForm form = new GhostNetCreateForm();
        form.setAnonymous(true);
        model.addAttribute("form", form);
        return "ghostnets-create";
    }

    @PostMapping("/ghostnets")
    public String create(@ModelAttribute("form") GhostNetCreateForm form,
                         BindingResult bindingResult,
                         Model model) {

        if (form.getLatitude() < -90 || form.getLatitude() > 90) {
            bindingResult.rejectValue("latitude", "lat.invalid", "Breitengrad muss zwischen −90 und +90 liegen");
        }

        if (form.getLongitude() < -180 || form.getLongitude() > 180) {
            bindingResult.rejectValue("longitude", "lon.invalid", "Längengrad muss zwischen −180 und +180 liegen");
        }

        if (form.getSizeEstimate() == null || form.getSizeEstimate().isBlank()) {
            bindingResult.rejectValue("sizeEstimate", "size.empty", "Größenangabe darf nicht leer sein");
        }

        if (!form.isAnonymous()) {
            if (form.getReporterName() == null || form.getReporterName().isBlank()) {
                bindingResult.rejectValue("reporterName", "name.empty", "Name ist erforderlich, wenn nicht anonym gemeldet wird");
            }
        }

        if (bindingResult.hasErrors()) {
            return "ghostnets-create";
        }

        ghostNetService.createNet(
                form.getLatitude(),
                form.getLongitude(),
                form.getSizeEstimate(),
                form.isAnonymous(),
                form.getReporterName(),
                form.getPhoneNumber()
        );

        return "redirect:/ghostnets";
    }

    @GetMapping("/ghostnets/{id}/assign")
    public String showAssignForm(@PathVariable Long id, Model model) {
        GhostNetAssignForm form = new GhostNetAssignForm();
        model.addAttribute("form", form);
        model.addAttribute("netId", id);
        return "ghostnets-assign";
    }

    @PostMapping("/ghostnets/{id}/assign")
    public String assign(@PathVariable Long id,
                         @ModelAttribute("form") GhostNetAssignForm form,
                         BindingResult bindingResult,
                         Model model) {

        if (form.getName() == null || form.getName().isBlank()) {
            bindingResult.rejectValue("name", "name.empty", "Name darf nicht leer sein");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("netId", id);
            return "ghostnets-assign";
        }

        ghostNetService.assignSalvager(id, form.getName(), form.getPhoneNumber());

        return "redirect:/ghostnets";
    }

    @GetMapping("/ghostnets/{id}/recover")
    public String showRecoverForm(@PathVariable Long id, Model model) {
        model.addAttribute("netId", id);
        return "ghostnets-recover";
    }

    @PostMapping("/ghostnets/{id}/recover")
    public String recover(@PathVariable Long id) {
        ghostNetService.markAsRecovered(id);
        return "redirect:/ghostnets";
    }

    @GetMapping("/ghostnets/{id}/lost")
    public String showLostForm(@PathVariable Long id, Model model) {
        GhostNetLostForm form = new GhostNetLostForm();
        model.addAttribute("form", form);
        model.addAttribute("netId", id);
        return "ghostnets-lost";
    }

    @PostMapping("/ghostnets/{id}/lost")
    public String lost(@PathVariable Long id,
                       @ModelAttribute("form") GhostNetLostForm form,
                       BindingResult bindingResult,
                       Model model) {

        if (form.getName() == null || form.getName().isBlank()) {
            bindingResult.rejectValue("name", "name.empty", "Name darf nicht leer sein");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("netId", id);
            return "ghostnets-lost";
        }

        ghostNetService.markAsLost(id, form.getName(), form.getPhoneNumber());

        return "redirect:/ghostnets";
    }
}
