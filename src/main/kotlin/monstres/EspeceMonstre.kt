package org.example.monstres

import java.io.File

/**
 * Classe représentant une espèce de monstre avec ses caractéristiques de base et ses modificateurs.
 *
 * @property id Identifiant unique de l'espèce.
 * @property nom Nom de l'espèce de monstre.
 * @property type Type élémentaire ou catégorie de l'espèce (ex : feu, eau, plante, etc.).
 * @property baseAttaque Valeur de base de l'attaque physique de l'espèce.
 * @property baseDefense Valeur de base de la défense physique de l'espèce.
 * @property baseVitesse Valeur de base de la vitesse de l'espèce.
 * @property baseAttaqueSpe Valeur de base de l'attaque spéciale de l'espèce.
 * @property baseDefenseSpe Valeur de base de la défense spéciale de l'espèce.
 * @property basePv Valeur de base des points de vie (PV) de l'espèce.
 * @property modAttaque Modificateur multiplicatif appliqué à l'attaque physique.
 * @property modDefense Modificateur multiplicatif appliqué à la défense physique.
 * @property modVitesse Modificateur multiplicatif appliqué à la vitesse.
 * @property modAttaqueSpe Modificateur multiplicatif appliqué à l'attaque spéciale.
 * @property modDefenseSpe Modificateur multiplicatif appliqué à la défense spéciale.
 * @property modPv Modificateur multiplicatif appliqué aux points de vie.
 * @property description Description textuelle de l'espèce, son apparence ou son histoire.
 * @property particularites Caractéristiques ou capacités spécifiques propres à cette espèce.
 * @property caractères Traits de personnalité ou comportementaux de l'espèce.
 */

class EspeceMonstre (
    var id : Int,
    var nom: String,
    var type: String,
    val baseAttaque: Int,
    val baseDefense: Int,
    val baseVitesse: Int,
    val baseAttaqueSpe: Int,
    val baseDefenseSpe: Int,
    val basePv: Int,
    val modAttaque: Double,
    val modDefense: Double,
    val modVitesse: Double,
    val modAttaqueSpe: Double,
    val modDefenseSpe: Double,
    val modPv: Double,
    val description: String = "",
    val particularites: String = "",
    val caractères: String = "",
){
/**
 * Affiche la représentation artistique ASCII du monstre.
 *
 * @param deFace Détermine si l'art affiché est de face (true) ou de dos (false).
 *               La valeur par défaut est true.
 * @return Une chaîne de caractères contenant l'art ASCII du monstre avec les codes couleur ANSI.
 *         L'art est lu à partir d'un fichier texte dans le dossier resources/art.
 */
fun afficheArt(deFace: Boolean=true): String{
    val nomFichier = if(deFace) "front" else "back";
    val art=  File("src/main/resources/art/${this.nom.lowercase()}/$nomFichier.txt").readText()
    val safeArt = art.replace("/", "∕")
    return safeArt.replace("\\u001B", "\u001B")
    }
}