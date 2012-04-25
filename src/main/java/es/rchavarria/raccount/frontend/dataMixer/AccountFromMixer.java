package es.rchavarria.raccount.frontend.dataMixer;

import es.rchavarria.raccount.model.DoubleMovement;

public class AccountFromMixer implements FieldMixer<DoubleMovement> {

    @Override
    public DoubleMovement mix(final DoubleMovement target, final DoubleMovement origin) {
        DoubleMovement result = target != null ? target : new DoubleMovement();
        if (canCopyConceptField(origin)) {
            result.setConcept(origin.getConcept());
        }
        if (canCopyAccountFromField(origin)) {
            result.setAccount(origin.getAccount());
        }
        if (canCopyAccountToField(origin)) {
            result.setAccountTo(origin.getAccountTo());
        }

        return result;
    }

    private boolean canCopyConceptField(final DoubleMovement origin) {
        return origin != null && origin.getConcept() != null;
    }

    private boolean canCopyAccountFromField(final DoubleMovement origin) {
        return origin != null && origin.getAccount() != null;
    }

    private boolean canCopyAccountToField(final DoubleMovement origin) {
        return origin != null && origin.getAccountTo() != null;
    }
}
