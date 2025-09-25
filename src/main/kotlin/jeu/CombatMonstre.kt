package org.example.jeu

import org.example.item.Utilisable
import org.example.joueur
import org.example.monstres.IndividuMonstre

class CombatMonstre (
    var monstreJoueur : IndividuMonstre,
    var monstreSauvage : IndividuMonstre,
){
    var round : Int = 1

    /**
     * Vérifie si le joueur a perdu le combat.
     *
     * Condition de défaite :
     * - Aucun monstre de l'équipe du joueur n'a de PV > 0.
     *
     * @return `true` si le joueur a perdu, sinon `false`.
     */
    fun gameOver(): Boolean {
        var compteur = 0
        joueur.equipeMonstre.forEach { monstre ->
            if(monstre.pv <= 0){
                compteur++
            }
        }
        if(compteur == joueur.equipeMonstre.size) {
            return true
        }else {
            return false
        }
    }

    /**
     * Vérifie si le joueur a gagné la bataille.
     *
     * Si les points de vie du monstre sauvage sont à 0 ou moins, le joueur gagne :
     * - Affiche un message de victoire.
     * - Le monstre du joueur gagne de l'expérience (20% de l'expérience du monstre sauvage).
     *
     * Sinon, si le monstre sauvage appartient maintenant au joueur (capturé), affiche un message de capture.
     *
     * Retourne true si le joueur a gagné ou capturé le monstre, sinon false.
     */

    fun joueurGagne(): Boolean{
        if(monstreSauvage.pv <= 0){
            println("${joueur.nom} a gagné !")
            var gainExp = monstreSauvage.exp*0.20
            monstreJoueur.exp += gainExp
            println("${monstreJoueur.nom} gagne $gainExp exp")
            return true
        }else{
            if(monstreSauvage.entraineur == joueur){
                println("${monstreSauvage.nom} à été capturé !")
                return true
            }else{
                return false
            }
        }
    }

    /**
     * Permet au monstre sauvage d'attaquer le monstre du joueur,
     * uniquement si le monstre sauvage est encore en vie (points de vie > 0).
     */

    fun actionAdversaire(){
        if(monstreSauvage.pv > 0){
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

    /**
     * Gère le tour d'action du joueur.
     *
     * 1. Vérifie si la partie est terminée via gameOver() ; si oui, retourne false pour arrêter.
     * 2. Affiche un menu d'actions et récupère le choix du joueur :
     *    - 1 : Le monstre du joueur attaque le monstre sauvage.
     *    - 2 : Le joueur utilise un objet de son sac sur le monstre sauvage.
     *        - Si l'objet est utilisable, il est appliqué (ex: capture possible).
     *        - Si la capture réussit, la fonction retourne false pour finir le combat.
     *        - Sinon, affiche un message si l'objet n'est pas utilisable.
     *    - 3 : Le joueur peut changer de monstre en choisissant dans son équipe.
     *        - Refuse le changement si le monstre choisi est KO (pv <= 0).
     *        - Sinon, effectue le remplacement et affiche un message.
     *
     * Retourne true si le combat continue, false sinon.
     */

    fun actionJoueur(): Boolean{
        if(gameOver() == true){
            return false
        }
        println("Menu d'action (1, 2 ,3)")
        var choixAction = readln().toInt()
        if(choixAction == 1){
            monstreJoueur.attaquer(monstreSauvage)
            return true
        } else if(choixAction == 2){
            println(joueur.sacAItems)
            var indexChoix: Int = readln().toInt()
            var objetChoisi = joueur.sacAItems[indexChoix]
            if(objetChoisi is Utilisable){
                var captureReussie = objetChoisi.utiliser(monstreSauvage)
                if(captureReussie == true){
                    return false
                }
            } else{
                println("Objet non utilisable")
            }
        } else if(choixAction == 3){
            println(joueur.equipeMonstre)
            var indexChoix = readln().toInt()
            var choixMonstre = joueur.equipeMonstre[indexChoix]
            if(choixMonstre.pv <= 0){
                println("Impossible ! Ce monstre est KO")
            } else{
                println("${choixMonstre.nom} remplace ${monstreJoueur.nom}")
            }
        }
        return true
    }

    /**
     * Affiche l'état actuel du combat :
     * - Le numéro du round.
     * - Le niveau et les points de vie du monstre sauvage.
     * - L'art ASCII du monstre sauvage.
     * - L'art ASCII du monstre du joueur (avec un paramètre false).
     * - Le niveau et les points de vie du monstre du joueur.
     */

    fun afficheCombat(){
        println("========= Début Round : $round =========")
        println("Niveau : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreSauvage.pvMax}")
        println(monstreSauvage.espece.afficheArt())
        println(monstreJoueur.espece.afficheArt(false))
        println("Niveau : ${monstreJoueur.niveau}")
        println("PV : ${monstreJoueur.pv} / ${monstreJoueur.pvMax}")
    }

    /**
     * Gère le déroulement d'un tour de combat entre le joueur et le monstre sauvage.
     * L'ordre des actions est déterminé par la vitesse des monstres :
     * - Si le monstre du joueur est plus rapide ou aussi rapide que le monstre sauvage,
     *   le joueur agit en premier, suivi de l'adversaire.
     * - Si le monstre sauvage est plus rapide, il agit en premier, suivi du joueur.
     *
     * À chaque tour, la fonction :
     * 1. Affiche l'état actuel du combat via `afficheCombat()`.
     * 2. Permet au joueur d'effectuer une action via `actionJoueur()`.
     * 3. Si le combat continue, l'adversaire agit via `actionAdversaire()`.
     * 4. Si le combat continue, le joueur peut effectuer une nouvelle action.
     *
     * Le tour se termine lorsque l'un des deux monstres est vaincu (points de vie <= 0).
     */
    fun jouer(){
        val joueurPlusRapide: Boolean = (monstreJoueur.vitesse>=monstreSauvage.vitesse)
        afficheCombat()
        if(joueurPlusRapide){
            var continuer = this.actionJoueur()
            if(continuer == false){
                return
            }
            else{
                this.actionAdversaire()
                return
            }
        } else {
            this.actionAdversaire()
            if(this.gameOver()==false){
                val continuer = this.actionJoueur()
            } else {
                return
            }
        }
    }
    /**
     * Lance le combat et gère les rounds jusqu'à la victoire ou la défaite.
     *
     * Affiche un message de fin si le joueur perd et restaure les PV
     * de tous ses monstres.
     */
    fun lancerCombat() {
        while (!gameOver() && !joueurGagne()) {
            this.jouer()
            println("======== Fin du Round : $round ========")
            round++
        }
        if (gameOver()) {
            joueur.equipeMonstre.forEach { it.pv = it.pvMax }
            println("Game Over !")
        }
    }
}