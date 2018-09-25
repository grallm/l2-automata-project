/**
 * 
 * Copyright (C) 2017 Emmanuel DESMONTILS
 * 
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 * 
 * 
 * 
 * E-mail:
 * Emmanuel.Desmontils@univ-nantes.fr
 * 
 * 
 **/


/**
 * Automate.java
 * 
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

package JFSM;

import java.util.Set;
import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import java.util.Map;
import java.util.HashMap;

import java.util.Iterator;

import java.util.Stack;

public abstract class Automate implements Cloneable {
	public Map<String,Etat> Q;
	public Set<String> F, I;
	public Set<String> A;
	public Stack<Transition> histo;
	public Set<Transition> mu;
	protected String current;
	// protected Etat trash;

	/** 
	* Constructeur de l'automate {A,Q,I,F,mu}
	* @param A l'alphabet de l'automate
	* @param Q l'ensemble des états de l'automate
	* @param I l'ensemble des états initiaux de l'automate
	* @param F l'ensemble des états finaux de l'automate
	* @param mu la fonction de transition de l'automate
	* @exception JFSMException Exception si un état qui n'existe pas est ajouté comme état initial ou final
	*/
	public Automate(Set<String> A, Set<Etat> Q, Set<String> I, Set<String> F, Set<Transition> mu) throws JFSMException {
		// Ajout de l'alphabet
		assert A.size()>0 : "A ne peut pas être vide" ;
		for(String a : A) assert a != "" : "Il ne peut pas y avoir de symbole vide" ;
		this.A = A;
		this.mu = new HashSet<Transition>();

		// Ajout des états
		assert Q.size()>0 : "Q ne peut pas être vide" ;
		this.Q = new HashMap<String,Etat>();

		for (Etat e : Q)
			if (this.Q.containsKey(e.name)) System.out.println("Etat dupliqué ! Seule une version sera conservée.");
			else this.Q.put(e.name,e); 
		
		// Création de l'historique (chemin)
		this.histo = new Stack<Transition>();

		// Ajout des transitions
		this.mu.addAll(mu);

		// On collecte les états initiaux, on les positionne comme tel. S'il n'existe pas, il est oublié.
		// assert I.size()>0 : "I ne peut pas être vide" ;
		this.I = new HashSet<String>();
		for (String i : I) setInitial(i);

		// On collecte les états finaux, on les positionne comme tel. S'il n'existe pas, il est oublié.
		this.F = new HashSet<String>();
		for(String f : F) setFinal(f);
	}

	public String toString() {
		String s = "{ Q={";
		for(String q : Q.keySet() ) if (q != "#Trash#") s = s + q + " ";
		s = s + "} I={ " ;
		for(String q : I ) s = s + q + " ";
		s = s + "} F={ " ;
		for(String q : F ) s = s + q + " ";
		s = s + "} \n   mu={ \n" ;
		for(Transition t : mu ) if ( (t.source != "#Trash#") && (t.cible != "#Trash#") ) s = s + "\t"+ t + "\n";
		s = s + "   }\n}" ;

		return s ;
	}

	public Object clone() {
		Automate o = null;
		try {
			o = (Automate)super.clone();
			o.Q = (Map<String,Etat>) ((HashMap<String,Etat>)Q).clone() ;
			o.F = (Set<String>)  ((HashSet<String>)F).clone();
			o.I = (Set<String>)  ((HashSet<String>)I).clone();
			o.A = (Set<String>)  ((HashSet<String>)A).clone();
			o.histo = (Stack<Transition>) ((Stack<Transition>)histo).clone();
			o.mu = (Set<Transition>) ((HashSet<Transition>)mu).clone();
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}

	/** 
	* Ajoute une transition à mu.  
	* @param t transition à ajouter
	*/
	public void addTransition(Transition t) {
		mu.add(t);
	}

	/** 
	* Ajoute un état à Q.  
	* @param e L'état
	*/
	public void addEtat(Etat e){
		if (!Q.containsKey(e.name))
			Q.put(e.name,e);
	}

	/** 
	* Retrouve un état par son nom.  
	* @param n Le nom de l'état 
	* @return l'état retrouvé, null sinon
	*/
	public Etat getEtat(String n) {
		if (Q.containsKey(n))
			return Q.get(n);
		else return null;
	}

	/** 
	* Fixe le vocabulaire de l'automate.  
	* @param A la vocabulaire 
	*/
	public void setA(Set<String> A){
		this.A = A;
	}

	/** 
	* Indique qu'un état (par son nom) est un état initial.  
	* @param e Le nom de l'état
	* @exception JFSMException Si l'état est absent
	*/
	public void setInitial(String e) throws JFSMException {	
		if (Q.containsKey(e)) {
			I.add(e);
		} else throw new JFSMException("Etat absent:"+e);
	}

	/** 
	* Indique qu'un état (par son nom) est un état final.  
	* @param e Le nom de l'état
	* @exception JFSMException Si l'état est absent
	*/
	public void setFinal(String e) throws JFSMException {	
		if (Q.containsKey(e)) {
			F.add(e);
		} else throw new JFSMException("Etat absent:"+e);
	}

	/** 
	* Détermine si un état (par son nom) est un état initial.  
	* @param e Le nom de l'état
	* @return vrai si initial, faux sinon
	*/
	public boolean isInitial(String e){
		assert Q.containsKey(e) : "isInitial : l'état doit être un état de l'automate." ;
		return I.contains(e);
	}

	/** 
	* Détermine si un état (par son nom) est un état final.  
	* @param e Le nom de l'état
	* @return vrai si final, faux sinon
	*/
	public boolean isFinal(String e){
		assert Q.containsKey(e) : "isFinal : l'état doit être un état de l'automate." ;
		return F.contains(e);
	}

	/** 
	* Initialise l'exécution de l'automate.  
	*/
	public void init() {
		histo.clear();
	}

	/** 
	* Indique si l'automate est dans un état final.  
	* @return vrai si final, faux sinon
	*/
	public boolean accepte(){return isFinal(current);}

	/** 
	* Supprime les états qui ne sont pas utiles (accessible et co-accessible)  
	* @return un automate équivalent utile (tous les états sont utiles)
	*/
	public Automate emonder() {
		System.out.println("emonder() : méthode non implémentée");
		return this;
	}

	/** 
	* Détermine si l'automate est utile  
	* @return booléen
	*/
	public boolean estUtile() {
		System.out.println("estUtile() : méthode non implémentée");
		return false;
	}

	/** 
	* Permet de transformer l'automate en un automate standard  
	* @return un automate équivalent standard
	*/
	public Automate standardiser() {
		System.out.println("standardiser() : méthode non implémentée");
		return this;
	}

	/** 
	* Détermine si l'automate est standard  
	* @return booléen
	*/
	public boolean estStandard() {
		System.out.println("estStandard() : méthode non implémentée");
		return false;
	}

	/** 
	* Permet de transformer l'automate en un automate normalisé  
	* @return un automate équivalent normalisé
	*/
	public Automate normaliser() {
		System.out.println("normaliser() : méthode non implémentée");
		return this;
	}

	/** 
	* Détermine si l'automate est normalisé  
	* @return booléen
	*/
	public boolean estNormalise() {
		System.out.println("estNormalise() : méthode non implémentée");
		return false;
	}

	/** 
	* Construit un automate reconnaissant le produit du langage de l'automate avec celui de "a" : L(this)xL(a)
	* @param a un Automate
	* @return un automate reconnaissant le produit
	*/
	public Automate produit(Automate a) {
		System.out.println("produit() : méthode non implémentée");
		return a;
	}

	/** 
	* Construit un automate reconnaissant le langage de l'automate à l'étoile : L(this)*
	* @return un automate reconnaissant la mise à l'étoile
	*/
	public Automate etoile() {
		System.out.println("etoile() : méthode non implémentée");
		return this;
	}

	/** 
	* Construit un automate reconnaissant l'union du langage de l'automate avec celui de "a" : L(this) U L(a)
	* @param a un Automate
	* @return un automate reconnaissant le produit
	*/
	public Automate union(Automate a) {
		System.out.println("union() : méthode non implémentée");
		return a;
	}

	/** 
	* Détermine des transitions possibles que peut emprunter l'automate en fonction de l'état courant et du symbole courant
	* @param symbol le symbole
	* @return la liste des transitions possibles 
	*/
	public abstract Queue<Transition> next(String symbol);

	/** 
	* Exécute l'automate sur un mot (une liste de symboles)
	* @param l la liste de symboles
	* @return un booléen indiquant sur le mot est reconnu 
	*/
	public abstract boolean run(List<String> l) ;
}
