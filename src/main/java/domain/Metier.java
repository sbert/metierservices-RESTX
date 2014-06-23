package domain;

import restx.exceptions.ErrorCode;
import restx.exceptions.ErrorField;
import restx.http.HttpStatus;

/**
 * @author 0027125
 */
public class Metier {

	private String code;

	private String libelle;

	private Qualification qualification;

	private FamillePro famille;

	private String division;

	private boolean appellationPrincipale;

	public Metier() {
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelleMetier) {
		this.libelle = libelleMetier;
	}

	public FamillePro getFamille() {
		return famille;
	}

	public void setFamille(FamillePro famille) {
		this.famille = famille;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String divisionMetier) {
		this.division = divisionMetier;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isAppellationPrincipale() {
		return appellationPrincipale;
	}

	public void setAppellationPrincipale(boolean appellationPrincipale) {
		this.appellationPrincipale = appellationPrincipale;
	}



    public static class Rules {

        @ErrorCode(code = "METIER-002", description = "must have metier - provided qualification code not found", status = HttpStatus.NOT_FOUND)
        public static enum QualificationCodeRef {
            @ErrorField("qualification code") QUALIFICATION_CODE
        }

    }
}
