package com.egg.biblioteca.controladores;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroresControlador implements ErrorController {

    @RequestMapping(value="/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest){

        ModelAndView errorPage = new ModelAndView("error");

        String  errorMsg="";

        Integer httpErrorCode = getErrorCode(httpRequest);

        System.out.println(httpErrorCode);

        switch(httpErrorCode){
            case 400, 404: {
                errorMsg = "El recurso solicitado no existe.";
                break;
            }
            case 403: {
                errorMsg="No tiene permiso para acceder al recurso.";
                break;
            }
            case 401:{
                errorMsg ="No se encuentra autenticado.";
                break;
            }
            case 500: {
                errorMsg = "Error interno del servidor.";
                break;
            }
        }

        errorPage.addObject("codigo",httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;

    }


    private Integer getErrorCode(HttpServletRequest httpRequest) {

        Integer errorCode = (Integer) httpRequest.getAttribute("jakarta.servlet.error.status_code");
        return (errorCode != null) ? errorCode : 500;
    }

    private String getErrorPath(){
        return "error.html";
    }
}
