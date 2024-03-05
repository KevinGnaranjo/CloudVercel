/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kevindev.controller;

import com.kevindev.ejeb.CategoriaFacadeLocal;
import com.kevindev.modelo.Categoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Costagramer
 */
@Named
@ViewScoped
public class CategoriaController implements Serializable {

    @EJB
    private CategoriaFacadeLocal categoriaEJB;
    private Categoria categoria;
    private List<Categoria> categorias;
    String mensaje = "";

    public CategoriaFacadeLocal getCategoriaEJB() {
        return categoriaEJB;
    }

    public void setCategoriaEJB(CategoriaFacadeLocal categoriaEJB) {
        this.categoriaEJB = categoriaEJB;
    }

    @PostConstruct
    public void init() {
        categorias = categoriaEJB.findAll();
        categoria = new Categoria();
        categorias.removeIf(c -> "Inactivo".equals(c.getEstado()));
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void registrar() {
        try {
            categoriaEJB.create(categoria);
            categorias = categoriaEJB.findAll();
            this.categoria = new Categoria();
            categorias.removeIf(c -> "Inactivo".equals(c.getEstado()));
            this.mensaje = "Almacenado con exito";
        } catch (Exception e) {
            e.printStackTrace();
            this.mensaje = "Error " + e.getMessage();
        }
        FacesMessage mens = new FacesMessage(this.mensaje);
        FacesContext.getCurrentInstance().addMessage(null, mens);
    }

    public void eliminar(Categoria c) {
        try {
            c.setEstado("Inactivo");
            categoriaEJB.edit(c);
            categorias = categoriaEJB.findAll(); // Recargar todas las categorías

            // Remover la categoría eliminada de la lista local
            categorias.removeIf(cat -> cat.getEstado().equals("Inactivo"));

            this.categoria = new Categoria();
            this.mensaje = "Eliminado exitosamente";
        } catch (Exception e) {
            e.printStackTrace();
            this.mensaje = "Error " + e.getMessage();
        }
        FacesMessage mens = new FacesMessage(this.mensaje);
        FacesContext.getCurrentInstance().addMessage(null, mens);
    }

    public void actualizar() {
        try {
            categoriaEJB.edit(categoria);
            categorias = categoriaEJB.findAll();

            this.categoria = new Categoria();
            categorias.removeIf(c -> "Inactivo".equals(c.getEstado()));
            this.mensaje = "Actualizado exitosamente";
        } catch (Exception e) {
            e.printStackTrace();
            this.mensaje = "Error " + e.getMessage();
        }
        FacesMessage mens = new FacesMessage(this.mensaje);
        FacesContext.getCurrentInstance().addMessage(null, mens);
    }

    public void cargarID(Categoria c) {
        this.categoria = c;
    }

    public void limpiar() {
        this.categoria = new Categoria();
    }

}
