package es.rchavarria.raccount.model;

public class ExpensesByConcept {

    public final Double expenses;
    public final Concept concept;

    public ExpensesByConcept(Double expenses, Concept concept) {
        this.expenses = expenses;
        this.concept = concept;
    }
}
