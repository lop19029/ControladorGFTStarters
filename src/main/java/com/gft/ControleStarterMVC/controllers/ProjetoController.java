package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.Grupo;
import com.gft.ControleStarterMVC.entities.Projeto;
import com.gft.ControleStarterMVC.entities.Starter;
import com.gft.ControleStarterMVC.services.*;
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
@RequestMapping("admin/projeto")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private StarterService starterService;

    @Autowired
    private ModuloService moduloService;

    @RequestMapping("/editar")
    public ModelAndView editarGrupo(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("projeto/form.html");

        Projeto projeto;

        //CREATE ou UPDATE?
        if(id == null) {
            projeto = new Projeto();
        } else {
            try {
                projeto = projetoService.obterProjeto(id);
            } catch (Exception e) {
                projeto = new Projeto();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("projeto", projeto);
        mv.addObject("listaStarters", starterService.listarTodosOsStarters());
        mv.addObject("listaModulos", moduloService.listarTodosOsModulos());

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarProjeto(@Valid Projeto projeto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("projeto/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("projeto", projeto);
            mv.addObject("listaStarters", starterService.listarTodosOsStarters());
            mv.addObject("listaModulos", moduloService.listarTodosOsModulos());
            return mv;
        }

        if(projeto.getId()==null) {
            mv.addObject("projeto", new Projeto());
        } else {
            mv.addObject("projeto", projeto);
        }

        //Save project
        projetoService.salvarProjeto(projeto);

        //Update starter nota
        try {
            Starter starter = starterService.obterStarter(projeto.getStarter().getId());
            int nota = 0;
            for (Projeto p:
                 starter.getProjetos()) {
                nota += Integer.parseInt(p.getAvaliacao());
            }
            starter.setNota(nota);
            starterService.salvarStarter(starter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.addObject("mensagem", "Projeto salvo com sucesso.");
        mv.addObject("listaStarters", starterService.listarTodosOsStarters());
        mv.addObject("listaModulos", moduloService.listarTodosOsModulos());
        return mv;
    }

    @RequestMapping
    public ModelAndView listarProjetos() {
        ModelAndView mv = new ModelAndView("projeto/list.html");
        mv.addObject("lista", projetoService.listarTodosOsProjetos());
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarProjeto(@RequestParam Long id, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView mv = new ModelAndView("redirect:/admin/projeto");

        Projeto projeto = projetoService.obterProjeto(id);

        //Update starter nota
        try {
            Starter starter = starterService.obterStarter(projeto.getStarter().getId());
            int nota = starter.getNota();
            nota-= Integer.parseInt(projeto.getAvaliacao());
            starter.setNota(nota);
            starterService.salvarStarter(starter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Delete project
        try {
            projetoService.deletarProjeto(id);
            redirectAttributes.addFlashAttribute("mensagem", "Projeto excluído com sucesso.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }
}
