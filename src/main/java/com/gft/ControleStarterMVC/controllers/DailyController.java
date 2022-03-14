package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.Daily;
import com.gft.ControleStarterMVC.entities.Grupo;
import com.gft.ControleStarterMVC.entities.Starter;
import com.gft.ControleStarterMVC.entities.User;
import com.gft.ControleStarterMVC.enums.NivelDeAcesso;
import com.gft.ControleStarterMVC.repositories.UserRepository;
import com.gft.ControleStarterMVC.security.UserPrincipalDetailsService;
import com.gft.ControleStarterMVC.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModuloService moduloService;

    @RequestMapping("/editar")
    public ModelAndView editarDaily(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("daily/form.html");

        //Create daily
        Daily daily;

        //CREATE ou UPDATE?
        if(id == null) {
            daily = new Daily();
        } else {
            try {
                daily = dailyService.obterDaily(id);
            } catch (Exception e) {
                daily = new Daily();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("daily", daily);
        mv.addObject("listaGrupos", listarGruposDoUsuarioLogado());
        mv.addObject("listaModulos", moduloService.listarTodosOsModulos());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarDaily(@Valid Daily daily, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("daily/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("daily", daily);
            mv.addObject("listaGrupos", listarGruposDoUsuarioLogado());
            mv.addObject("listaModulos", moduloService.listarTodosOsModulos());
            return mv;
        }

        if(daily.getId()==null) {
            mv.addObject("daily", new Daily());
        } else {
            mv.addObject("daily", daily);
        }

        dailyService.salvarDaily(daily);
        mv.addObject("mensagem", "Daily salva com sucesso.");
        mv.addObject("listaGrupos", listarGruposDoUsuarioLogado());
        mv.addObject("listaModulos", moduloService.listarTodosOsModulos());

        return mv;
    }

    @RequestMapping
    public ModelAndView listarDailies() {
        ModelAndView mv = new ModelAndView("daily/list.html");
        mv.addObject("listaGrupos", listarGruposDoUsuarioLogado());
        return mv;
    }

    @RequestMapping("/info")
    public ModelAndView verDetalhesDaDaily(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("daily/info.html");
        Daily daily;
        try {
            daily = dailyService.obterDaily(id);

        } catch (Exception e) {

            daily = new Daily();
            mv.addObject("mensagem", e.getMessage());
        }
        mv.addObject("daily", daily);
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarDaily(@RequestParam Long id, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/daily");

        Daily daily;
        try {
            dailyService.deletarDaily(id);
            redirectAttributes.addFlashAttribute("mensagem", "Daily excluída com sucesso.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }

    private User obterUsuarioLogado(){

        //Who's logged in?
        String nomeDoUsuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        User usuarioLogado = userService.obterUsuarioPorNomeDeUsuario(nomeDoUsuarioLogado);

        return usuarioLogado;
    }

    //Lists all groups if user has FULL access. If RESTRICTED access, returns this.user.groups
    private List<Grupo> listarGruposDoUsuarioLogado(){

        User usuarioLogado = obterUsuarioLogado();

        List<Grupo> listaGrupos;

        //Check user access level
        if(usuarioLogado.getNivelDeAcesso().equals(NivelDeAcesso.RESTRICTED)){
            listaGrupos = new ArrayList<>();
            listaGrupos.addAll(userService.listarGruposDeScrumMaster(usuarioLogado));
        }
        else {
            listaGrupos = grupoService.listarTodosOsGrupos();
        }

        return listaGrupos;
    }
}
