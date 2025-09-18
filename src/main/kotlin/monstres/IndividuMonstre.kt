package org.example.monstres

import org.example.dresseur.Entraineur
import kotlin.random.Random

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
    explnit : Double,
){
    var niveau : Int = 1

    var attaque : Int = espece.baseAttaque + if (Random.nextBoolean()) 2 else -2
    var defense : Int = espece.baseDefense + if (Random.nextBoolean()) 2 else -2
    var vitesse : Int = espece.baseVitesse + if (Random.nextBoolean()) 2 else -2
    var attaqueSpe : Int = espece.baseAttaqueSpe + if (Random.nextBoolean()) 2 else -2
    var defenseSpe : Int = espece.baseDefenseSpe + if (Random.nextBoolean()) 2 else -2
    var pvMax : Int = espece.basePv + if (Random.nextBoolean()) 5 else -5
    var potentiel : Double = (Random.nextDouble(0.5,2.0))
    var exp : Double = 0.0
    var pv : Int = pvMax

}