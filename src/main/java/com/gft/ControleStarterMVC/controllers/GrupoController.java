package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.Grupo;
import com.gft.ControleStarterMVC.entities.Modulo;
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
@RequestMapping("admin/grupo")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProgramaStartService programaStartService;

    @Autowired
    private TecnologiaService tecnologiaService;

    @RequestMapping("/editar")
    public ModelAndView editarGrupo(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("grupo/form.html");

        Grupo grupo;

        //CREATE ou UPDATE?
        if(id == null) {
            grupo = new Grupo();
        } else {
            try {
                grupo = grupoService.obterGrupo(id);
            } catch (Exception e) {
                grupo = new Grupo();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("grupo", grupo);
        mv.addObject("listaScrums", userService.listarTodosOsSrumMasters());
        mv.addObject("listaProgramas", programaStartService.listarTodosOsProgramasStart());
        mv.addObject("listaTecnologias", tecnologiaService.listarTodasAsTecnologias());

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("grupo/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("grupo", grupo);
            mv.addObject("listaScrums", userService.listarTodosOsSrumMasters());
            mv.addObject("listaProgramas", programaStartService.listarTodosOsProgramasStart());
            mv.addObject("listaTecnologias", tecnologiaService.listarTodasAsTecnologias());
            return mv;
        }

        if(grupo.getId()==null) {
            mv.addObject("grupo", new Grupo());
        } else {
            mv.addObject("grupo", grupo);
        }

        grupoService.salvarGrupo(grupo);
        mv.addObject("mensagem", String.format("Grupo %d salvo com sucesso.", grupo.getId()));
        mv.addObject("listaScrums", userService.listarTodosOsSrumMasters());
        mv.addObject("listaProgramas", programaStartService.listarTodosOsProgramasStart());
        mv.addObject("listaTecnologias", tecnologiaService.listarTodasAsTecnologias());
        return mv;
    }

    @RequestMapping
    public ModelAndView listarGrupos() {
        ModelAndView mv = new ModelAndView("grupo/list.html");
        mv.addObject("lista", grupoService.listarTodosOsGrupos());
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarGrupo(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/admin/grupo");

        Grupo grupo;
        try {
            grupoService.deletarGrupo(id);
            redirectAttributes.addFlashAttribute("mensagem", "Grupo excluído com sucesso.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }
}
