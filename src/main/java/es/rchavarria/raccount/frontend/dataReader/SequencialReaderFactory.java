package es.rchavarria.raccount.frontend.dataReader;

import java.io.FileInputStream;

import es.rchavarria.raccount.model.DoubleMovement;

public class SequencialReaderFactory {
	public enum Reader {
		ING, CAJAMADRID, IBERCAJA
	}

	public static SequencialReader<DoubleMovement> create(final FileInputStream fis, final Reader reader) {
		switch (reader) {
		case ING:
			return new IngMovementSequencialReader(fis);
		case CAJAMADRID:
			return new CjMdMovementSequencialReader(fis);
		case IBERCAJA:
			return new IbcjMovementSequencialReader(fis);
		}

		throw new RuntimeException("There is no seq reader for type: " + reader);
	}
}
