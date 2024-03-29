package ru.pcs.graduatework.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.graduatework.dto.ClientDto;
import ru.pcs.graduatework.dto.ClientPortfolioDto;
import ru.pcs.graduatework.forms.ClientForm;
import ru.pcs.graduatework.service.ClientsService;
import ru.pcs.graduatework.service.StocksService;

import java.util.List;

@Controller
@RequestMapping("/portfolio/{client-id}/edit")
@RequiredArgsConstructor
public class EditClientController {

    private final ClientsService clientsService;
    private final StocksService stocksService;

    @GetMapping
    public String editClientPage(Model model, @PathVariable("client-id") Integer clientId) {
        ClientDto clientDto = clientsService.getClient(clientId);
        model.addAttribute("client", clientDto);
        model.addAttribute("isLoginUnique", true);
//        model.addAttribute("clientForm", new ClientForm());
        return "personal-edit-delete";
    }

    @PostMapping("/update")
    public String editClient(Model model, @PathVariable("client-id") Integer clientId, ClientForm form) {
//    public String editClient(Model model, @PathVariable("client-id") Integer clientId, @Valid ClientForm form, BindingResult bindingResult) {
        ClientDto clientDto = clientsService.getClient(clientId);

//        if (bindingResult.hasErrors()) {
//            model.addAttribute("client", client);
//            model.addAttribute("clientForm", form);
//            return "personal-edit-delete";
//        }
        if (!clientsService.isLoginUnique(form.getLogin())) {
            model.addAttribute("client", clientDto);
            model.addAttribute("clientForm", form);
            model.addAttribute("isLoginUnique", false);
            return "personal-edit-delete";
        }
        clientsService.editClient(clientId, form);
        List<ClientPortfolioDto> portfolio = stocksService.getPortfolioInformation(clientDto.getId());
        model.addAttribute("client", clientDto);
        model.addAttribute("portfolio", portfolio);
        if (portfolio.size() > 0) {
            return "personal-portfolioPage";
        } else {
            return "personal-portfolioPage-stocksZero";
        }
    }

    @PostMapping("/delete")
    public String removeClient(@PathVariable("client-id") Integer clientId) {
        clientsService.deleteClient(clientId);
        return "redirect:/welcome.html";
    }

}

