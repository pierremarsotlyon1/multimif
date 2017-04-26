package metier;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import dao.FichierDao;
import model.*;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 16/11/2016.
 */
public class GestionParsing extends GestionBase {
    private FichierDao fichierDao;


    public GestionParsing(EntityManager em) {
        super(em);
        fichierDao = new FichierDao(em);
    }

    /**
     * Permet de parser un fichier pour récupérer les classes extends/implements
     * @param cu - L'objet CompilationUnit
     * @param javaDocFichier _ l'objet JavaDocFichier pour pouvoir sauvegarder les infos parsés
     * @return - succès du parsing
     */
    private boolean parsingClass(CompilationUnit cu, JavaDocFichier javaDocFichier) {
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                JavaDocFichier javaDocFichier = (JavaDocFichier) arg;
                if (javaDocFichier == null) return;

                //On récup le nom de la méthode
                String nomClasse = n.getName();

                //On récup les classes extends
                for (ClassOrInterfaceType cor : n.getExtends()) {
                    //Création de l'objet
                    JavaDocClasseExtends javaDocClasseExtends = new JavaDocClasseExtends(cor.getName());

                    //On ajoute à la liste et on fait la liaison
                    javaDocClasseExtends.setJavaDocFichier(javaDocFichier);
                    javaDocFichier.getJavaDocClasses().add(javaDocClasseExtends);
                }

                //On récup les classes impléments
                for (ClassOrInterfaceType cor : n.getImplements()) {
                    //Création de l'objet
                    JavaDocClasseImplement javaDocClasseImplement = new JavaDocClasseImplement(cor.getName());

                    //On ajoute à la liste et on fait la liaison
                    javaDocClasseImplement.setJavaDocFichier(javaDocFichier);
                    javaDocFichier.getJavaDocClasses().add(javaDocClasseImplement);
                }

                //On visite les méthodes
                parsingMethodes(n, javaDocFichier);

                //On assigne le nom de la classe
                javaDocFichier.setClasseName(nomClasse);
            }
        }.visit(cu, javaDocFichier);
        return true;
    }

    /**
     * Permet de parser un fichier pour récupérer les méthodes
     * @param cu - L'objet CompilationUnit
     * @param javaDocFichier _ l'objet JavaDocFichier pour pouvoir sauvegarder les infos parsés
     * @return - succès du parsing
     */
    private void parsingMethodes(ClassOrInterfaceDeclaration cu, JavaDocFichier javaDocFichier) {
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                JavaDocFichier javaDocFichier = (JavaDocFichier) arg;
                if (javaDocFichier == null) return;

                String contentJavaDoc = "";

                //On récup le nom de la méthode
                String nomMethode = n.getName();

                //On récup la déclaration de la méthode
                String declaration = n.getDeclarationAsString();

                if (nomMethode == null || nomMethode.isEmpty() || declaration == null || declaration.isEmpty()) {
                    super.visit(n, arg);
                    return;
                }

                //On récup l'objet de JavaDoc
                JavadocComment javaDoc = n.getJavaDoc();

                //On récup le contenu des commentaires de la javadoc de la méthode
                if(javaDoc != null)
                    contentJavaDoc = javaDoc.getContent();

                //Création de l'objet
                JavaDocMethode javaDocMethode = new JavaDocMethode(nomMethode, declaration, contentJavaDoc);

                //On parse les paramètres de la méthode
                for (Parameter parameter : n.getParameters()) {
                    JavaDocAttribut javaDocAttribut = new JavaDocAttribut(parameter.getName(), parameter.getType().toString());

                    javaDocMethode.getJavaDocAttribut().add(javaDocAttribut);
                    javaDocAttribut.setJavaDocMethode(javaDocMethode);
                }

                //On ajoute la méthode à la liste des méthodes
                javaDocFichier.getJavaDocMethodes().add(javaDocMethode);
                javaDocMethode.setJavaDocFichier(javaDocFichier);

                super.visit(n, arg);
            }
        }.visit(cu, javaDocFichier);
    }

    /**
     * Permet de parser un fichier
     * @param file - le fichier (Objet java)
     * @param idFichier - l'id du fichier
     * @return - succès du parsing
     */
    public boolean parseFichier(File file, int idFichier) {
        try {
            if (file == null || !file.exists()) return false;

            fichierDao.beginTransaction();

            //On récup le fichier
            Fichier fichier = fichierDao.get(idFichier);
            if (fichier == null) return false;

            JavaDocFichier javaDocFichier = fichier.getJavaDocFichier();

            //Instanciation du parser
            FileInputStream in = new FileInputStream(file);
            CompilationUnit cu;

            try {
                //On parse le fichier
                cu = JavaParser.parse(in);

                if(cu == null)
                {
                    fichierDao.rollback();
                    return false;
                }

                if(javaDocFichier == null)
                {
                    javaDocFichier = new JavaDocFichier();
                    javaDocFichier.setFichier(fichier);

                    fichier.setJavaDocFichier(javaDocFichier);

                    em.persist(javaDocFichier);
                }
                //On supprime la javadoc du fichier
                else if(javaDocFichier != null)
                {
                    //Méthodes
                    for (JavaDocMethode jdm : javaDocFichier.getJavaDocMethodes()) {
                        for(JavaDocAttribut javaDocAttribut : jdm.getJavaDocAttribut())
                        {
                            em.remove(javaDocAttribut);
                        }
                        em.remove(jdm);
                    }

                    //On supprime les anciens imports
                    for (JavaDocImport i : javaDocFichier.getJavaDocImportList()) {
                        em.remove(i);
                    }

                    //On supprime les classes extends et implements
                    for (JavaDocClasse javaDocClasse : javaDocFichier.getJavaDocClasses()) {
                        em.remove(javaDocClasse);
                    }

                    javaDocFichier.setJavaDocClasses(new ArrayList<JavaDocClasse>());
                    javaDocFichier.setJavaDocMethodes(new ArrayList<JavaDocMethode>());
                    javaDocFichier.setJavaDocImportList(new ArrayList<JavaDocImport>());
                }

                //On récup le nom du package
                String packageName = null;
                PackageDeclaration packageDeclaration = cu.getPackage();
                if(packageDeclaration != null)
                {
                    packageName = packageDeclaration.getPackageName();
                }

                //On récup la liste des imports
                List<ImportDeclaration> importsDeclaration = cu.getImports();

                if (!importsDeclaration.isEmpty()) {
                    //On parcourt la liste des imports
                    for (ImportDeclaration importDeclaration : importsDeclaration) {
                        //Création de l'objet import
                        JavaDocImport javaDocImportObj = new JavaDocImport(importDeclaration.getName().getName());

                        //Création de la liaison
                        javaDocFichier.getJavaDocImportList().add(javaDocImportObj);
                        javaDocImportObj.setJavaDocFichier(javaDocFichier);

                        //Persist de l'import
                        em.persist(javaDocImportObj);
                    }
                }

                //On visite toutes les méthodes
                //gestionParsingClasses.visit(cu, javaDocFichier);
                parsingClass(cu, javaDocFichier);

                //On assigne le nom du package
                javaDocFichier.setPackageName(packageName);

                //On fait les persists des méthodes
                for (JavaDocMethode m : javaDocFichier.getJavaDocMethodes()) {
                    for(JavaDocAttribut javaDocAttribut : m.getJavaDocAttribut())
                    {
                        em.persist(javaDocAttribut);
                    }
                    em.persist(m);
                }

                //On fait le persist des classes extends et implements
                for (JavaDocClasse javaDocClasse : javaDocFichier.getJavaDocClasses()) {
                    em.persist(javaDocClasse);
                }

                fichierDao.commit();
            } finally {
                //Fermeture du flux
                in.close();
            }

            return true;
        } catch (Exception e) {
            fichierDao.rollback();
            return false;
        }
    }
}
