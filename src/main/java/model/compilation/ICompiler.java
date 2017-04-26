package model.compilation;

import model.Projet;

/**
 * Created by Naeno on 24/11/2016.
 */
public interface ICompiler {
    String compile(Projet projet);
    String execute(Projet projet,String mainFile);
}
