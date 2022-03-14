package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.Tecnologia;
import com.gft.ControleStarterMVC.services.TecnologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/tecnologia")
public class TecnologiaController {

    @Autowired
    private TecnologiaService tecnologiaService;

    @RequestMapping("/editar")
    public ModelAndView editarTecnologia(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("tecnologia/form.html");

        Tecnologia tecnologia;

        //CREATE ou UPDATE?
        if(id == null) {
            tecnologia = new Tecnologia();
        } else {
            try {
                tecnologia = tecnologiaService.obterTecnologia(id);
            } catch (Exception e) {
                tecnologia = new Tecnologia();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("tecnologia", tecnologia);

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarTecnologia(@Valid Tecnologia tecnologia, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("tecnologia/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("tecnologia", tecnologia);
            return mv;
        }

        if(tecnologia.getId()==null) {
            mv.addObject("tecnologia", new Tecnologia());
        } else {
            mv.addObject("tecnologia", tecnologia);
        }

        tecnologiaService.salvarTecnologia(tecnologia);
        mv.addObject("mensagem", String.format("%s salvo com sucesso.", tecnologia.getNome()));
        return mv;
    }

    @RequestMapping
    public ModelAndView listarTecnologias() {
        ModelAndView mv = new ModelAndView("tecnologia/list.html");
        mv.addObject("lista", tecnologiaService.listarTodasAsTecnologias());
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarTecnologia(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/admin/tecnologia");
        try{
            tecnologiaService.deletarTecnologia(id);
            redirectAttributes.addFlashAttribute("mensagem", "Tecnologia excluída com sucesso.");

        } catch (Exception e){

            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }
}
