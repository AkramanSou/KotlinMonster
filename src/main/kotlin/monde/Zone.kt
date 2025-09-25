package org.example.monde

import org.example.jeu.CombatMonstre
import org.example.joueur
import org.example.monstres.EspeceMonstre
import org.example.monstres.IndividuMonstre
import kotlin.random.Random

/**
 * Représente une zone dans un environnement de jeu.
 *
 * @property id Identifiant unique de la zone.
 * @property nom Nom de la zone.
 * @property expZone Points d'expérience que l'on peut obtenir dans cette zone.
 * @property especesMonstres Liste mutable des espèces de monstres présentes dans la zone.
 * @property zoneSuivante Référence vers la zone suivante (peut être nulle si aucune zone suivante).
 * @property zonePrecedente Référence vers la zone précédente (peut être nulle si aucune zone précédente).
 */

class Zone (
    var id : Int,
    var nom : String,
    var expZone : Int,
    var especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedente: Zone? = null,
    //TODO genereMonstre()
    //TODO rencontreMonstre()
){
    fun genereMonstre(): IndividuMonstre {
        val especeMonstreAlea = especesMonstres.random()
        expZone += if (Random.nextBoolean()) 20 else -20
        var monstreAlea = IndividuMonstre(4000,"SauvageTest",especeMonstreAlea,null,expZone.toDouble())
        return monstreAlea
    }

    fun rencontreMonstre(){
        val monstreSauvage = genereMonstre()
        var premierPokemon: IndividuMonstre? = null
        joueur.equipeMonstre.forEach(){monstre ->
            if(monstre.pv>0){
                premierPokemon = monstre
            }
        }
        val combat = CombatMonstre(premierPokemon!!, monstreSauvage)
        combat.lancerCombat()
    }
}