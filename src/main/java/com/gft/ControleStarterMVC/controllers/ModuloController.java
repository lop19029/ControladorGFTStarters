package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.Modulo;
import com.gft.ControleStarterMVC.entities.Starter;
import com.gft.ControleStarterMVC.repositories.StarterRepository;
import com.gft.ControleStarterMVC.services.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("admin/modulo")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @RequestMapping("/editar")
    public ModelAndView editarModulo(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("modulo/form.html");

        Modulo modulo;

        //CREATE ou UPDATE?
        if(id == null) {
            modulo = new Modulo();
        } else {
            try {
                modulo = moduloService.obterModulo(id);
            } catch (Exception e) {
                modulo = new Modulo();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("modulo", modulo);

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarModulo(@Valid Modulo modulo, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("modulo/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("modulo", modulo);
            return mv;
        }

        if(modulo.getId()==null) {
            mv.addObject("modulo", new Modulo());
        } else {
            mv.addObject("modulo", modulo);
        }

        moduloService.salvarModulo(modulo);
        mv.addObject("mensagem", String.format("%s salvo com sucesso.", modulo.getNome()));
        return mv;
    }

    @RequestMapping
    public ModelAndView listarModulos() {
        ModelAndView mv = new ModelAndView("modulo/list.html");
        mv.addObject("lista", moduloService.listarTodosOsModulos());
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarModulo(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/admin/modulo");

        Modulo modulo;
        try {
            modulo = moduloService.obterModulo(id);
            //modulo.setStarters(null); //Avoid starters from being removed when deleting modulo
            moduloService.deletarModulo(modulo);
            redirectAttributes.addFlashAttribute("mensagem", "Modulo excluído com sucesso.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }
}
