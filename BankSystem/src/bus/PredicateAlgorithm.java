package bus;

import java.util.ArrayList;
import java.util.Collections;

public class PredicateAlgorithm {
	
	// Sorting attributes from ICustomer
	public static void sort (ArrayList<ICustomer> list, PredicateFirstName fNamePredicate) {
		Collections.sort(list,fNamePredicate);
	}
	
	public static void sort (ArrayList<ICustomer> list, PredicateLastName lNamePredicate) {
		Collections.sort(list,lNamePredicate);
	}
	
	public static void sort (ArrayList<ICustomer> list, PredicateCustomerId custIdPredicate) {
		Collections.sort(list,custIdPredicate);
	} 
	
	// Sorting attributes from IAccount
	public static void sort (ArrayList<IAccount> list, PredicateAccountNumber accNumberPredicate) {
		Collections.sort(list,accNumberPredicate);
	}
	
	public static void sort (ArrayList<IAccount> list, PredicateAccountType typePredicate) {
		Collections.sort(list,typePredicate);
	}
	
	public static void sort (ArrayList<IAccount> list, PredicateOpenDate datePredicate) {
		Collections.sort(list,datePredicate);
	}
	
	public static void sort (ArrayList<IAccount> list, PredicateAvailableBalance availableBalancePredicate) {
		Collections.sort(list,availableBalancePredicate);
	}
	
	public static void sort (ArrayList<IAccount> list, PredicateTotalBalance totalBalancePredicate) {
		Collections.sort(list,totalBalancePredicate);
	}
	
	// Sorting attributes from ITransaction
	public static void sort (ArrayList<ITransaction> list, PredicateTransactionType transactionTypePredicate) {
		Collections.sort(list,transactionTypePredicate);
	}
	
	public static void sort (ArrayList<ITransaction> list, PredicateTransactionNumber transactionNumberPredicate) {
		Collections.sort(list,transactionNumberPredicate);
	}
	
	public static void sort (ArrayList<ITransaction> list, PredicateAccountNumberTransaction accNumberTransPredicate) {
		Collections.sort(list,accNumberTransPredicate);
	}
	
	public static void sort (ArrayList<ITransaction> list, PredicateTransactionDate transDatePredicate) {
		Collections.sort(list,transDatePredicate);
	}
	
	public static void sort (ArrayList<ITransaction> list, PredicateTransactionDescription transDescriptionPredicate) {
		Collections.sort(list,transDescriptionPredicate);
	}
	
	public static void sort (ArrayList<ITransaction> list, PredicateTransactionAmount transAmtPredicate) {
		Collections.sort(list,transAmtPredicate);
	}
}
