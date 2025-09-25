package org.example.monstres

import org.example.dresseur.Entraineur
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Représente un individu monstre dans le jeu.
 *
 * @property id Identifiant unique de l'individu.
 * @property nom Nom de l'individu.
 * @property espece Espèce du monstre, contenant ses statistiques de base.
 * @property entraineur Entraîneur associé à cet individu, peut être null s'il n'en a pas.
 * @property explnit Valeur d'expérience initiale (probablement une faute de frappe, devrait être "expInit" ?).
 *
 * Chaque individu possède un niveau initial de 1, ainsi que des statistiques dérivées de son espèce
 * avec une légère variation aléatoire (±2 pour la plupart des stats, ±5 pour les points de vie).
 * Le potentiel est un coefficient aléatoire entre 0.5 et 2.0 qui pourrait influencer la croissance.
 * L'expérience commence à 0, et les points de vie actuels sont initialisés à la valeur maximale.
 */

class IndividuMonstre (
    var id : Int,
    var nom : String,
    var espece : EspeceMonstre,
    var entraineur : Entraineur? = null,
    expInit : Double,
){
    var niveau : Int = 1

    var attaque : Int = espece.baseAttaque + if (Random.nextBoolean()) 2 else -2
    var defense : Int = espece.baseDefense + if (Random.nextBoolean()) 2 else -2
    var vitesse : Int = espece.baseVitesse + if (Random.nextBoolean()) 2 else -2
    var attaqueSpe : Int = espece.baseAttaqueSpe + if (Random.nextBoolean()) 2 else -2
    var defenseSpe : Int = espece.baseDefenseSpe + if (Random.nextBoolean()) 2 else -2
    var pvMax : Int = espece.basePv + if (Random.nextBoolean()) 5 else -5
    var potentiel : Double = (Random.nextDouble(0.5,2.0))

    /**
     * Propriété `exp` représentant l'expérience du monstre.
     *
     * Lors de l'affectation d'une nouvelle valeur :
     * - La valeur interne `field` est mise à jour.
     * - On vérifie si le monstre est actuellement au niveau 1.
     * - Tant que l'expérience est suffisante pour passer au niveau supérieur,
     *   la fonction `levelUp()` est appelée.
     * - Si le monstre n'était pas au niveau 1 au départ,
     *   un message est affiché pour indiquer la montée de niveau,
     *   puis la boucle est interrompue.
     *
     * Ceci permet de gérer automatiquement la montée de niveau en fonction
     * de l'expérience accumulée.
     */

    var exp : Double = 0.0
        get() = field
        set(value){
            field = value
            var estNiveau1 = false
            if (niveau ==1) {
                estNiveau1 = true
            }
            do {
                levelUp()
                if (estNiveau1 == false) {
                    println("Le monstre $nom est mainteant niveau $niveau")
                    break
                }
            }while (field >= palierExp(niveau))

        }


    /**
     *  @property pv  Points de vie actuels.
     * Ne peut pas être inférieur à 0 ni supérieur à [pvMax].
     */

    var pv : Int = pvMax
        get() = field
        set(nouveauPv) {
            field = nouveauPv
            if (nouveauPv > pvMax){
                field = pvMax
            }else if (nouveauPv < 0){
                field = 0
            }
        }

    init {
        this.exp = expInit // applique le setter et déclenche un éventuel level-up
    }

    /**
     * Retourne l'expérience requise pour atteindre le niveau donné.
     *
     * @param niveauCible Niveau cible (par défaut, niveau actuel).
     * @return Expérience nécessaire pour ce palier.
     */


    fun palierExp(niveauCible : Int = this.niveau) : Double{
        return (100*(niveauCible-1).toDouble().pow(2.0))
    }


    /**
     * Cette fonction augmente le niveau du personnage/monstre et met à jour ses statistiques.
     * À chaque appel :
     * - Le niveau (`niveau`) est incrémenté de 1.
     * - Chaque statistique principale (attaque, défense, vitesse, attaque spéciale, défense spéciale, PV max)
     *   est augmentée en fonction du potentiel du personnage multiplié par un modificateur spécifique à son espèce.
     * - Une variation aléatoire (+2 ou -2 pour la plupart des stats, +5 ou -5 pour les PV max) est ajoutée
     *   pour apporter une légère différence à chaque montée de niveau.
     */

    fun levelUp() : Unit {

        niveau++

        attaque += round((espece.modAttaque * potentiel)).toInt() + (Random.nextInt(-2, 3))
        defense += round((espece.modDefense * potentiel)).toInt() + (Random.nextInt(-2, 3))
        vitesse += round((espece.modVitesse * potentiel)).toInt() + (Random.nextInt(-2, 3))
        attaqueSpe += round((espece.modAttaqueSpe * potentiel)).toInt() + (Random.nextInt(-2, 3))
        defenseSpe += round((espece.modDefenseSpe * potentiel)).toInt() + (Random.nextInt(-2, 3))
        pvMax += round((espece.modPv * potentiel)).toInt() + (Random.nextInt(-5, 5))

    }

    /**
     * Attaque un autre [IndividuMonstre] et inflige des dégâts.
     *
     * Les dégâts sont calculés de manière très simple pour le moment :
     * `dégâts = attaque - (défense / 2)` (minimum 1 dégât).
     *
     * @param cible Monstre cible de l'attaque.
     */

    fun attaquer (cible : IndividuMonstre){
        var degatBrut = this.attaque
        var degatTotal = degatBrut - (this.defense/2)
        if (degatTotal < 1) {
            degatTotal = 1
        }
        var pvAvant = cible.pv
        cible.pv -=degatTotal
        var pvApres = cible.pv
        println("${this.nom} inflige ($pvAvant-$pvApres) dégâts à ${cible.nom}")
    }

    /**
     * Demande au joueur de renommer le monstre.
     * Si l'utilisateur entre un texte vide, le nom n'est pas modifié.
     */

    fun renommer(){
        println("renommer ${this.nom}")
        var nouveauNom = readln()
        if (nouveauNom.isNotEmpty()){
            this.nom = nouveauNom
        }
    }

    /**
     * Affiche les informations principales du monstre :
     * espèce, nom, PV, attaque et défense.
     */
    fun afficheDetail(){
        println(espece.afficheArt())
        println("nom du monstre : $nom")
        println("point de vie : $pv")
        println("attaque : $attaque")
        println("defense : $defense")
    }
}