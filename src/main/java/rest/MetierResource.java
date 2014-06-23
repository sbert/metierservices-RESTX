package rest;

import domain.FamillePro;
import domain.Metier;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.exceptions.RestxErrors;
import restx.factory.Component;
import restx.jongo.JongoCollection;
import restx.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

/**
 * Created by sbert on 20/06/2014.
 */
@Component
@RestxResource
@PermitAll
public class MetierResource {

    private static final Logger logger = LoggerFactory.getLogger(MetierResource.class);

    private final JongoCollection metiers;
    private final RestxErrors errors;

    public MetierResource(@Named("metiers") JongoCollection metiers, RestxErrors errors) {
        this.metiers = metiers;
        this.errors = errors;
    }

    @GET("/metier/libelle/{libelle}")
    public Iterable<Metier> findMetierByLibelle(String libelle) {
        return metiers.get().find("{libelle: {$regex: '#', $options: 'i'}}", regExpContains(libelle)).as(Metier.class);
    }

    @GET("/metier/qualif/libelle/{libelle}")
    public Iterable<Metier> findMetierByLibelleQualif(String libelle) {
        return metiers.get().find("{qualification.libelle: {$regex: '#', $options: 'i'}}", regExpContains(libelle)).as(Metier.class);
    }

    @GET("/metier/qualif/code/{code}")
    public Iterable<Metier> findMetierByCodeQualif(String code) {
        Iterable<Metier> metiersResult = metiers.get().find("{qualification.code: '#'}", code).as(Metier.class);
        if( !metiersResult.iterator().hasNext() ) {
            throw errors.on(Metier.Rules.QualificationCodeRef.class)
                    .set(Metier.Rules.QualificationCodeRef.QUALIFICATION_CODE, code)
                    .raise();
        }
        return metiersResult;
    }

    @GET("/appellation/{codeMetier}")
    public Metier findAppellationMetier(String codeMetier) {
        return metiers.get().findOne("{code: '#', appellationPrincipale: true}", codeMetier).as(Metier.class);
    }

    @POST("/appellation")
    public Iterable<Metier> findAppellationMetier(List<String> codeMetiers) {
        return metiers.get().find("{code: {$in: #}, appellationPrincipale: true}", codeMetiers).as(Metier.class);
    }

    @GET("/famille")
    public List<FamillePro> getFamillePro() {
        return metiers.get().distinct("famille").as(FamillePro.class);
    }

    private String regExpContains(String label) {
        return ".*" + label + ".*";
    }

}
