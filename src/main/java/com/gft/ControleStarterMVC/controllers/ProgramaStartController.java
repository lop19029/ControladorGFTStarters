package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.ProgramaStart;
import com.gft.ControleStarterMVC.entities.Tecnologia;
import com.gft.ControleStarterMVC.services.ProgramaStartService;
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
@RequestMapping("admin/programaStart")
public class ProgramaStartController {

    @Autowired
    private ProgramaStartService programaStartService;

    @RequestMapping("/editar")
    public ModelAndView editarProgramaStart(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("programaStart/form.html");

        ProgramaStart programaStart;

        //CREATE ou UPDATE?
        if(id == null) {
            programaStart = new ProgramaStart();
        } else {
            try {
                programaStart = programaStartService.obterProgramaStart(id);
            } catch (Exception e) {
                programaStart = new ProgramaStart();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("programaStart", programaStart);

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarProgramaStart(@Valid ProgramaStart programaStart, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("programaStart/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("programaStart", programaStart);
            return mv;
        }

        if(programaStart.getId()==null) {
            mv.addObject("programaStart", new ProgramaStart());
        } else {
            mv.addObject("programaStart", programaStart);
        }

        programaStartService.salvarProgramaStart(programaStart);
        mv.addObject("mensagem", String.format("%s salvo com sucesso.", programaStart.getNome()));
        return mv;
    }

    @RequestMapping
    public ModelAndView listarProgramasStart() {
        ModelAndView mv = new ModelAndView("programaStart/list.html");
        mv.addObject("lista", programaStartService.listarTodosOsProgramasStart());
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarProgramaStart(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/admin/programaStart");
        try{
            programaStartService.deletarProgramaStart(id);
            redirectAttributes.addFlashAttribute("mensagem", "Programa Start excluída com sucesso.");

        } catch (Exception e){

            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }
}
