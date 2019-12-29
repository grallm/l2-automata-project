package exo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import JFSM.AFD;
import JFSM.AFN;
import JFSM.Automate;
import JFSM.Etat;
import JFSM.JFSMException;
import JFSM.Transition;

public class Ex1B {
	public static void main(String[] args) throws JFSMException {
		// transpose();
		etoile();
	}

	// Test Automate.transpose()
	public static void transpose() throws JFSMException {
		// Code exported from http://madebyevan.com/fsm/ with https://github.com/grallm/export-fsm
		// Alphabet
		Set<String> A = new HashSet<String>();
		A.add("a");
		A.add("b");

		// States
		Set<Etat> Q = new HashSet<Etat>();
		Q.add(new Etat("1"));
		Q.add(new Etat("2"));
		Q.add(new Etat("3"));

		// Initial States
		Set<String> I = new HashSet<String>();
		I.add("1");

		// Final States
		Set<String> F = new HashSet<String>();
		F.add("3");

		// Transitions
		Set<Transition> mu = new HashSet<Transition>();
		mu.add(new Transition("1", "a", "2"));
		mu.add(new Transition("2", "b", "3"));

		// Constructor
		Automate afn = new AFN(A, Q, I, F, mu);


		// Check words : ab and ba - true and false
		List<String> l = new ArrayList<String>();
		l.add("a");l.add("b");
		System.out.println(afn.run(l) + "\n");
		List<String> l2= new ArrayList<String>();
		l2.add("b");l2.add("a");
		System.out.println(afn.run(l2) + "\n" + "\n");

		// Check words with transposed : ab and ba - false and true
		System.out.println(afn.transpose().run(l) + "\n");
		System.out.println(afn.transpose().run(l2) + "\n");
	}

	// Test Automate.etoile()
	public static void etoile() throws JFSMException {
		// Code exported from http://madebyevan.com/fsm/ with https://github.com/grallm/export-fsm
		// Alphabet
		Set<String> A = new HashSet<String>();
		A.add("a");
		A.add("b");

		// States
		Set<Etat> Q = new HashSet<Etat>();
		Q.add(new Etat("1"));
		Q.add(new Etat("2"));
		Q.add(new Etat("3"));

		// Initial States
		Set<String> I = new HashSet<String>();
		I.add("1");

		// Final States
		Set<String> F = new HashSet<String>();
		F.add("3");

		// Transitions
		Set<Transition> mu = new HashSet<Transition>();
		mu.add(new Transition("1", "a", "2"));
		mu.add(new Transition("2", "b", "3"));

		// Constructor
		Automate afn = new AFN(A, Q, I, F, mu);


		// Check words : ab, abab and '' (nothing) - true, false and false
		List<String> l = new ArrayList<String>();
		l.add("a");l.add("b");
		System.out.println(afn.run(l) + "\n");
		List<String> l2= new ArrayList<String>();
		l2.add("a");l2.add("b");l2.add("a");l2.add("b");
		System.out.println(afn.run(l2) + "\n" + "\n");
		List<String> l3= new ArrayList<String>();
		System.out.println(afn.run(l3) + "\n" + "\n");

		// Check words of starred automate : ab, abab and '' (nothing) - true, true and true
		System.out.println(afn.etoile().run(l) + "\n");
		System.out.println(afn.etoile().run(l2) + "\n");
		System.out.println(afn.etoile().run(l3) + "\n" + "\n");
	}
}
