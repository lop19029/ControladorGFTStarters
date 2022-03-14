package com.gft.ControleStarterMVC.controllers;

import com.gft.ControleStarterMVC.entities.Starter;
import com.gft.ControleStarterMVC.services.GrupoService;
import com.gft.ControleStarterMVC.services.StarterService;
import com.gft.ControleStarterMVC.services.TecnologiaService;
import com.gft.ControleStarterMVC.utils.FileUploadUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RestController
@RequestMapping("admin/starter")
public class StarterController {

    @Autowired
    private StarterService starterService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private TecnologiaService tecnologiaService; //For filters

    @RequestMapping("/editar")
    public ModelAndView editarStarter(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("starter/form.html");

        Starter starter;

        //CREATE ou UPDATE?
        if(id == null) {
            starter = new Starter();
        } else {
            try {
                starter = starterService.obterStarter(id);
            } catch (Exception e) {
                starter = new Starter();
                mv.addObject("mensagem", e.getMessage());
            }
        }
        mv.addObject("starter", starter);
        mv.addObject("listaGrupos", grupoService.listarTodosOsGrupos());

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "editar")
    public ModelAndView salvarStarter(
            @Valid Starter starter,
            BindingResult bindingResult,
            @RequestParam(name = "imagem", required = false) MultipartFile multipartFile)
            throws IOException {

        ModelAndView mv = new ModelAndView("starter/form.html");

        if(bindingResult.hasErrors()) {
            mv.addObject("starter", starter);
            mv.addObject("listaGrupos", grupoService.listarTodosOsGrupos());
            return mv;
        }

        //CREATE or EDIT
        if(starter.getId()==null) {
            mv.addObject("starter", new Starter());
        } else {
            mv.addObject("starter", starter);
        }

        //Setup nota on create
        if(starter.getNota()==null){
            starter.setNota(0);
        }

        Starter starterSalvo = starterService.salvarStarter(starter);

        //Upload foto
        //Edit foto if the user uploaded anything
        //Set default if upload is empty and foto is empty
        //Don't do anything if foto not empty and user didn't upload anything
        if (!(multipartFile.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            starterSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "starter-fotos/" + starterSalvo.getId();
            FileUploadUtil.saveFile(pastaDeFotos, nomeDoArquivo, multipartFile);
            starterService.salvarStarter(starterSalvo);
        }
        else if(starterSalvo.getFoto().isEmpty()) {
            //Set default image
            starterSalvo.setFoto("starter-default.png");
            starterService.salvarStarter(starterSalvo);
        }


        mv.addObject("mensagem", String.format("%s salvo com sucesso.", starter.getNome()));
        mv.addObject("listaGrupos", grupoService.listarTodosOsGrupos());

        return mv;
    }

    @RequestMapping
    public ModelAndView listarStarters() {
        ModelAndView mv = new ModelAndView("starter/list.html");
        mv.addObject("lista", starterService.listarTodosOsStarters());
        mv.addObject("listaTecnologias", tecnologiaService.listarTodasAsTecnologias());
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/json")
    public List<Starter> listarStartersJson(String nome, Long tecnologiaId) {
        byte[] decodedBytes = Base64.getDecoder().decode(nome);
        String nameConvert = new String(decodedBytes);
        return starterService.listarStartersFiltrados(nameConvert, tecnologiaId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/json/topStarters")
    public List<Starter> listarTopStartersJson(String nome, Long tecnologiaId) {
        byte[] decodedBytes = Base64.getDecoder().decode(nome);
        String nameConvert = new String(decodedBytes);

        //Get starters grades
        LinkedHashMap<Long,Integer> startersENotaMap = new LinkedHashMap<>();
        for (Starter s:
                starterService.listarStartersFiltrados(nameConvert, tecnologiaId)) {
            startersENotaMap.put(s.getId(),s.getNota());
        }

        //Organize starters according to their grades

        List<Map.Entry<Long, Integer> > startersENotaLista = new ArrayList<>(startersENotaMap.entrySet());

        Collections.sort(
                startersENotaLista,
                new Comparator<Map.Entry<Long, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                        return o2.getValue() - o1.getValue();
                    }
                }
        );

        // Create Top starters array
        List<Starter> topStarters = new ArrayList<>();

        for (Map.Entry<Long, Integer> l : startersENotaLista){
            try {
                Starter s = starterService.obterStarter(l.getKey());
                topStarters.add(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return topStarters;
    }

    @RequestMapping("/topStarters")
    public ModelAndView listarTopStarters(){

        ModelAndView mv = new ModelAndView("starter/list-topStarters.html");

        //Get starters grades
        LinkedHashMap<Long,Integer> startersENotaMap = new LinkedHashMap<>();
        for (Starter s:
                starterService.listarTodosOsStarters()) {
            startersENotaMap.put(s.getId(),s.getNota());
        }

        //Organize starters according to their grades

        List<Map.Entry<Long, Integer> > startersENotaLista = new ArrayList<>(startersENotaMap.entrySet());

        Collections.sort(
                startersENotaLista,
                new Comparator<Map.Entry<Long, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                        return o2.getValue() - o1.getValue();
                    }
                }
        );

        // Create Top starters array
        List<Starter> topStarters = new ArrayList<>();

        for (Map.Entry<Long, Integer> l : startersENotaLista){
            try {
                Starter s = starterService.obterStarter(l.getKey());
                topStarters.add(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mv.addObject("lista", topStarters);
        mv.addObject("listaTecnologias", tecnologiaService.listarTodasAsTecnologias());
        return mv;
    }

    @RequestMapping("/info")
    public ModelAndView verInformacoesDoStarter(@RequestParam Long id) {
        ModelAndView mv = new ModelAndView("starter/info.html");
        Starter starter;
        try {
            starter = starterService.obterStarter(id);

        } catch (Exception e) {

            starter = new Starter();
            mv.addObject("mensagem", e.getMessage());

        }
        mv.addObject("starter", starter);
        return mv;
    }

    @RequestMapping("/deletar")
    public ModelAndView deletarStarter(@RequestParam Long id, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView mv = new ModelAndView("redirect:/admin/starter");

        Starter starterToDelete = starterService.obterStarter(id);

        //delete starter foto directory if not default
        if(!starterToDelete.getFoto().equals("starter-default.png")){
            String path = "starter-fotos/" + id+ "/" + starterToDelete.getFoto();
            FileUtils.forceDelete(new File(path));
            FileUtils.deleteDirectory(new File("starter-fotos/" + id));
        }

        try {
            starterService.deletarStarter(id);
            redirectAttributes.addFlashAttribute("mensagem", "Starter excluído com sucesso.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Ups! Algo deu errado: "+ e.getMessage());
        }

        return mv;
    }
}
