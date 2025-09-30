package org.example.jeu

import org.example.dresseur.Entraineur
import org.example.joueur
import org.example.monde.Zone
import org.example.monstre1
import org.example.monstre2
import org.example.monstre3
import org.example.monstres.IndividuMonstre

class Partie (
    var id : Int,
    var joueur : Entraineur,
    var zone : Zone
){
    fun choixStarter(){
        val m1 = IndividuMonstre(4, "springleaf",monstre1,null,1500.0)
        val m2 = IndividuMonstre(5, "flamkip", monstre2,null,1500.0)
        val m3 = IndividuMonstre(6, "aquamy", monstre3,null,1500.0)

        m1.afficheDetail()
        m2.afficheDetail()
        m3.afficheDetail()

        println("Choisir un monstre (1 -> springleaf, 2 -> flamkip, ou 3 -> aquamy) : ")
        val choix = readln()
        var starter = m1
        if (choix == "1") {
            starter = m1
        } else if (choix == "2") {
            starter = m2
        } else if (choix == "3") {
            starter  = m3
        } else {
            print("Erreur lors de la saisie du choix.")
        }
        starter.renommer()
        joueur.equipeMonstre.add(starter)
        starter.entraineur = joueur
    }


    fun modifierOrdreEquipe() {
        if (joueur.equipeMonstre.size < 2) {
            println("Erreur : il n'y a pas assez de monstres dans l'équipe.")
        }

        println("Équipe :")
        for (i in joueur.equipeMonstre.indices) {
            println("$i : ${joueur.equipeMonstre[i].nom}")
        }

        println("Saisir la position du premier monstre :")
        val position1 = readln().toInt()
        println("Saisir la position du second monstre :")
        val position2 = readln().toInt()

        val valeur = joueur.equipeMonstre[position1]
        joueur.equipeMonstre[position1] = joueur.equipeMonstre[position2]
        joueur.equipeMonstre[position2] = valeur
    }

    fun examineEquipe(){
        println("Équipe :")
        for (i in joueur.equipeMonstre.indices) {
            println("$i : ${joueur.equipeMonstre[i].nom}")
        }
        println("numéro du monstre pour voir son détail ,q -> quitter le menu, m -> modifier l'ordre de l'équipe")
        var choix = readln().toString()
        when(choix){
            "q" -> return
            "m" -> this.modifierOrdreEquipe()
            else -> joueur.equipeMonstre[choix.toInt()].afficheDetail()
        }
    }

    fun jouer() {
        println("Vous êtes dans la zone ${zone.nom}")

        println("Actions possibles :")
        println("1 => Rencontrer un monstre sauvage")
        println("2 => Examiner l'équipe de monstres")
        println("3 => Aller à la zone suivante")
        println("4 => Aller à la zone précédente")

        when (readln()) {
            "1" -> {
                zone.rencontreMonstre()
                jouer()
            }

            "2" -> {
                examineEquipe()
                jouer()
            }

            "3" -> {
                if (zone.zoneSuivante != null) {
                    zone = zone.zoneSuivante!!
                    jouer()
                } else {
                    println("Pas de zone suivante.")
                    jouer()
                }
            }

            "4" -> {
                if (zone.zonePrecedente != null) {
                    zone = zone.zonePrecedente!!
                    jouer()
                } else {
                    println("Pas de zone précédente.")
                    jouer()
                }
            }

            else -> {
                println("Choix invalide.")
                jouer()
            }
        }
    }
}