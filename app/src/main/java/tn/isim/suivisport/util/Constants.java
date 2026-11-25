package tn.isim.suivisport.util;

/** Constantes partagées (clés des extras d'Intent et limites de saisie). */
public final class Constants {

    private Constants() { }

    public static final String EXTRA_SEANCE_ID = "extra_seance_id";
    public static final String EXTRA_SEANCE_TITRE = "extra_seance_titre";

    public static final int MAX_TITRE_LENGTH = 60;
    public static final int MAX_NOM_LENGTH = 60;
    public static final int MAX_USERNAME_LENGTH = 30;
}
